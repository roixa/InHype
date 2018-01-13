
package com.roix.inhype.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

public class GetSelectionListResponse implements RoixRecyclerAdapter.RoixDataItem{

    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    public GetSelectionListResponse(List<Photo> photos) {
        this.photos = photos;
    }
    public GetSelectionListResponse() {
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }
}
