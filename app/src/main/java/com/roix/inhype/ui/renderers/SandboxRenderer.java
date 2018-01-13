package com.roix.inhype.ui.renderers;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.SandboxItemBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.roix.inhype.ui.adapters.SandboxPagerAdapter;

/**
 * Created by roix on 24.06.2017.
 */

public class SandboxRenderer extends RoixRecyclerAdapter.RoixRenderer implements RoixRecyclerAdapter.EventListener{

    SandboxItemBinding binding;
    RoixRecyclerAdapter adapter;
    RoixRecyclerAdapter.ItemEventListener listener;
    public static final int EVENT_SCROLLED=1;
    @Override
    public int getResID() {
        return R.layout.sandbox_item;
    }

    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        this.listener=listener;
        adapter=new RoixRecyclerAdapter(binding.getRoot().getContext(),binding.rv,this);
        binding.rv.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rv.setAdapter(adapter);
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        PhotosCollection collection= (PhotosCollection) item;
        adapter.addItems(collection.getList(),ProfileRenderer.class);
        int category=collection.getCategory();
        Pair<Integer,Integer> pair=DataConverter.getCategoryContentById(category);
        binding.categoryImage.setImageResource(pair.first);
        binding.categoryTv.setText(pair.second);
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {
        PhotosCollection collection= (PhotosCollection) item;
        adapter.addItems(collection.getList(),ProfileRenderer.class);
    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        if(type==ProfileRenderer.EVENT_CLICKED){
            listener.OnEvent(type,item);
        }
    }
}
