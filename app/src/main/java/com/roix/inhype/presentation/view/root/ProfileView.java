package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;

import java.util.List;

public interface ProfileView extends BaseView {
    void fillProfileInfo(Profile profile,boolean isMyProfile,boolean isSubscribe);
    void onLoadProfilePhotos(List<Photo> photos);
    void showToast(String s);

}
