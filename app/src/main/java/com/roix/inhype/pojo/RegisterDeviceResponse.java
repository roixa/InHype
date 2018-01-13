package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 10.05.2017.
 */

public class RegisterDeviceResponse {
    @SerializedName("did")
    @Expose
    private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

}
