package com.roix.inhype.ui.fragment.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.roix.inhype.R;
import com.roix.inhype.databinding.FragmentSandboxBinding;
import com.roix.inhype.databinding.SandboxPageBinding;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.squareup.picasso.Picasso;

/**
 * Created by roix on 24.06.2017.
 */

public class SandboxPageFragment extends Fragment {

    SandboxPageBinding binding;

    public static SandboxPageFragment newInstance(PhotosCollection photo){
        String json=new Gson().toJson(photo);
        SandboxPageFragment fragment=new SandboxPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("json",json);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sandbox_page,container,false);
        String json=getArguments().getString("json","");
        Log.d("SandboxPageFragment",json);
        PhotosCollection photosCollection=new Gson().fromJson(json,PhotosCollection.class);
        if(photosCollection.getList().size()>0) Picasso.with(container.getContext()).load(photosCollection.getList().get(0).getPreview()).into(binding.iv1);
        if(photosCollection.getList().size()>1) Picasso.with(container.getContext()).load(photosCollection.getList().get(1).getPreview()).into(binding.iv2);
        if(photosCollection.getList().size()>2) Picasso.with(container.getContext()).load(photosCollection.getList().get(2).getPreview()).into(binding.iv3);
        if(photosCollection.getList().size()>3) Picasso.with(container.getContext()).load(photosCollection.getList().get(3).getPreview()).into(binding.iv4);

        return binding.getRoot();
    }

}
