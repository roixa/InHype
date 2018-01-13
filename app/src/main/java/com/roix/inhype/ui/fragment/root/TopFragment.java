package com.roix.inhype.ui.fragment.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.FragmentTopBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.ui.activity.profile.ProfileActivity;
import com.roix.inhype.ui.renderers.ChooseListItemRenderer;
import com.squareup.picasso.Picasso;

import static com.roix.inhype.ui.renderers.ChooseListItemRenderer.EVENT_CLICK_PROFILE;

/**
 * Created by roix on 18.06.2017.
 */

public class TopFragment extends Fragment {

    FragmentTopBinding binding;

    RoixRecyclerAdapter.ItemEventListener listener;

    public static TopFragment newInstance(Photo photo, RoixRecyclerAdapter.ItemEventListener listener){

        String json=new Gson().toJson(photo);
        TopFragment fragment=new TopFragment();

        fragment.listener=listener;
        Bundle bundle=new Bundle();
        bundle.putString("json",json);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top,container,false);
        String json=getArguments().getString("json","");
        Photo photo=new Gson().fromJson(json,Photo.class);
        Picasso.with(binding.getRoot().getContext()).load(photo.getPreview()).into(binding.photo);
        binding.likesCount.setText(photo.getLikes()+"");
        binding.commentsCount.setText(photo.getComments()+"");
        int id=photo.isLikedByMe()?R.drawable.heart_pressed:R.drawable.heart;
        binding.likeFrame.setOnClickListener(v -> {
            if(!photo.isLikedByMe())
                listener.OnEvent(ChooseListItemRenderer.EVENT_CLICK_LIKE,photo);
        });

        binding.commentFrame.setOnClickListener(v ->
                //listener.OnEvent(ChooseListItemRenderer.EVENT_CLICK_COMMENT,photo)
                prepareCommentsState(photo)
        );

        binding.likeButton.setImageResource(id);
        if(photo.getProfile()!=null){
            binding.profileName.setText(photo.getProfile().getNickname());
            Picasso.with(binding.getRoot().getContext()).load(photo.getProfile().getAvatar()).resize(50, 50).into(binding.icAvatar);
            binding.profileFrame.setOnClickListener(v -> {
                //listener.OnEvent(ChooseListItemRenderer.EVENT_CLICK_PROFILE,photo);
                startActivity(ProfileActivity.getIntent(getActivity(),photo.getProfile().getId()));
            });
            binding.icLevel.setImageResource(DataConverter.getLevelResLevel(photo.getProfile().getRating()));
            binding.tvLevel.setText(photo.getProfile().getRating()+"");
        }


        return binding.getRoot();
    }

    public void prepareCommentsState(Photo photo) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        CommentsFragment newFragment = CommentsFragment.newInstance(photo);
        newFragment.show(ft, "dialog");
    }

}
