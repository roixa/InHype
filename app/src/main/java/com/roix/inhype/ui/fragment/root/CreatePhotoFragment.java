package com.roix.inhype.ui.fragment.root;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.FragmentCreatePhotoBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.presentation.view.root.CreatePhotoView;
import com.roix.inhype.presentation.presenter.root.CreatePhotoPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.renderers.CategoriesRenderer;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class CreatePhotoFragment extends MvpFragment implements CreatePhotoView,RoixRecyclerAdapter.EventListener {
    public static final String TAG = "CreatePhotoFragment";
    private int PICK_IMAGE_REQUEST = 1;

    @InjectPresenter
    CreatePhotoPresenter mCreatePhotoPresenter;
    FragmentCreatePhotoBinding binding;
    RoixRecyclerAdapter adapter;
    public static CreatePhotoFragment newInstance() {
        CreatePhotoFragment fragment = new CreatePhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_create_photo, container, false);
        binding.setPresenter(mCreatePhotoPresenter);
        adapter=new RoixRecyclerAdapter(getActivity(),binding.rv,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(adapter);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.addItem(new LoginResponse(),CategoriesRenderer.class);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG,"onActivityResult uri "+uri.toString());
            //binding.uploadAva.setImageURI(uri);
            mCreatePhotoPresenter.photoChoosedFromGallery(getRealPathFromURI(getActivity(),uri));

        }

    }


    @Override
    public void openChoosePhotoGallery() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onUploadPhoto(String url) {
        Picasso.with(getActivity()).load(url).resize(150, 150).into(binding.uploadAva);
    }


    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void prepareChooseListState() {

    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        if(type==CategoriesRenderer.EVENT_CATEGORY_CHOOSED){
            int id= (int) content;
            mCreatePhotoPresenter.categoryChoosed(id);
        }

    }
}
