package com.roix.inhype.presentation.view.login;

import com.roix.inhype.BaseView;

public interface LoginView extends BaseView {
    void showProgress(boolean b);
    void showToast(String s);
    void prepareRegisterState();
    void prepareRootState();
    void prepareRecoverPasswordState();
}
