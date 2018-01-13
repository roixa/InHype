package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.GetAllSelectionCategoriesResponse;
import com.roix.inhype.pojo.GetSelectionListResponse;

public interface SandboxView extends BaseView {
    void onLoadItems(GetAllSelectionCategoriesResponse photos);
}
