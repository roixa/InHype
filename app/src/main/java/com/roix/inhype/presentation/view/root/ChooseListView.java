package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.ListPhotoData;
import com.roix.inhype.pojo.inner.ListPhotoDataList;

import java.util.List;

public interface ChooseListView extends BaseView {
    void onLoadItems(GetSelectionListResponse photos);
    void onLoadItems(List<Photo> photos);
    void handleLikeButtonInList(boolean isLike,String likes,String comments,int pos);
    void prepareCommentsState(Photo photo);
    void showToast(String s);
    void updateItems(GetSelectionListResponse photos);
    void clearCategoryContent();
    void setupTopList(List<Photo> photos);

}
