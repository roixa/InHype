package com.roix.inhype.presentation.presenter.register;


import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.RegisterProfileResponse;
import com.roix.inhype.presentation.view.register.RegisterView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class RegisterPresenter extends BasePresenter<RegisterView> {

    private String login;
    private String password;
    private String email;



    public void onRegisterButtonClicked(){
        sub(rep.checkRegisterDevice(), () ->
                sub(rep.registerProfile(login, email, password), registerProfileResponse -> getViewState().showEmailConfirmDialog())
        );

    }

    public void onLoginTextChanged(CharSequence s, int start, int before, int count) {
        Log.w("RegisterPresenter", "onLoginTextChanged " + s);
        login=s.toString();
    }
    public void onPasswordTestChanged(CharSequence s, int start, int before, int count){
        Log.w("RegisterPresenter", "onPasswordTestChanged " + s);
        password=s.toString();
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count){
        Log.w("RegisterPresenter", "onEmailTextChanged " + s);
        email=s.toString();

    }

}
