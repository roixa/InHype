package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

import java.util.List;

/**
 * Created by roix on 22.05.2017.
 */

public class LoginResponse implements RoixRecyclerAdapter.RoixDataItem{
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("user")
    @Expose
    private User user;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }
}
