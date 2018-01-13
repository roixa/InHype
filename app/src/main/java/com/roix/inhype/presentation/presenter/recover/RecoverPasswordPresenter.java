package com.roix.inhype.presentation.presenter.recover;


import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.presentation.view.recover.RecoverPasswordView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class RecoverPasswordPresenter extends BasePresenter<RecoverPasswordView> {
    private String email;

    public void onRecoverPasswordButtonClicked(){
        sub(rep.recoverPassword(email), responseBody -> getViewState().showToast("ok"));
        //rep.recoverPassword(email).subscribe(responseBody -> getViewState().showToast("ok"),this::handleError);
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count){
        email=s.toString();
    }

}
