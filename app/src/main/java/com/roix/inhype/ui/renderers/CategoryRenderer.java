package com.roix.inhype.ui.renderers;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.CategoryItemBinding;
import com.roix.inhype.pojo.Category;

/**
 * Created by roix on 17.06.2017.
 */

public class CategoryRenderer extends RoixRecyclerAdapter.RoixRenderer {
    CategoryItemBinding binding;

    @Override
    public int getResID() {
        return R.layout.category_item;
    }


    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        Category category= (Category) item;
        binding.text.setText(category.getName());
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }
}
