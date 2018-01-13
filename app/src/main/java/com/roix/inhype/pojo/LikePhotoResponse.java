package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 06.06.2017.
 */

public class LikePhotoResponse {

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("likes")
    @Expose
    private String likes;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
