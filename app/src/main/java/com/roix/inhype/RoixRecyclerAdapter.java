package com.roix.inhype;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roix.inhype.pojo.Photo;
import com.roix.inhype.ui.renderers.ProfileRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roix on 24.05.2017.
 */

public class RoixRecyclerAdapter extends RecyclerView.Adapter<RoixRecyclerAdapter.RoixViewHolder> {

    private List<Pair<RoixRenderer,RoixDataItem>> pairs;

    private Context context;
    private EventListener eventListener;
    private RecyclerView recyclerView;

    public RoixRecyclerAdapter(Context context,RecyclerView recyclerView, EventListener eventListener){
        this.context=context;
        this.eventListener=eventListener;
        this.recyclerView=recyclerView;
    }


    @Override
    public RoixViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RoixRenderer itemRenderer=pairs.get(viewType).first;//itemRenderers.get(viewType);
        Log.d("RoixRecyclerAdapter","onCreateViewHolder "+viewType);
        View view = LayoutInflater.from(context).inflate(itemRenderer.getResID(), parent, false);
        RoixViewHolder holder=new RoixViewHolder(view,eventListener,itemRenderer);
        itemRenderer.create(view,holder);
        holder.setItemRenderer(itemRenderer);
        return holder;

    }

    public RoixRenderer getRenderer(int pos){
        return pairs.get(pos).first;
    }

    @Override
    public void onBindViewHolder(RoixViewHolder holder, int position) {
        Log.d("RoixRecyclerAdapter","onBindViewHolder "+position);
        RoixDataItem item=pairs.get(position).second;//items.get(position);
        holder.getItemRenderer().bind(item);
        holder.setUpExpandLogic();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public <T extends RoixDataItem,P extends RoixRenderer> void addItems (List<T>list,Class rendererClass )  {
        if(pairs==null)pairs=new ArrayList<>();
        int startedSize=pairs.size();
        for(int i=0;i<list.size();i++){
            Log.d("RoixRecyclerAdapter","addItems "+startedSize+" "+i);
            RoixRenderer itemRenderer=createItemRenderer(i+startedSize,rendererClass);
            pairs.add(new Pair<>(itemRenderer, list.get(i)));
        }
        notifyDataSetChanged();
    }

    public void addItem (RoixDataItem item,Class rendererClass ){
        if(pairs==null)pairs=new ArrayList<>();
        RoixRenderer itemRenderer=createItemRenderer(pairs.size(),rendererClass);
        pairs.add(new Pair<>(itemRenderer, item));
        notifyDataSetChanged();

    }


    public <T extends RoixDataItem,P extends RoixRenderer> void setItems(List<T>list,Class<P> rendererClass){
        pairs.clear();
        addItems(list,rendererClass);
    }

    public  <T extends RoixDataItem> void  updateAndAddItems(List<T> list,Class rendererClass){
        if(pairs==null)pairs=new ArrayList<>();
        int offset=0;
        for(int i=0;i<list.size();i++){
            RoixDataItem dataItem=list.get(i);
            int found=findFirstItemWithThisType(rendererClass,offset,i);

            if(found!=-1){
                pairs.get(found).second.copy(dataItem);
                offset=found-i;
            }
            else pairs.add(new Pair<>(createItemRenderer(i, rendererClass), dataItem));
        }
        notifyDataSetChanged();
    }

    private <P extends RoixRenderer>int findFirstItemWithThisType(Class<P> rendererClass,int offset,int pos){
        if(offset+pos>=pairs.size())return -1;
        for(int i=offset+pos;i<pairs.size()-offset;i++){
            Pair<RoixRenderer,RoixDataItem> pair=pairs.get(i);
            if(rendererClass.toString().equals(pair.first.getClass().toString())){
                Log.d("RoixRecyclerAdapter","class match "+rendererClass.toString() +" i "+i+" offset "+offset);
                return i;
            }
        }
        return -1;
    }



    public RoixDataItem getItem(int pos){
        return pairs.get(pos).second;
    }


    @Override
    public int getItemCount() {
        if(pairs!=null)
            return pairs.size();
        return 0;
    }

    public void clearData(){
        if(pairs!=null)pairs.clear();
        pairs=null;
        notifyDataSetChanged();
    }

    public <T extends RoixRenderer> void clearData(Class<T> rendererClass){
        List<Pair<RoixRenderer,RoixDataItem>> pairs1=new ArrayList<>();
        for (Pair<RoixRenderer,RoixDataItem> pair:pairs){
            if(!pair.first.getClass().toString().equals(rendererClass.toString())){
                pairs1.add(pair);
            }
        }
        pairs=pairs1;
    }



    private <P extends RoixRenderer> RoixRenderer createItemRenderer(int pos,Class<P> rendererClass){
        RoixRenderer itemRenderer= null;
        try {
            itemRenderer = rendererClass.newInstance();
            itemRenderer.savePos(pos);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return itemRenderer;
    }



    public interface RoixDataItem{
        void copy(RoixDataItem item);
    }

    public interface EventListener{
        void onEvent(int type,Object content,RoixDataItem item,int pos);
    }

    public interface ItemEventListener{
        void OnEvent(int type,Object content);
    }

    public static abstract class RoixRenderer{
        public static final int EVENT_CLICKED=-3;

        private int pos=-1;

        public abstract int getResID();
        public abstract void create(View v, ItemEventListener listener);
        public abstract void bind(RoixDataItem item);
        public abstract void change(int eventType,RoixDataItem item);

        public RoixRenderer(){}//required empty
        final public void savePos(int pos){this.pos=pos;}
        final public int getPos(){return pos;}

    }

    public static abstract class RoixExpandableRenderer extends RoixRenderer{
        public static final int EVENT_EXPAND =-1;
        public static final int EVENT_COLLAPSE =-2;
        public static final int COMMAND_COLLAPSE=-3;
        public abstract View getNotExpandedPart();

        private boolean isExpanded=false;

        final public boolean isExpanded() {
            return isExpanded;
        }

        final public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

    }


    public class RoixViewHolder extends RecyclerView.ViewHolder implements ItemEventListener{

        private EventListener listener;
        private RoixRenderer itemRenderer;
        private RoixRenderer renderer;

        public RoixViewHolder(View view,EventListener listener,RoixRenderer renderer) {
            super(view);
            this.listener=listener;
            this.renderer=renderer;
        }

        public void setUpExpandLogic(){
            if(renderer instanceof RoixExpandableRenderer){
                RoixExpandableRenderer expandableRenderer= (RoixExpandableRenderer) renderer;
                expandableRenderer.getNotExpandedPart().setOnClickListener(v -> {
                    handleExpand(expandableRenderer);
                });

            }

        }

        public void handleExpand(RoixExpandableRenderer expandableRenderer){
            int pos=getAdapterPosition();
            listener.onEvent(RoixRenderer.EVENT_CLICKED,null,pairs.get(pos).second,pos);
            boolean isExpanded=expandableRenderer.isExpanded();
            int event=isExpanded?RoixExpandableRenderer.EVENT_COLLAPSE:RoixExpandableRenderer.EVENT_EXPAND;
            renderer.change(event,null);
            expandableRenderer.setExpanded(!expandableRenderer.isExpanded());
            TransitionManager.beginDelayedTransition(recyclerView);
            notifyDataSetChanged();

        }

        @Override
        public void OnEvent(int type, Object content) {
            int pos=itemRenderer.getPos();
            if(type==RoixExpandableRenderer.COMMAND_COLLAPSE){
                handleExpand((RoixExpandableRenderer) renderer);
            }
            if(pairs!=null&&pos>=0&&pos<pairs.size()){
                listener.onEvent(type,content,pairs.get(pos).second,pos);
            }
        }

        public RoixRenderer getItemRenderer() {
            return itemRenderer;
        }

        public void setItemRenderer(RoixRenderer itemRenderer) {
            this.itemRenderer = itemRenderer;
        }
    }

}