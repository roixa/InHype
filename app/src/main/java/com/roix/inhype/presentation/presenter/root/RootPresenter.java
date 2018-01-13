package com.roix.inhype.presentation.presenter.root;


import android.util.Log;

import com.roix.inhype.MyApplication;
import com.roix.inhype.R;
import com.roix.inhype.presentation.view.root.RootView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class RootPresenter extends MvpPresenter<RootView> {

    private String TAG="RootPresenter";

    public void init(){
        getViewState().prepareBestTab();
    }

    public void navigationDrawerClicked( int id){
        Log.d(TAG,"navigationDrawerClicked");
        switch (id){
            case R.id.nav_best:
                getViewState().prepareBestTab();
                break;
            case R.id.nav_choose:
                getViewState().prepareChooseTab();
                break;
            case R.id.nav_hype:
                getViewState().prepareHypeTab();
                break;
            case R.id.nav_lenta:
                getViewState().prepareFeedTab();
                break;
            case R.id.nav_profile:
                getViewState().prepareProfileTab();
                break;
            case R.id.nav_settings:
                getViewState().prepareSettingsTab();
                break;
            case R.id.nav_help:
                getViewState().prepareHelpTab();
                break;
            case R.id.nav_favorites:
                getViewState().prepareFavoritesTab();
                break;

        }
    }

    public void editToolbarButtonPressed(){
        getViewState().prepareSettingsTab();
    }


    public String getOwnerAvaUrl(){
        return MyApplication.getContentRepository().getOwnerAvatar();
    }

    public void backButtonPressed(){}

}
