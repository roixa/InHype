package com.roix.inhype.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.roix.inhype.RoixRecyclerAdapter;

/**
 * Created by roix on 22.05.2017.
 */

public class Category implements RoixRecyclerAdapter.RoixDataItem{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }
}
