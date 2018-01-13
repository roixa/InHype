package com.roix.inhype.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Observable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.GetAllSelectionCategoriesResponse;
import com.roix.inhype.pojo.GetCommentsResponse;
import com.roix.inhype.pojo.GetOwnProfileResponse;
import com.roix.inhype.pojo.GetProfilesInfoResponse;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.GetSubscribersResponse;
import com.roix.inhype.pojo.GetSubscriptionsResponse;
import com.roix.inhype.pojo.LikePhotoResponse;
import com.roix.inhype.pojo.LoadAvaResponse;
import com.roix.inhype.pojo.LoadImageResponse;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.pojo.RegisterDeviceResponse;
import com.roix.inhype.pojo.RegisterProfileResponse;
import com.roix.inhype.pojo.UpdateProfileRequest;
import com.roix.inhype.pojo.inner.ListPhotoData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Part;

/**
 * Created by roix on 13.06.2017.
 */

public class ContentRepository   {

    private OurApi api;
    private Retrofit retrofit;
    private DataRepository data;


    public ContentRepository (Context context){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000,TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://82.119.157.2:1501").client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())


                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data=new DataRepository(context);
        api=retrofit.create(OurApi.class);
    }

    private Single transformSingle(Single single){
        return single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private Completable transformCompletable(Completable completable){
        return completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }


    public Single<RegisterDeviceResponse> registerDevice(){
        String os="android";
        String osVersion= Build.VERSION.SDK_INT+"";
        String model= Build.MODEL;
        return transformSingle(api.registerDevice(os,osVersion,model,"",""));
    }

    public Completable checkRegisterDevice(){
        if(data.getDid()==null) return registerDevice().map(registerDeviceResponse -> {
            String did=registerDeviceResponse.getDid();
            data.saveDid(did);
            return did;
        }).toCompletable();
        else return Completable.complete();
    }

    public Single<LoginResponse> login(String login, String password){
        return transformSingle(api.login(data.getDid(),login,password).doOnSuccess(data::saveLoginInfo));
    }

    public LoginResponse getLoginInfo(){
        return data.getLoginInfo();
    }

    public Single<RegisterProfileResponse> registerProfile(String login,String email,String password){
        return transformSingle(api.register(data.getDid(),login,password,email,0));
    }

    public Single<ResponseBody> recoverPassword(String email){
        return transformSingle(api.recoverPassword(email));
    }

    /*
    public Single<GetSelectionListResponse> getSelection(int category,int page,int perpage){
        return transformSingle(api.getSelection(getDid(),category,page,perpage));
    }
    */
    public Single<GetSelectionListResponse> getSelection(int category,int page,int perpage){
        return transformSingle(api.getSelection(data.getDid(),category,page,perpage));
    }


    //@todo realize cache
    public Single<GetOwnProfileResponse> getProfile(long id){
        return transformSingle(api.getProfile(data.getDid(),id));
    }

    public Single<LikePhotoResponse> likePhoto(String keyPhoto,boolean confirmed){
        return transformSingle(api.likePhoto(data.getDid(),keyPhoto,confirmed));
    }

    public Completable sendComment(String keyPhoto,String message,boolean confirmed){
        return transformCompletable(api.sendComment(data.getDid(),keyPhoto,message,confirmed));
    }

    public Single<List<Comment>> getLastComments(String keyPhoto, int length){
        return transformSingle(
                api.getLastComments(data.getDid(),keyPhoto,length)
                    .flatMap(getCommentsResponse ->
                        api.getProfilesInfo(data.getDid(),DataConverter.convertCommentsToStringIds(getCommentsResponse.getComments()))
                                .map(getProfilesInfoResponse ->
                                        DataConverter.fetchProfilesAndComments(getCommentsResponse.getComments(),getProfilesInfoResponse.getUsers())
                                )
                    )
        );
    }

    public Single<GetCommentsResponse> getSegmentComments(String keyPhoto,int length,int lastId){
        return transformSingle(api.getSegmentComments(data.getDid(),keyPhoto,length,lastId));
    }

    public Single<LoadImageResponse> uploadImage(MultipartBody.Part image, RequestBody description,RequestBody category){
        return transformSingle(api.uploadImage(data.getDid(),image,description,category));
    }
    public Single<GetOwnProfileResponse> getOwnProfile(){
        return transformSingle(api.getOwnProfile(data.getDid()));
    }

    public Single<LoadAvaResponse> uploadAva(MultipartBody.Part image, RequestBody name){
        return transformSingle(api.uploadAva(data.getDid(),image,name));
    }

    public Single<ResponseBody> updateOwnProfile(UpdateProfileRequest request){
        return transformSingle(api.updateOwnProfile(data.getDid(),request));
    }

    public Single<GetSelectionListResponse> getOwnPhotos(boolean confirmed,int page,int perPage){
        return transformSingle(api.getOwnPhotos(data.getDid(),confirmed,page,perPage));
    }
    public Single<GetSelectionListResponse> getPhotos(long id,boolean confirmed,int page,int perPage){
        return transformSingle(api.getPhotos(data.getDid(),id,confirmed,page,perPage));
    }

    public Single<List<Photo>> getProfilesInfo(List<Photo> photos){
        return transformSingle(api.getProfilesInfo(data.getDid(),DataConverter.convertToStringIds(photos)).map((
                getProfilesInfoResponse -> (DataConverter.fetchProfilesAndPhotos(photos,getProfilesInfoResponse.getUsers())))));
    }
    public Single<GetAllSelectionCategoriesResponse> getSelectionFromAllCategories(int limit){
        return transformSingle(api.getSelectionWithCategories(data.getDid(),"1,2,3,4,5,6,7,8,9,10,11,12",limit));
    }
    public boolean isLoginIn(){
        return data.getLoginInfo()!=null&&data.getLoginInfo().getUser()!=null;
    }
    public int getOwnLoginId(){
        return data.getLoginInfo().getUser().getId();
    }

    public Single<GetSelectionListResponse> getBest(int category,int page,int perpage){
        return transformSingle(api.getBest(data.getDid(),category,page,perpage));
    }

    public Single<GetSelectionListResponse> getTop(){
        return transformSingle(api.getTop(data.getDid()));
        //return transformSingle(api.getSelection(data.getDid(),0,1,10));
    }

    public Single<GetSelectionListResponse> getNews(int page,int perpage){
        return transformSingle(api.getNews(data.getDid(),page,perpage));
        //return transformSingle(api.getSelection(data.getDid(),0,page,perpage));
    }


    public String getOwnerAvatar(){
        return data.getLoginInfo().getUser().getProfile().getAvatar();
    }

    public boolean isMyProfile(int id){
        return data.getLoginInfo().getUser().getId()==id||id==0;
    }

    public Completable subscribe(int id){
        return transformCompletable(api.subscribe(data.getDid(),id,true));
    }

    public Completable unsubscribe(int id){
        return transformCompletable(api.unsubscribe(data.getDid(),id));
    }

    public Single<GetSubscribersResponse> getSubscribers(int page, int perpage){
        return transformSingle(api.getSubscribers(data.getDid(),page,perpage));//.flattenAsObservable(response -> response.getSubscribers()).map(profileShort -> profileShort.setSubscribes(false)).toList());
    }

    public Single<GetSubscriptionsResponse> getSubscriptions(int page, int perpage){
        return transformSingle(api.getSubscriptions(data.getDid(),page,perpage));//.flattenAsObservable(response -> response.getSubscriptions()).map(profileShort -> profileShort.setSubscribes(false)).toList());
    }

    public Completable handleFavorites(String id,boolean flag){
        return transformCompletable(api.handleFavorites(data.getDid(),id,flag));
    }

    public Single<GetSelectionListResponse> getFavorites(int page, int perpage){
        return transformSingle(api.getFavorites(data.getDid(),page,perpage));
    }

    public Completable claim(String id){
        return transformCompletable(api.claim(data.getDid(),id,"claim",0));
    }

}
