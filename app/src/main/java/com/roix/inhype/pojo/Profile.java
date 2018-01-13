package com.roix.inhype.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 11.05.2017.
 */

public class Profile implements Parcelable{
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("subscribers")
    @Expose
    private Integer subscriptions;
    @SerializedName("subscriptions")
    @Expose
    private Integer subscribers;

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("nickName")
    @Expose
    private String nickName;


    public Integer getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Integer subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeInt(subscriptions);
        dest.writeInt(subscribers);
        dest.writeInt(rating);
        dest.writeString(status);
        dest.writeString(nickName);
    }

    public static final Parcelable.Creator<Profile> CREATOR
            = new Parcelable.Creator<Profile>() {
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
    private Profile(Parcel in) {
        avatar = in.readString();
        subscriptions=in.readInt();
        subscribers=in.readInt();
        rating=in.readInt();
        status=in.readString();
        nickName=in.readString();
    }


}
