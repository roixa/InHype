package com.roix.inhype.ui.activity.preparephoto;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.internal.enums.MediaAction;
import com.roix.inhype.BaseActivity;
import com.roix.inhype.NoScrollLinearLayoutManager;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.ActivityPreparePhotoBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.presentation.view.preparephoto.PreparePhotoView;
import com.roix.inhype.presentation.presenter.preparephoto.PreparePhotoPresenter;

import com.arellomobile.mvp.MvpActivity;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.root.RootActivity;
import com.roix.inhype.ui.renderers.CategoriesRenderer;
import com.roix.inhype.ui.renderers.CategoryRenderer;
import com.roix.inhype.ui.renderers.CropperRenderer;
import com.roix.inhype.ui.renderers.DescriptionEditRenderer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PreparePhotoActivity extends BaseActivity implements PreparePhotoView,RoixRecyclerAdapter.EventListener {
    public static final String TAG = "PreparePhotoActivity";
    private final static String MEDIA_ACTION_ARG = "media_action_arg";
    private final static String FILE_PATH_ARG = "file_path_arg";
    private final static String DEGREES_ARG = "degrees_arg";
    ActivityPreparePhotoBinding binding;
    RoixRecyclerAdapter adapter;

    @InjectPresenter
    PreparePhotoPresenter mPreparePhotoPresenter;

    public static Intent newIntentPhoto(Context context, String filePath,int degrees) {
        return new Intent(context, PreparePhotoActivity.class)
                .putExtra(DEGREES_ARG,degrees)
                .putExtra(MEDIA_ACTION_ARG, MediaAction.ACTION_PHOTO)
                .putExtra(FILE_PATH_ARG, filePath);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_prepare_photo);
        setSupportActionBar(binding.appBar.toolbar);

        adapter=new RoixRecyclerAdapter(this,binding.rv,this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new NoScrollLinearLayoutManager(this));
        adapter.addItem(new LoginResponse(), CategoriesRenderer.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString(FILE_PATH_ARG);
            int degrees=extras.getInt(DEGREES_ARG);
            Log.d("PreparePhotoActivity","onNewIntent value "+value);
            Comment comment=new Comment();
            comment.setText(value);
            comment.setAuthorId(degrees);
            adapter.addItem(comment, CropperRenderer.class);
            //adapter.addItem(comment, DescriptionEditRenderer.class);

        }
        binding.setPresenter(mPreparePhotoPresenter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.edit){
            ((CropperRenderer)adapter.getRenderer(1)).crop();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        Log.d("PreparePhotoActivity","onEvent type"+type);
        if(type==CropperRenderer.EVENT_PHOTO_PREPARED){
            mPreparePhotoPresenter.sendPhoto((Bitmap) content,((Comment)item).getAuthorId());
        } else if (type==CategoriesRenderer.EVENT_CATEGORY_CHOOSED){
            mPreparePhotoPresenter.setCategory((Integer) content);
        }
    }

    @Override
    public void handleError(Throwable throwable) {

        new AlertDialog.Builder(this).setTitle(DataConverter.handleErrorMessage(throwable)).setPositiveButton("OK",(dialog, which) -> release()).setCancelable(false).create().show();

    }


    @Override
    public void handleProgress(boolean isProgress) {
        if (isProgress)
            new AlertDialog.Builder(this).setView(new ProgressBar(this)).setTitle(getString(R.string.image_loading)).setCancelable(false).create().show();
    }

    @Override
    public void release() {

        finish();
    }
}
