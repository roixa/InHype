package com.roix.inhype;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.MvpDelegate;

/**
 * Created by roix on 17.05.2017.
 */

public class BaseActivity extends AppCompatActivity {
    private MvpDelegate<? extends MvpActivity> mMvpDelegate;

    public BaseActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMvpDelegate().onCreate(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.getMvpDelegate().onDetach();
        if(this.isFinishing()) {
            this.getMvpDelegate().onDestroy();
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.getMvpDelegate().onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();
        this.getMvpDelegate().onAttach();
    }

    public MvpDelegate getMvpDelegate() {
        if(this.mMvpDelegate == null) {
            this.mMvpDelegate = new MvpDelegate(this);
        }

        return this.mMvpDelegate;
    }
}
