package com.roix.inhype.pojo.inner;

import com.roix.inhype.RoixRecyclerAdapter;

import java.util.List;

/**
 * Created by roix on 21.06.2017.
 */

public class ListPhotoDataList implements RoixRecyclerAdapter.RoixDataItem {
    private List<ListPhotoData> list;

    public ListPhotoDataList(List<ListPhotoData> list) {
        this.list = list;
    }

    public List<ListPhotoData> getList() {
        return list;
    }

    public void setList(List<ListPhotoData> list) {
        this.list = list;
    }

    @Override
    public void copy(RoixRecyclerAdapter.RoixDataItem item) {

    }
}
