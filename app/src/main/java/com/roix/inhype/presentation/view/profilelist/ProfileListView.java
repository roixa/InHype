package com.roix.inhype.presentation.view.profilelist;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.ProfileShort;

import java.util.List;

public interface ProfileListView extends BaseView {
    void onLoadProfiles(List<ProfileShort> list);
    void updateItemUi(boolean isSubscribes,boolean wasHasReciprocity,int pos);
}
