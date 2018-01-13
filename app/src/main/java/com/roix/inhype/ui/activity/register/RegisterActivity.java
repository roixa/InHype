package com.roix.inhype.ui.activity.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.roix.inhype.MyApplication;
import com.roix.inhype.R;
import com.roix.inhype.databinding.ActivityRegisterBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.presentation.view.register.RegisterView;
import com.roix.inhype.presentation.presenter.register.RegisterPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.root.RootActivity;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends MvpActivity implements RegisterView {
    public static final String TAG = "RegisterActivity";
    @InjectPresenter
    RegisterPresenter mRegisterPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        binding.setPresenter(mRegisterPresenter);
    }

    @Override
    public void prepareRootState() {
        Intent intent= RootActivity.getIntent(this);
        startActivity(intent);
    }


    @Override
    public void showEmailConfirmDialog() {
        final RegisterActivity context=this;
        new AlertDialog.Builder(context).setTitle("Please confirm email and login").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        }).create().show();

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
