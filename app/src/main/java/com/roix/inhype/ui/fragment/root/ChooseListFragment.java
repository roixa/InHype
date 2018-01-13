package com.roix.inhype.ui.fragment.root;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.EndlessScrollListener;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.ListPhotoData;
import com.roix.inhype.pojo.inner.ListPhotoDataList;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.roix.inhype.presentation.presenter.root.RootPresenter;
import com.roix.inhype.presentation.view.root.ChooseListView;
import com.roix.inhype.presentation.presenter.root.ChooseListPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.profile.ProfileActivity;
import com.roix.inhype.ui.renderers.CategoriesRenderer;
import com.roix.inhype.ui.renderers.ChooseListItemRenderer;
import com.roix.inhype.ui.renderers.TopRenderer;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

public class ChooseListFragment extends BaseSupportFragment implements ChooseListView,RoixRecyclerAdapter.EventListener {
    public static final String TAG = "ChooseListFragment";

    @InjectPresenter
    ChooseListPresenter mChooseListPresenter;

    RoixRecyclerAdapter  adapter;

    public static ChooseListFragment newInstance() {
        ChooseListFragment fragment = new ChooseListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        setRetainInstance(false);
        View view=inflater.inflate(R.layout.fragment_choose_list, container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter=new RoixRecyclerAdapter(getActivity(),recyclerView,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mChooseListPresenter.loadMore(page);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();
        mChooseListPresenter.init();

    }

    @Override
    public void onLoadItems(GetSelectionListResponse response) {
        //adapter.addItems(photos,new ChooseListItemRenderer());

        //adapter.addItems(response.getPhotos(), ChooseListItemRenderer.class);
    }

    @Override
    public void onLoadItems(List<Photo> datas) {
        adapter.addItems(datas,ChooseListItemRenderer.class);
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
    public void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void updateItems(GetSelectionListResponse photos) {
        adapter.clearData(ChooseListItemRenderer.class);
        adapter.addItems(photos.getPhotos(), ChooseListItemRenderer.class);

    }

    @Override
    public void clearCategoryContent() {
        adapter.clearData(ChooseListItemRenderer.class);
    }

    @Override
    public void setupTopList(List<Photo> photos) {
        adapter.clearData();
        adapter.addItem(new PhotosCollection(photos,0), TopRenderer.class);
        adapter.addItem(mChooseListPresenter.getLoginInfo(), CategoriesRenderer.class);
    }


    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        Log.d("ChooseListFragment","onEvent "+type);
        if(type==CategoriesRenderer.EVENT_CATEGORY_CHOOSED){
            int id= (int) content;
            mChooseListPresenter.changeCategory(id);
        }
        if(item instanceof Photo){
            Photo photo= (Photo) item;
            if(type==ChooseListItemRenderer.EVENT_CLICK_LIKE){
                mChooseListPresenter.likePhoto(photo,pos);
            }
            else if(type==ChooseListItemRenderer.EVENT_CLICK_COMMENT){
                mChooseListPresenter.commentsButtonClicked(photo);
            }
            if(type==ChooseListItemRenderer.EVENT_CLICK_PROFILE){
                int profileId= (int) content;
                startActivity(ProfileActivity.getIntent(getActivity(),profileId));
            }

        }

        else if(content instanceof Photo){
            Photo photo= (Photo) content;
            if(type==ChooseListItemRenderer.EVENT_CLICK_LIKE){
                mChooseListPresenter.likePhoto(photo,pos);
            }
            else if(type==ChooseListItemRenderer.EVENT_CLICK_COMMENT){
                //mChooseListPresenter.commentsButtonClicked(photo);
            }
            if(type==ChooseListItemRenderer.EVENT_CLICK_PROFILE){
                //startActivity(ProfileActivity.getIntent(getActivity(),photo.getProfile().getId()));
            }
        }

    }


    @Override
    public void handleProgress(boolean isProgress) {

    }
}
