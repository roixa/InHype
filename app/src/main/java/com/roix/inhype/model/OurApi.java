package com.roix.inhype.model;

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
import com.roix.inhype.pojo.RegisterDeviceResponse;
import com.roix.inhype.pojo.RegisterProfileResponse;
import com.roix.inhype.pojo.UpdateProfileRequest;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roix on 06.05.2017.
 */

public interface OurApi {

    @FormUrlEncoded
    @POST("/api/mobile/device/register")
    Single<RegisterDeviceResponse> registerDevice(
            @Field("os") String os,
            @Field("osVersion") String osVersion,
            @Field("model") String model,
            @Field("push") String push,
            @Field("resolution") String resolution
            );

    @FormUrlEncoded
    @POST("/api/mobile/auth/login")
    Single<LoginResponse> login(
            @Header("did") String did,
            @Field("login") String login,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/mobile/user/register")
    Single<RegisterProfileResponse> register(
            @Header("did") String did,
            @Field("login") String login,
            @Field("password") String password,
            @Field("email") String email,
            @Field("sex") int sex
    );

    @POST("/api/mobile/user/profile")
    Single<GetOwnProfileResponse> getOwnProfile(
            @Header("did") String did
    );

    @POST("/api/mobile/user/profile/{id}")
    Single<GetOwnProfileResponse> getProfile(
            @Header("did") String did,@Path("id") long id);


    @PUT ("api/mobile/user/profile/update")
    Single<ResponseBody> updateOwnProfile(@Header("did") String did,@Body UpdateProfileRequest request);

    @Multipart
    @POST("/api/mobile/image/avatar/upload")
    Single<LoadAvaResponse> uploadAva(
            @Header("did") String did,
            @Part MultipartBody.Part image, @Part("description") RequestBody name
    );
    @Multipart
    @POST("/api/mobile/image/upload")
    Single<LoadImageResponse> uploadImage(
            @Header("did") String did,
            @Part MultipartBody.Part image, @Part("description") RequestBody description,@Part("firstCategory") RequestBody firstCategory
    );

    @POST("/api/mobile/inhype/selection/category/{id}")
    Single<GetSelectionListResponse> getSelection(
            @Header("did") String did,@Path("id") int category,@Query("page") int page,@Query("perPage") int perPage
    );
    @POST("/api/mobile/inhype/selection/")
    Single<GetAllSelectionCategoriesResponse> getSelectionWithCategories(
            @Header("did") String did,@Query("category") String category,@Query("limit") int limit
    );

    @POST("/api/mobile/inhype/best/category/{id}")
    Single<GetSelectionListResponse> getBest(
            @Header("did") String did,@Path("id") int category,@Query("page") int page,@Query("perPage") int perPage
    );

    @POST("/api/mobile/user/news")
    Single<GetSelectionListResponse> getNews(
            @Header("did") String did,@Query("page") int page,@Query("perPage") int perPage
    );


    @POST("/api/mobile/inhype/top")
    Single<GetSelectionListResponse> getTop(
            @Header("did") String did
    );


    @POST("/api/mobile/user/photos")
    Single<GetSelectionListResponse> getOwnPhotos(
            @Header("did") String did,@Query("confirmed") boolean confirmed,@Query("page") int page,@Query("perPage") int perPage
    );
    @POST("/api/mobile/user/photos/{id}")
    Single<GetSelectionListResponse> getPhotos(
            @Header("did") String did,@Path("id") long id,@Query("confirmed") boolean confirmed,@Query("page") int page,@Query("perPage") int perPage
    );



    @GET("/api/mobile/auth/password/recovery")
    Single<ResponseBody> recoverPassword(
           @Query("email") String email
    );

    @POST("/api/mobile/user/photo/like")
    Single<LikePhotoResponse> likePhoto(
            @Header("did") String did,@Query("idPhoto") String keyPhoto,@Query("confirmed") boolean confirmed
    );

    @POST("/api/mobile/user/photo/comments/last/{id}")
    Single<GetCommentsResponse> getLastComments(
            @Header("did") String did,@Path("id") String keyPhoto,@Query("length") int length
    );

    @POST("/api/mobile/user/photo/comments/segment/{id}")
    Single<GetCommentsResponse> getSegmentComments(
            @Header("did") String did,@Path("id") String keyPhoto,@Query("length") int length,@Query("lastIdentifier") int lastIdentifier
    );
    @POST("/api/mobile/user/photo/comment/{id}/add")
    Completable sendComment(
            @Header("did") String did,@Path("id") String keyPhoto,@Query("message") String message,@Query("confirmed") boolean confirmed
    );

    @POST("/api/mobile/user/info")
    Single<GetProfilesInfoResponse> getProfilesInfo(
            @Header("did") String did,@Query("users") String users
    );

    @POST("/api/mobile/user/subscribe/{userId}")
    Completable subscribe(
            @Header("did") String did,@Path("userId") int userId,@Query("push") boolean push);

    @POST("/api/mobile/user/unsubscribe/{userId}")
    Completable unsubscribe(
            @Header("did") String did,@Path("userId") int userId);

    @POST("/api/mobile/user/subscribers")
    Single<GetSubscribersResponse> getSubscribers(
            @Header("did") String did,@Query("page") int page,@Query("perPage") int perPage
    );

    @POST("/api/mobile/user/subscriptions")
    Single<GetSubscriptionsResponse> getSubscriptions(
            @Header("did") String did,@Query("page") int page,@Query("perPage") int perPage
    );

    @POST("/api/mobile/user/photo/favorites/{id}")
    Completable handleFavorites(
            @Header("did") String did,@Path("id") String keyPhoto,@Query("flag") boolean flag
    );

    @POST("/api/mobile/user/photo/favorites")
    Single<GetSelectionListResponse> getFavorites(
            @Header("did") String did,@Query("page") int page,@Query("perPage") int perPage
    );

    @POST("/api/mobile/user/photo/claim/{id}")
    Completable claim(
            @Header("did") String did,@Path("id") String keyPhoto,@Query("message") String message,@Query("type") int type
    );

}
