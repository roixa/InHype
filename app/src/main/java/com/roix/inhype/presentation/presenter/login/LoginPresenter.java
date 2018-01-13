package com.roix.inhype.presentation.presenter.login;

import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.presentation.view.login.LoginView;
import com.arellomobile.mvp.InjectViewState;


@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {


    private String login;
    private String password;


    public void onLoginButtonClicked(){
        Log.d("LoginPresenter","onLoginButtonClicked ");

        sub(rep.checkRegisterDevice(), () ->
                sub(rep.login(login, password), loginResponse -> {
                    getViewState().showToast("login successful");
                    getViewState().prepareRootState();
                })
        );


    }

    public void onRegisterButtonClicked(){
        Log.d("LoginPresenter","onRegisterButtonClicked ");
        getViewState().prepareRegisterState( );
    }

    public void onLoginTextChanged(CharSequence s, int start, int before, int count) {
        Log.w("LoginPresenter", "onLoginTextChanged " + s);
        login=s.toString();
    }

    public void onPasswordTestChanged(CharSequence s, int start, int before, int count){
        Log.w("LoginPresenter", "onPasswordTestChanged " + s);
        password=s.toString();
    }

    public void onRecoverPasswordButtonClicked(){
        getViewState().prepareRecoverPasswordState();
    }

}
