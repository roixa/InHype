package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 17.05.2017.
 */

public class UpdateProfileRequest {


    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    public UpdateProfileRequest(){}

    public UpdateProfileRequest(String nickname,String status){
        this.status=status;
        this.nickname=nickname;
    }

    public UpdateProfileRequest(String avatar, String status, String nickname) {
        this.avatar = avatar;
        this.status = status;
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avater) {
        this.avatar = avater;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
