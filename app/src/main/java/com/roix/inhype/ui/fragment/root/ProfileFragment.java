package com.roix.inhype.ui.fragment.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.FragmentProfileBinding;
import com.roix.inhype.databinding.FragmentSettingsBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.presentation.view.root.ProfileView;
import com.roix.inhype.presentation.presenter.root.ProfilePresenter;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.profilelist.ProfileListActivity;
import com.roix.inhype.ui.renderers.ChooseListItemRenderer;
import com.roix.inhype.ui.renderers.ProfileRenderer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends BaseSupportFragment implements ProfileView,RoixRecyclerAdapter.EventListener {

    public static final String TAG = "ProfileFragment";
    @InjectPresenter
    ProfilePresenter mProfilePresenter;

    FragmentProfileBinding binding;
    RoixRecyclerAdapter adapter;

    public static ProfileFragment newInstance(int profileId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt("id",profileId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_profile, container, false);
        binding.setPresenter(mProfilePresenter);
        adapter=new RoixRecyclerAdapter(getActivity(),binding.recyclerView,this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.recyclerView.setAdapter(adapter);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int id=getArguments().getInt("id",0);
        binding.subscribersFrame.setOnClickListener(v -> ProfileListActivity.start(getActivity(),id,ProfileListActivity.TYPE_SUBSCRIBERS));
        binding.subscribtionsFrame.setOnClickListener(v -> ProfileListActivity.start(getActivity(),id,ProfileListActivity.TYPE_SUBSCRIPTIONS));

        mProfilePresenter.init(id);
        mProfilePresenter.loadProfilePhotos(0);
    }

    @Override
    public void fillProfileInfo(Profile profile,boolean isMyProfile,boolean isSubscribe) {

        binding.extraTextView.setText(profile.getStatus());
        binding.nickname.setText(profile.getNickName());
        binding.levelTextView.setText(profile.getRating()+"");
        binding.subscribersTextView.setText(profile.getSubscribers()+"");
        binding.subscribtionsTextView.setText(profile.getSubscriptions()+"");
        Picasso.with(getActivity()).load(profile.getAvatar()).resize(150, 150).into(binding.profileImage);
        binding.levelImage.setImageResource(DataConverter.getLevelResLevel(profile.getRating()));
        Log.d("ProfileFragment"," profile.isSubscribe "+isSubscribe);
        binding.subscribeFrame.setBackgroundResource(isSubscribe?R.drawable.ok_subs_bg:R.drawable.bg_subs_make);
        binding.subscribeTitle.setVisibility(isSubscribe?View.GONE:View.VISIBLE);
        binding.unsubscrbieTitle.setVisibility(isSubscribe?View.VISIBLE:View.GONE);
        binding.subscribeFrame.setVisibility(isMyProfile?View.GONE:View.VISIBLE);

    }

    @Override
    public void onLoadProfilePhotos(List<Photo> photos) {
        Log.d(TAG,"onLoadProfilePhotos"+photos.size());
        adapter.addItems(photos,ProfileRenderer.class);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        prepareCommentsState((Photo) item);
    }

    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

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
