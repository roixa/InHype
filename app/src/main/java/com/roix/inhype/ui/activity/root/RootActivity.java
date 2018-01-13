package com.roix.inhype.ui.activity.root;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.roix.inhype.BaseActivity;
import com.roix.inhype.R;
import com.roix.inhype.databinding.ActivityLoginBinding;
import com.roix.inhype.databinding.ActivityRootBinding;
import com.roix.inhype.presentation.view.root.RootView;
import com.roix.inhype.presentation.presenter.root.RootPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.fragment.root.CameraContainerFragment;
import com.roix.inhype.ui.fragment.root.ChooseListFragment;
import com.roix.inhype.ui.fragment.root.CreatePhotoFragment;
import com.roix.inhype.ui.fragment.root.FavoritesFragment;
import com.roix.inhype.ui.fragment.root.MyFeedFragment;
import com.roix.inhype.ui.fragment.root.ProfileFragment;
import com.roix.inhype.ui.fragment.root.SandboxFragment;
import com.roix.inhype.ui.fragment.root.SettingsFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;


public class RootActivity extends BaseActivity implements RootView, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "RootActivity";

    @InjectPresenter
    RootPresenter mRootPresenter;

    private ActionBarDrawerToggle toggle;
    private ActivityRootBinding binding;


    private MenuItem editButton;
    private MenuItem avaButton;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, RootActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_root);
        binding.setPresenter(mRootPresenter);
        binding.navView.bringToFront();
        binding.appBar.toolbar.setTitle("");
        setSupportActionBar(binding.appBar.toolbar);

        binding.navView.setNavigationItemSelectedListener(this);
        ColorStateList colorStateList=new ColorStateList(
                new int [] [] {
                        new int [] {android.R.attr.state_checked},
                        new int [] {}
                },
                new int [] {
                        Color.MAGENTA,
                        Color.BLACK
                }
        );

        binding.navView.setItemTextColor(colorStateList);
        binding.navView.setItemIconTintList(colorStateList);

        DrawerLayout drawer = binding.drawerLayout;
        binding.appBar.toolbar.setNavigationIcon(R.drawable.menu_button);
        toggle = new ActionBarDrawerToggle(
                this, drawer, binding.appBar.toolbar, R.string.app_name, R.string.app_name);

        drawer.addDrawerListener(toggle);

        mRootPresenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root_toolbar, menu);
        editButton=menu.findItem(R.id.edit);
        avaButton=menu.findItem(R.id.ava);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        View view=  avaButton.getActionView();
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.ava_view);
        Picasso.with(this).load(mRootPresenter.getOwnerAvaUrl()).resize(50, 50).into(circleImageView);
        handleToolbarIcons(false);
        return true;
    }

    private void handleToolbarIcons(boolean isEditTab){
        if(avaButton!=null)
            avaButton.setVisible(!isEditTab);
        if(editButton!=null)
            editButton.setVisible(isEditTab);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.edit){
            mRootPresenter.editToolbarButtonPressed();
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mRootPresenter.backButtonPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG,"onNavigationItemSelected");
        mRootPresenter.navigationDrawerClicked(item.getItemId());
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void prepareBestTab() {
        handleToolbarIcons(false);
        binding.navView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ChooseListFragment.newInstance()).commit();

    }

    @Override
    public void prepareChooseTab() {
        handleToolbarIcons(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SandboxFragment.newInstance()).commit();
        binding.navView.getMenu().getItem(1).setChecked(true);


    }

    @Override
    public void prepareHypeTab() {
        handleToolbarIcons(false);

        binding.navView.getMenu().getItem(2).setChecked(true);
        //getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CreatePhotoFragment.newInstance()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CameraContainerFragment.newInstance()).commit();

    }

    @Override
    public void prepareFeedTab() {
        handleToolbarIcons(false);
        binding.navView.getMenu().getItem(3).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, MyFeedFragment.newInstance()).commit();

    }

    @Override
    public void prepareProfileTab() {
        handleToolbarIcons(true);

        binding.navView.getMenu().getItem(4).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ProfileFragment.newInstance(0)).commit();

    }

    @Override
    public void prepareSettingsTab() {
        handleToolbarIcons(false);
        binding.navView.getMenu().getItem(5).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SettingsFragment.newInstance()).commit();

    }

    @Override
    public void prepareHelpTab() {
        showEmailChooser(this);
    }

    @Override
    public void prepareFavoritesTab() {
        handleToolbarIcons(false);
        binding.navView.getMenu().getItem(7).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, FavoritesFragment.newInstance()).commit();
    }

    public static void showEmailChooser(Context context){
        String email=context.getString(R.string.email_support);
        String title=context.getString(R.string.user_support);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.setData(Uri.parse("mailto: "+email));
        context.startActivity(Intent.createChooser(emailIntent, title));
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
