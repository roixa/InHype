package com.roix.inhype.presentation.presenter.root;


import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.presentation.view.root.SandboxView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

@InjectViewState
public class SandboxPresenter extends BasePresenter<SandboxView> {

    public void loadChooseFirstPagePhotos(){
        if(rep==null)rep= MyApplication.getContentRepository();
        /*
        sub(rep.getSelection(0, 1, 10), response -> {
            getViewState().onLoadItems(response);
        });
        */
        sub(rep.getSelectionFromAllCategories(10), responseBody -> {
            getViewState().onLoadItems(responseBody);
        });

    }
}
