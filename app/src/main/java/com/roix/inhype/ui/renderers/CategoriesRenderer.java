package com.roix.inhype.ui.renderers;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.roix.inhype.CustomLinearLayoutManager;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.CategoriesItemBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.model.DataRepository;
import com.roix.inhype.pojo.Category;
import com.roix.inhype.pojo.LoginResponse;

import java.util.List;

/**
 * Created by roix on 17.06.2017.
 */

public class CategoriesRenderer extends RoixRecyclerAdapter.RoixExpandableRenderer {
    public static final int EVENT_CATEGORY_CHOOSED=3;

    public CategoriesItemBinding binding;
    String TAG="CategoriesRenderer";
    RoixRecyclerAdapter.ItemEventListener listener;
    Context context;


    @Override
    public int getResID() {
        return R.layout.categories_item;
    }


    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        binding.setRend(this);
        this.listener=listener;
        context=v.getContext();
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {

    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {
        Log.d(TAG,"change "+eventType);

        if(eventType==EVENT_EXPAND){
            binding.expanded.setVisibility(View.VISIBLE);
            binding.headerArrow.setImageResource(R.drawable.arrow_up);
        }
        else if(eventType==EVENT_COLLAPSE){
            binding.headerArrow.setImageResource(R.drawable.arrow_down);
            binding.expanded.setVisibility(View.GONE);
        }
    }


    @Override
    public View getNotExpandedPart() {
        return binding.nonExpanded;
    }

    public void handleClick(int serverId){
        Pair<Integer,Integer> pair= DataConverter.getCategoryContentById(serverId);
        listener.OnEvent(COMMAND_COLLAPSE,null);
        binding.headerText.setText(pair.second);
        binding.headerIcon.setImageResource(pair.first);
        listener.OnEvent( EVENT_CATEGORY_CHOOSED,serverId);
    }

    public void onClickCategoryNo(){
        handleClick(0);
    }
    public void onClickCategoryAnimal(){
        handleClick(1);
    }
    public void onClickCategoryFood(){
        handleClick(2);
    }
    public void onClickCategoryNature(){
        handleClick(3);
    }
    public void onClickCategorySport(){
        handleClick(4);
    }
    public void onClickCategoryGames(){
        handleClick(5);
    }
    public void onClickCategoryCosplay(){
        handleClick(6);
    }
    public void onClickCategoryPeoples(){
        handleClick(7);
    }
    public void onClickCategoryBeauty(){
        handleClick(8);
    }
    public void onClickCategoryPrestige(){
        handleClick(9);
    }
    public void onClickCategoryActions(){
        handleClick(10);
    }
    public void onClickCategoryErotic(){
        handleClick(11);
    }

}
