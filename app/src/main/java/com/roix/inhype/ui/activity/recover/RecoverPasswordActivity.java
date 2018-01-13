package com.roix.inhype.ui.activity.recover;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.roix.inhype.R;
import com.roix.inhype.databinding.ActivityLoginBinding;
import com.roix.inhype.databinding.ActivityRecoverPasswordBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.presentation.view.recover.RecoverPasswordView;
import com.roix.inhype.presentation.presenter.recover.RecoverPasswordPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;

public class RecoverPasswordActivity extends MvpActivity implements RecoverPasswordView {
    public static final String TAG = "RecoverPasswordActivity";
    @InjectPresenter
    RecoverPasswordPresenter mRecoverPasswordPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, RecoverPasswordActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecoverPasswordBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_recover_password);
        binding.setPresenter(mRecoverPasswordPresenter);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }
}
