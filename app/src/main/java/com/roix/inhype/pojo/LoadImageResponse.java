package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 19.05.2017.
 */

public class LoadImageResponse {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("url1")
    @Expose
    private String url1;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id1")
    @Expose
    private String id1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }
}
