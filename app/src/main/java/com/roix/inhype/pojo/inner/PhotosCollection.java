package com.roix.inhype.pojo.inner;

import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.Photo;

import java.util.List;

/**
 * Created by roix on 23.06.2017.
 */

public class PhotosCollection implements RoixRecyclerAdapter.RoixDataItem {
    private List<Photo> list;
    private int category;

    public PhotosCollection(List<Photo> list,int category) {
        this.list = list;
        this.category=category;
    }

    public List<Photo> getList() {
        return list;
    }

    public void setList(List<Photo> list) {
        this.list = list;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }

    public static PhotosCollection getParsedCollection(int pos,List<Photo> list){
        return new PhotosCollection(list,0);
    }
}
