package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

/**
 * Created by roix on 07.06.2017.
 */

public class Comment implements RoixRecyclerAdapter.RoixDataItem {
    @SerializedName("identifier")
    @Expose
    private Integer identifier;
    @SerializedName("authorId")
    @Expose
    private Integer authorId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("date")
    @Expose
    private String date;

    private ProfileShort profile;

    public ProfileShort getProfile() {
        return profile;
    }

    public void setProfile(ProfileShort profile) {
        this.profile = profile;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {
        Comment comment= (Comment) item;
        setText(comment.getText());
        setAuthorId(comment.getAuthorId());
        setDate(comment.getDate());
        setIdentifier(comment.getIdentifier());
        setProfile(comment.getProfile());
    }
}
