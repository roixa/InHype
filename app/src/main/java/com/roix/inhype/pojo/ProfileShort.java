package com.roix.inhype.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

/**
 * Created by roix on 21.06.2017.
 */

public class ProfileShort implements Parcelable,RoixRecyclerAdapter.RoixDataItem{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("subscribers")
    @Expose
    private Integer subscribers;
    @SerializedName("subscriptions")
    @Expose
    private Integer subscriptions;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    @SerializedName("hasReciprocity")
    @Expose
    private boolean hasReciprocity;


    public boolean isHasReciprocity() {
        return hasReciprocity;
    }

    public void setHasReciprocity(boolean hasReciprocity) {
        this.hasReciprocity = hasReciprocity;
    }

    private boolean isSubscribes=false;


    private boolean isSubscriber=false;

    public boolean isSubscriber() {
        return isSubscriber;
    }

    public void setSubscriber(boolean subscriber) {
        isSubscriber = subscriber;
    }

    public boolean isSubscribes() {
        return isSubscribes;
    }

    public ProfileShort setSubscribes(boolean subscribes) {
        isSubscribes = subscribes;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Integer getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Integer subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
        dest.writeString(nickname);

    }
    public ProfileShort (Parcel in){
        avatar = in.readString();
        subscriptions=in.readInt();
        subscribers=in.readInt();
        rating=in.readInt();
        nickname=in.readString();

    }
    public static final Parcelable.Creator<ProfileShort> CREATOR
            = new Parcelable.Creator<ProfileShort>() {
        public ProfileShort createFromParcel(Parcel in) {
            return new ProfileShort(in);
        }

        public ProfileShort[] newArray(int size) {
            return new ProfileShort[size];
        }
    };

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }
}
