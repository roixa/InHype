package com.roix.inhype.ui.activity.profilelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.roix.inhype.BaseActivity;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.ProfileShort;
import com.roix.inhype.presentation.view.profilelist.ProfileListView;
import com.roix.inhype.presentation.presenter.profilelist.ProfileListPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.profile.ProfileActivity;
import com.roix.inhype.ui.renderers.SubscribersRenderer;

import java.util.List;

public class ProfileListActivity extends BaseActivity implements ProfileListView,RoixRecyclerAdapter.EventListener {

    public static final String TAG = "ProfileListActivity";

    @InjectPresenter
    ProfileListPresenter mProfileListPresenter;
    RoixRecyclerAdapter adapter;


    public static final int TYPE_SUBSCRIBERS=0;
    public static final int TYPE_SUBSCRIPTIONS=1;

    public static Intent getIntent(final Context context,int profileId,int type) {
        Intent intent = new Intent(context, ProfileListActivity.class);
        intent.putExtra("profileId",profileId);
        intent.putExtra("type",type);
        return intent;
    }

    public static void start(Context context,int profileId,int type){
        Intent intent=getIntent(context,profileId,type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RoixRecyclerAdapter(this,recyclerView,this);
        recyclerView.setAdapter(adapter);
        int profileId=getIntent().getIntExtra("profileId",0);
        int type=getIntent().getIntExtra("type",0);
        mProfileListPresenter.init(type,profileId);

    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        if(type==0)mProfileListPresenter.handleSubscribeClick((ProfileShort) item,pos);
        else if(type==1){
            startActivity(ProfileActivity.getIntent(this,((ProfileShort) item).getId()));
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleProgress(boolean isProgress) {

    }

    @Override
    public void onLoadProfiles(List<ProfileShort> list) {
        Log.d("ProfileListActivity","onLoadProfiles "+list.size());
        int type=getIntent().getIntExtra("type",0);
        adapter.addItems(list, SubscribersRenderer.class);

    }

    @Override
    public void updateItemUi(boolean isSubscribes,boolean wasHasReciprocity, int pos) {
        ProfileShort profileShort=((ProfileShort) adapter.getItem(pos));
        profileShort.setSubscribes(isSubscribes);
        profileShort.setHasReciprocity(!wasHasReciprocity);
        adapter.notifyItemChanged(pos);

    }
}
