package com.roix.inhype.presentation.presenter.preparephoto;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.model.DataRepository;
import com.roix.inhype.presentation.view.preparephoto.PreparePhotoView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.roix.inhype.ui.activity.register.RegisterActivity.TAG;

@InjectViewState
public class PreparePhotoPresenter extends BasePresenter<PreparePhotoView> {

    private String text="";
    private  int categoryId=0;


    public void onExtraTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(this.getClass().getName(), "onExtraTextChanged " + s.toString());
        text=s.toString();
    }

    public void setCategory(int id){
        categoryId=id;
    }

    public void sendPhoto(Bitmap bitmap,int degrees){
        File file = getFileFromBitmap(bitmap,degrees);
        Log.w(this.getClass().getName(), "file  " + file.exists());

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.w(this.getClass().getName(), "requestFile  " + requestFile.contentType());

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Log.w(this.getClass().getName(), "MultipartBody.Part body  " + body.headers());

        RequestBody requestBody = createPartFromString(text);
        Log.w(this.getClass().getName(), "requestBody  " + requestBody.contentType());

        RequestBody categoryBody = createPartFromString(categoryId+"");
        Log.w(this.getClass().getName(), "requestBody2  " + requestBody.contentType());


        sub(rep.uploadImage(body, requestBody,categoryBody), loadImageResponse -> {
            String url=loadImageResponse.getUrl1();
            Log.v(TAG, "url "+url);
            getViewState().release();
        });

    }
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }


    public File getFileFromBitmap(Bitmap bitmap,int degrees){
        Bitmap bitmap1=rotateBitmap(bitmap,degrees);
        File file = new File(DataRepository.filePath,"photo");
        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }
    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


}
