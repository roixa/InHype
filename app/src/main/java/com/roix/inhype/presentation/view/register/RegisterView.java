package com.roix.inhype.presentation.view.register;

import com.roix.inhype.BaseView;

public interface RegisterView extends BaseView {
    void prepareRootState();
    void showEmailConfirmDialog();
    void showToast(String s);

}
