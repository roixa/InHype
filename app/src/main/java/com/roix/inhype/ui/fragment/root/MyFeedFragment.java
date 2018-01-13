package com.roix.inhype.ui.fragment.root;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.EndlessScrollListener;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.presentation.view.root.MyFeedView;
import com.roix.inhype.presentation.presenter.root.MyFeedPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.profile.ProfileActivity;
import com.roix.inhype.ui.renderers.ChooseListItemRenderer;

import java.util.List;

public class MyFeedFragment extends BaseSupportFragment implements MyFeedView,RoixRecyclerAdapter.EventListener {
    public static final String TAG = "MyFeedFragment";
    @InjectPresenter
    MyFeedPresenter mMyFeedPresenter;

    RoixRecyclerAdapter adapter;

    public static MyFeedFragment newInstance() {
        MyFeedFragment fragment = new MyFeedFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_feed, container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.rv);
        adapter=new RoixRecyclerAdapter(getActivity(),recyclerView,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mMyFeedPresenter.loadMore(page);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyFeedPresenter.init();

    }
    @Override
    public void handleLikeButtonInList(boolean isLike,String likes,String comments,int pos) {
        Photo photo=((Photo) adapter.getItem(pos));
        photo.setLikedByMe(isLike);
        photo.setLikes(Integer.parseInt(likes));
        photo.setComments(Integer.parseInt(comments));
        adapter.notifyItemChanged(pos);
    }

    @Override
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


    @Override
    public void onLoadPhotos(List<Photo> photos) {
        adapter.addItems(photos, ChooseListItemRenderer.class);
    }

    public void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        Photo photo= (Photo) item;
        if(type==ChooseListItemRenderer.EVENT_CLICK_LIKE){
            mMyFeedPresenter.likePhoto(photo,pos);
        }
        else if(type==ChooseListItemRenderer.EVENT_CLICK_COMMENT){
            mMyFeedPresenter.commentsButtonClicked(photo);
        }
        if(type==ChooseListItemRenderer.EVENT_CLICK_PROFILE){
            int profileId= (int) content;
            startActivity(ProfileActivity.getIntent(getActivity(),profileId));
        }
    }
}
