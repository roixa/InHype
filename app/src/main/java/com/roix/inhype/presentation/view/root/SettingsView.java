package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.Profile;

public interface SettingsView extends BaseView {

    void fillProfileInfo(Profile profile);
    void openChoosePhotoGallery();
    void onUploadAva(String url);
    void showToast(String s);
}
