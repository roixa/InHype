package com.roix.inhype.ui.renderers;

import android.databinding.DataBindingUtil;
import android.util.Pair;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.ProfileItemBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.ProfileShort;
import com.squareup.picasso.Picasso;

/**
 * Created by roix on 01.07.2017.
 */

public class SubscribersRenderer extends RoixRecyclerAdapter.RoixRenderer {
    ProfileItemBinding binding;

    @Override
    public int getResID() {
        return R.layout.profile_item;
    }

    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        binding.actionIcon.setOnClickListener(view -> listener.OnEvent(0,null));
        binding.getRoot().setOnClickListener(v1 -> listener.OnEvent(1,null));
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        ProfileShort profile= (ProfileShort) item;
        Picasso.with(binding.getRoot().getContext()).load(profile.getAvatar()).resize(50, 50).into(binding.avatar);
        binding.name.setText(profile.getNickname());
        int  resLevel= DataConverter.getLevelResLevel(profile.getRating());
        binding.levelIcon.setImageResource(resLevel);
        binding.levelText.setText(profile.getRating()+"");
        updateSubButton(profile);
    }

    private void updateSubButton(ProfileShort profile){
        boolean dub=profile.isHasReciprocity();
        boolean isSubscribersList=profile.isSubscriber();
        if(isSubscribersList){
            int res=dub?R.drawable.check_mark:R.drawable.add;
            binding.actionIcon.setImageResource(res);

        }
        else if(dub)
            binding.actionIcon.setImageResource(R.drawable.check_mark);
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }
}
