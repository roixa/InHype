package com.roix.inhype.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.roix.inhype.R;
import com.roix.inhype.databinding.ActivityLoginBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.presentation.view.login.LoginView;
import com.roix.inhype.presentation.presenter.login.LoginPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.recover.RecoverPasswordActivity;
import com.roix.inhype.ui.activity.register.RegisterActivity;
import com.roix.inhype.ui.activity.root.RootActivity;

public class LoginActivity extends MvpActivity implements LoginView {
    public static final String TAG = "LoginActivity";
    @InjectPresenter
    LoginPresenter mLoginPresenter;
    ActivityLoginBinding binding;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        ActivityLoginBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setPresenter(mLoginPresenter);

    }

    @Override
    public void showProgress(boolean b) {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void prepareRegisterState() {
        Intent intent= RegisterActivity.getIntent(this);
        startActivity(intent);
    }

    @Override
    public void prepareRootState() {
        Intent intent= RootActivity.getIntent(this);
        startActivity(intent);

    }

    @Override
    public void prepareRecoverPasswordState() {
        Intent intent = RecoverPasswordActivity.getIntent(this);
        startActivity(intent);
    }

    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }
}
