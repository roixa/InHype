package com.roix.inhype;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.roix.inhype.databinding.ActivityMainBinding;
import com.roix.inhype.ui.activity.login.LoginActivity;
import com.roix.inhype.ui.activity.register.RegisterActivity;
import com.roix.inhype.ui.activity.root.RootActivity;


public class MainActivity extends AppCompatActivity implements OnClickListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.loginButton.setOnClickListener(this);
        binding.registerButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.getContentRepository().isLoginIn()){
            startActivity(RootActivity.getIntent(this));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_button){
            startActivity(LoginActivity.getIntent(this));
        }else if(v.getId()==R.id.register_button){
            startActivity(RegisterActivity.getIntent(this));
        }
    }
}

