package com.roix.inhype.ui.renderers;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.CropperItemBinding;
import com.roix.inhype.pojo.Comment;
import com.yashoid.instacropper.InstaCropperView;

import java.io.File;

/**
 * Created by roix on 28.06.2017.
 */

public class CropperRenderer extends RoixRecyclerAdapter.RoixRenderer {
    CropperItemBinding binding;
    RoixRecyclerAdapter.ItemEventListener listener;
    public static final int EVENT_PHOTO_PREPARED=1;
    @Override
    public int getResID() {
        return R.layout.cropper_item;
    }

    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        this.listener=listener;
        binding= DataBindingUtil.bind(v);
        binding.photoPreviewContainer.setRatios(1, 1, 1);

    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        Comment comment= (Comment) item;
        String url=comment.getText();
        binding.photoPreviewContainer.setRotation(comment.getAuthorId());
        binding.photoPreviewContainer.setImageUri(Uri.fromFile(new File(url)));
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {
        Log.d("CropperRenderer","change "+eventType);
        crop();
    }

    public void crop(){
        binding.photoPreviewContainer.crop(
                View.MeasureSpec.makeMeasureSpec(1024, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), bitmap -> {
                    Log.d("CropperRenderer","on crop ");
                    listener.OnEvent(EVENT_PHOTO_PREPARED,bitmap);
                });
    }
}
