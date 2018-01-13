package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.Photo;

import java.util.List;

public interface FavoritesView extends BaseView {
    void onLoadPhotos(List<Photo> photos);
    void handleLikeButtonInList(boolean isLike,String likes,String comments,int pos);
    void prepareCommentsState(Photo photo);

}
