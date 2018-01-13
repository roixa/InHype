package com.roix.inhype.pojo.inner;

import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.pojo.ProfileShort;

/**
 * Created by roix on 14.06.2017.
 */

public class ListPhotoData implements RoixRecyclerAdapter.RoixDataItem{
    private Photo photo;
    private ProfileShort profile;

    public ListPhotoData(Photo photo, ProfileShort profile) {
        this.photo = photo;
        this.profile = profile;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public ProfileShort getProfile() {
        return profile;
    }

    public void setProfile(ProfileShort profile) {
        this.profile = profile;
    }

    //@Todo finish profile copy
    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {
        ListPhotoData data= (ListPhotoData) item;
        photo.copy(data.getPhoto());


    }
}
