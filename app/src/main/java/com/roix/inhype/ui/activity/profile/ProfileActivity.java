package com.roix.inhype.ui.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roix.inhype.R;
import com.roix.inhype.databinding.ActivityProfileBinding;
import com.roix.inhype.ui.activity.root.RootActivity;
import com.roix.inhype.ui.fragment.root.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    public static Intent getIntent(final Context context,int profileId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("profileId",profileId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.appBar.toolbar.setTitle("");
        setSupportActionBar(binding.appBar.toolbar);
        int profileId=getIntent().getIntExtra("profileId",0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ProfileFragment.newInstance(profileId)).commit();

    }
}
