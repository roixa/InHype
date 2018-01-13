package com.roix.inhype.presentation.presenter.root;


import android.database.Observable;
import android.databinding.ObservableField;
import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.GetCommentsResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.presentation.view.root.CommentsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CommentsPresenter extends BasePresenter<CommentsView> {

    Photo photo;
    String TAG="CommentsPresenter";
    public String message="";

    public void init( Photo photo){
        if(rep==null)rep=MyApplication.getContentRepository();
        this.photo=photo;
        getLastComments();
    }

    public void getLastComments(){
        /*
        api.getLastComments(did,photo.getId(),10).enqueue(new Callback<GetCommentsResponse>() {
            @Override
            public void onResponse(Call<GetCommentsResponse> call, Response<GetCommentsResponse> response) {
                if(response.isSuccessful()){
                    getViewState().onLoadComments(response.body().getComments());
                }
            }

            @Override
            public void onFailure(Call<GetCommentsResponse> call, Throwable t) {

            }
        });
        */
        sub(rep.getLastComments(photo.getId(), 10), getCommentsResponse -> {
            getViewState().onLoadComments(getCommentsResponse);
        });
    }

    public void onScrolledRecycler(){

    }
    public void onMessageTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(TAG, "onMessageTextChanged " + s);
        message=s.toString();
    }


    public void sendButtonClicked(){
        sub(rep.sendComment(photo.getId(), message, photo.getConfirmed()), () -> getViewState().onSentComment());
    }

    public void loadProfile(int profileId){
        if(rep==null)rep=MyApplication.getContentRepository();
        sub(rep.getProfile(profileId),profile -> getViewState().onLoadProfile(profile.getProfile()));
    }

    public String getOwnerAva(){
        if(rep==null)rep=MyApplication.getContentRepository();
        return rep.getOwnerAvatar();
    }

    public void handleFavoritesButton(Photo photo){
        sub(rep.handleFavorites(photo.getId(),!photo.isFavorites()),() -> {});
    }

    public void handleClaimButton(Photo photo){
        sub(rep.claim(photo.getId()),() -> {});
    }

    public void likePhoto(Photo photo){
        sub(rep.likePhoto(photo.getId(), photo.getConfirmed()), likePhotoResponse ->
                getViewState().handleLikeButton(true)
        );
    }

}

