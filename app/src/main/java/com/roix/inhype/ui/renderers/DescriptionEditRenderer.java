package com.roix.inhype.ui.renderers;

import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.DescriptionEditItemBinding;

/**
 * Created by roix on 28.06.2017.
 */

public class DescriptionEditRenderer extends RoixRecyclerAdapter.RoixRenderer {

    DescriptionEditItemBinding binding;
    RoixRecyclerAdapter.ItemEventListener listener;
    @Override
    public int getResID() {
        return R.layout.description_edit_item;
    }

    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        this.listener=listener;
        binding= DataBindingUtil.bind(v);
        binding.setPresenter(this);
        binding.extraEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(this.getClass().getName(), "onExtraTextChanged " + s.toString());
                listener.OnEvent(3,s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {

    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }

    public void onExtraTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(this.getClass().getName(), "onExtraTextChanged " + s.toString());
        listener.OnEvent(3,s.toString());
    }

    public String getText(){
        return binding.extraEdit.getText().toString();
    }

}
