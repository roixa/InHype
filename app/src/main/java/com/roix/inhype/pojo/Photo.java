
package com.roix.inhype.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

public class Photo  implements Parcelable,RoixRecyclerAdapter.RoixDataItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ownerUser")
    @Expose
    private Integer ownerUser;
    @SerializedName("firstCategory")
    @Expose
    private Integer firstCategory;
    @SerializedName("secondCategory")
    @Expose
    private Integer secondCategory;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("confirmed")
    @Expose
    private Boolean confirmed;

    @SerializedName("hasLike")
    @Expose
    private boolean isLikedByMe=false;


    @SerializedName("likes")
    @Expose
    private Integer likes;

    @SerializedName("comments")
    @Expose
    private Integer comments;

    @SerializedName("profile")
    @Expose
    private ProfileShort profile;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("favorites")
    @Expose
    private boolean favorites;

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public boolean isLikedByMe() {
        return isLikedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        isLikedByMe = likedByMe;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(Integer ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Integer getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(Integer firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Integer getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(Integer secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProfileShort getProfile() {
        return profile;
    }

    public void setProfile(ProfileShort profile) {
        this.profile = profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(id);
        out.writeInt(ownerUser);
        out.writeInt(firstCategory);
        out.writeInt(secondCategory);
        out.writeString(date);

        out.writeInt(rating);
        out.writeString(preview);
        out.writeString(image);
        out.writeInt(confirmed?1:0);
        out.writeInt(likes);
        out.writeInt(comments);
        out.writeParcelable(profile,flags);
    }

    public static final Parcelable.Creator<Photo> CREATOR
            = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel in) {
        id = in.readString();
        ownerUser=in.readInt();
        firstCategory=in.readInt();
        secondCategory=in.readInt();
        date=in.readString();
        rating=in.readInt();
        preview=in.readString();
        image=in.readString();
        confirmed=in.readInt()==0?false:true;
        likes=in.readInt();
        comments=in.readInt();
        profile=in.readParcelable(ProfileShort.class.getClassLoader());
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {
        Photo photo= (Photo) item;
        setComments(photo.getComments());
        setDate(photo.getDate());
        setLikes(photo.getLikes());
        setLikedByMe(photo.isLikedByMe);
        setConfirmed(photo.getConfirmed());
        setDescription(photo.getDescription());
        setId(photo.getId());
        setFirstCategory(photo.getFirstCategory());
        setSecondCategory(photo.getSecondCategory());
        setOwnerUser(photo.getOwnerUser());
        setImage(photo.getImage());
        setPreview(photo.getPreview());
        setRating(photo.getRating());
    }
}
