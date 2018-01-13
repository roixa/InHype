package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roix on 21.06.2017.
 */

public class GetProfilesInfoResponse {

    @SerializedName("users")
    @Expose
    private List<ProfileShort> users = null;

    public List<ProfileShort> getUsers() {
        return users;
    }

    public void setUsers(List<ProfileShort> users) {
        this.users = users;
    }
}
