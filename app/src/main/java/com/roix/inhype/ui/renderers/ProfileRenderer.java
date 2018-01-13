package com.roix.inhype.ui.renderers;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.ChoosePhotoItemBinding;
import com.roix.inhype.databinding.ProfileListItemBinding;
import com.roix.inhype.pojo.Photo;
import com.squareup.picasso.Picasso;

/**
 * Created by roix on 31.05.2017.
 */

public class ProfileRenderer extends RoixRecyclerAdapter.RoixRenderer {
    ProfileListItemBinding binding;
    Context context;
    public static final int EVENT_CLICKED=0;
    @Override
    public int getResID() {
        return R.layout.profile_list_item;
    }


    @Override
    public void create(View v, final RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        context=v.getContext();
        v.setOnClickListener(v1 -> listener.OnEvent(EVENT_CLICKED,null));
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        Photo photo= (Photo) item;
        Picasso.with(context).load(photo.getPreview()).into(binding.photo);

    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }


}
