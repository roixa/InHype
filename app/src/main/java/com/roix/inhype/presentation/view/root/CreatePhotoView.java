package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;

public interface CreatePhotoView extends BaseView {
    void openChoosePhotoGallery();
    void onUploadPhoto(String url);
    void showToast(String s);
    void prepareChooseListState();

}
