package com.roix.inhype.presentation.presenter.root;


import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.presentation.view.root.MyFeedView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MyFeedPresenter extends BasePresenter<MyFeedView> {

    public void init(){
        if(rep==null)rep= MyApplication.getContentRepository();
        sub(rep.getNews(1,10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())),response -> {
           getViewState().onLoadPhotos(response);
        });
    }

    public void loadMore(int page){
        if(rep==null)rep= MyApplication.getContentRepository();
        sub(rep.getNews(page+1,10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())),response -> {
            getViewState().onLoadPhotos(response);
        });

    }
    public void likePhoto(Photo photo, final int pos){
        sub(rep.likePhoto(photo.getId(), photo.getConfirmed()), likePhotoResponse ->
                getViewState().handleLikeButtonInList(true,likePhotoResponse.getLikes(),likePhotoResponse.getComments(),pos)
        );
    }

    public void commentsButtonClicked(Photo photo){
        getViewState().prepareCommentsState(photo);
    }


}
