package com.roix.inhype.presentation.presenter.root;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.GetOwnProfileResponse;
import com.roix.inhype.pojo.LoadAvaResponse;
import com.roix.inhype.pojo.LoadImageResponse;
import com.roix.inhype.pojo.UpdateProfileRequest;
import com.roix.inhype.presentation.view.root.SettingsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    String TAG="SettingsPresenter";

    private String name;
    private String email;
    private String extra;
    private String avatarUrl;


    public void init(){
        if(rep==null)rep=MyApplication.getContentRepository();
        sub(rep.getOwnProfile(), getOwnProfileResponse -> getViewState().fillProfileInfo(getOwnProfileResponse.getProfile()));
    }

    public void onClickUploadAva(){
        getViewState().openChoosePhotoGallery();
    }

    public void onClickApplyChanges(){
        sub(rep.updateOwnProfile(new UpdateProfileRequest(avatarUrl, extra, name)), responseBody -> getViewState().showToast("profile updated"));
    }

    public void photoChoosedFromGallery(String uri){
        Log.v(TAG, "url "+uri);

        File file = new File(uri);//FileUtils.getFile(this, uri);
        Log.v(TAG, "File exists"+file.exists());

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody description = createPartFromString("file");

        sub(rep.uploadAva(body, description), loadAvaResponse -> {
            avatarUrl=loadAvaResponse.getAvatar();
            Log.v(TAG, "url "+avatarUrl);
            getViewState().onUploadAva(avatarUrl);
            getViewState().showToast("image loaded");

        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }



    public void onNameTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(TAG, "onNameTextChanged " + s);
        name=s.toString();
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(TAG, "onEmailTextChanged " + s);
        email=s.toString();
    }

    public void onExtraTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(TAG, "onExtraTextChanged " + s);
        extra=s.toString();
    }




}
