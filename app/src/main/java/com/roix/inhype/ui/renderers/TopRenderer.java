package com.roix.inhype.ui.renderers;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.TopItemBinding;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.inner.ListPhotoDataList;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.roix.inhype.ui.adapters.TopPagerAdapter;

/**
 * Created by roix on 18.06.2017.
 */

public class TopRenderer extends RoixRecyclerAdapter.RoixRenderer {
    TopItemBinding binding;
    RoixRecyclerAdapter.ItemEventListener listener;
    @Override
    public int getResID() {
        return R.layout.top_item;
    }

    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        this.listener=listener;
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        if (!(item instanceof PhotosCollection))return;
        PhotosCollection response= (PhotosCollection) item;
        FragmentManager fragmentManager = ((FragmentActivity) binding.getRoot().getContext()).getSupportFragmentManager();
        TopPagerAdapter adapter=new TopPagerAdapter(fragmentManager,response.getList(),listener,binding.pager);
        binding.pager.setAdapter(adapter);
        binding.indicator.setViewPager(binding.pager);
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }
}
