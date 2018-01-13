package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;

public interface RootView extends MvpView {

    void prepareBestTab();
    void prepareChooseTab();
    void prepareHypeTab();
    void prepareFeedTab();
    void prepareProfileTab();
    void prepareSettingsTab();
    void prepareHelpTab();
    void prepareFavoritesTab();

    void showToast(String s);

}
