package com.roix.inhype.ui.fragment.root;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.R;
import com.roix.inhype.databinding.FragmentSettingsBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.presentation.view.root.SettingsView;
import com.roix.inhype.presentation.presenter.root.SettingsPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends BaseSupportFragment implements SettingsView {

    public static final String TAG = "SettingsFragment";
    private int PICK_IMAGE_REQUEST = 1;

    @InjectPresenter
    SettingsPresenter mSettingsPresenter;
    FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_settings, container, false);
        binding.setPresenter(mSettingsPresenter);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingsPresenter.init();
    }

    @Override
    public void fillProfileInfo(Profile profile) {
        binding.extraEdit.setText(profile.getStatus());
        binding.userEdit.setText(profile.getNickName());
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
    public void onUploadAva(String url) {
        Picasso.with(getActivity()).load(url).resize(50, 50).into(binding.uploadAva);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG,"onActivityResult uri "+uri.toString());
            mSettingsPresenter.photoChoosedFromGallery(getRealPathFromURI(getActivity(),uri));

        }

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
}
