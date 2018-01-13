package com.roix.inhype.presentation.presenter.root;


import android.support.annotation.NonNull;
import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.LoadAvaResponse;
import com.roix.inhype.pojo.LoadImageResponse;
import com.roix.inhype.presentation.view.root.CreatePhotoView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CreatePhotoPresenter extends BasePresenter<CreatePhotoView> {
    private String TAG="CreatePhotoPresenter";
    private String localUrl;
    private String description;
    private int categoryID=0;

    public void init(){

    }

    public  void onClickSend(){

        if(localUrl==null||localUrl.isEmpty()){
            getViewState().showToast("please choose image");
            return;
        }
        File file = new File(localUrl);//FileUtils.getFile(this, uri);
        Log.v(TAG, "File exists"+file.exists());

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody requestBody = createPartFromString(description);
        RequestBody categoryBody = createPartFromString(categoryID+"");

        getViewState().showToast("loading started");

        sub(rep.uploadImage(body, requestBody,categoryBody), loadImageResponse -> {
            String url=loadImageResponse.getUrl1();
            Log.v(TAG, "url "+url);
            getViewState().onUploadPhoto(url);
            getViewState().showToast("image loaded");
        });
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    public void onClickUploadFromGallery(){
        getViewState().openChoosePhotoGallery();
    }

    public void onExtraTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(this.getClass().getName(), "onExtraTextChanged " + s);
        description=s.toString();
    }

    public void photoChoosedFromGallery(String url){
        localUrl=url;
        getViewState().showToast("photo choosed "+url);
    }

    public void categoryChoosed(int id){
        categoryID=id;
    }

}
