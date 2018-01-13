package com.roix.inhype.presentation.presenter.root;


import android.util.Log;

import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.model.OurApi;
import com.roix.inhype.pojo.GetOwnProfileResponse;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.presentation.view.root.ProfileView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {
    String TAG="ProfilePresenter";
    int id;
    private Profile profile;
    private boolean isSubscribe;
    public void init(int id){
        Log.d(TAG,"init");
        if(rep==null)rep=MyApplication.getContentRepository();
        this.id=id;

        if(rep.isMyProfile(id)){
            sub(rep.getOwnProfile(), getOwnProfileResponse -> {
                profile=getOwnProfileResponse.getProfile();
                isSubscribe=getOwnProfileResponse.isSubscribe();
                getViewState().fillProfileInfo(getOwnProfileResponse.getProfile(),rep.isMyProfile(id),isSubscribe);
                Log.d(TAG,"getOwnProfile");
            });
        }
        else {
            sub(rep.getProfile(id), getOwnProfileResponse -> {
                profile=getOwnProfileResponse.getProfile();
                isSubscribe=getOwnProfileResponse.isSubscribe();
                getViewState().fillProfileInfo(profile,rep.isMyProfile(id),isSubscribe);
                Log.d(TAG,"getOwnProfile");
            });

        }
    }

    public void loadProfilePhotos(int page){
        Log.d(TAG,"loadProfilePhotos "+page);
        if(rep.isMyProfile(id)){
            sub(rep.getOwnPhotos(false, page+1, 10), getSelectionListResponse -> getViewState().onLoadProfilePhotos(getSelectionListResponse.getPhotos()));

        }else {
            sub(rep.getPhotos(id,false, page+1, 10), getSelectionListResponse -> getViewState().onLoadProfilePhotos(getSelectionListResponse.getPhotos()));

        }
    }

    public void onSubscribeButtonClicked(){
        if(!isSubscribe){
            sub(rep.subscribe(id),() ->{
                isSubscribe=true;
                getViewState().fillProfileInfo(profile,false,true);
            } );
        }
        else {
            sub(rep.unsubscribe(id),() ->{
                isSubscribe=false;
                getViewState().fillProfileInfo(profile,false,false);
            } );
        }
    }

}
