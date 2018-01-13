package com.roix.inhype.ui.fragment.root;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentControlsAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;
import com.github.florent37.camerafragment.listeners.CameraFragmentStateAdapter;
import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.R;
import com.roix.inhype.databinding.FragmentCameraBinding;
import com.roix.inhype.model.DataRepository;
import com.roix.inhype.presentation.view.root.CameraView;
import com.roix.inhype.presentation.presenter.root.CameraPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.preparephoto.PreparePhotoActivity;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class CameraContainerFragment extends BaseSupportFragment implements CameraView {

    public static final String TAG = "CameraContainerFragment";
    @InjectPresenter
    CameraPresenter mCameraPresenter;
    CameraFragment cameraFragment;
    FragmentCameraBinding binding;
    boolean isFrontCameraRegime=false;

    public static CameraContainerFragment newInstance() {
        CameraContainerFragment fragment = new CameraContainerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);

        return binding.getRoot();
    }

    private static final int PERMISSION_REQUEST = 1;

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
        binding.buttonsFrame.setOnClickListener(v -> openChoosePhotoGallery());
        setRetainInstance(false);
        cameraFragment.setRetainInstance(false);
        cameraFragment.setAllowReturnTransitionOverlap(false);
        getFragmentManager().beginTransaction()
                .replace(R.id.content, cameraFragment, "camera")
                .commitAllowingStateLoss();
        binding.recordButton.setOnClickListener(v -> cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultAdapter() {
                                                   @Override
                                                   public void onVideoRecorded(String filePath) {
                                                       Toast.makeText(getActivity(), "onVideoRecorded " + filePath, Toast.LENGTH_SHORT).show();
                                                   }

                                                   @Override
                                                   public void onPhotoTaken(byte[] bytes, String filePath) {
                                                       Log.d("PreparePhotoActivity","url2 "+filePath);
                                                       int degrees=isFrontCameraRegime?270:90;
                                                       startActivity(PreparePhotoActivity.newIntentPhoto(getActivity(), filePath,degrees));
                                                   }
                                               }, DataRepository.filePath
                ,
                "photo0"));
        binding.frontBackCameraSwitcher.setOnClickListener(v -> cameraFragment.switchCameraTypeFrontBack());
        binding.flashSwitchView.setOnClickListener(v -> cameraFragment.toggleFlashMode());
        cameraFragment.setStateListener(new CameraFragmentStateAdapter() {

            @Override
            public void onCurrentCameraBack() {
                //binding.frontBackCameraSwitcher.displayBackCamera();
                isFrontCameraRegime=false;
            }

            @Override
            public void onCurrentCameraFront() {
                //binding.frontBackCameraSwitcher.displayFrontCamera();
                isFrontCameraRegime=true;

            }

            @Override
            public void onFlashAuto() {
                binding.flashSwitchView.displayFlashAuto();
            }

            @Override
            public void onFlashOn() {
                binding.flashSwitchView.displayFlashOn();
            }

            @Override
            public void onFlashOff() {
                binding.flashSwitchView.displayFlashOff();
            }

            @Override
            public void onCameraSetupForPhoto() {

                //binding.recordButton.displayPhotoState();
            }

            @Override
            public void onCameraSetupForVideo() {
                //binding.recordButton.displayVideoRecordStateReady();
            }

            @Override
            public void shouldRotateControls(int degrees) {
            }

            @Override
            public void onRecordStateVideoReadyForRecord() {
                //binding.recordButton.displayVideoRecordStateReady();
            }

            @Override
            public void onRecordStateVideoInProgress() {
                //binding.recordButton.displayVideoRecordStateInProgress();
            }

            @Override
            public void onRecordStatePhoto() {

                //binding.recordButton.displayPhotoState();
            }

            @Override
            public void onStopVideoRecord() {
                //cameraSwitchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStartVideoRecord(File outputFile) {
            }
        });

        cameraFragment.setControlsListener(new CameraFragmentControlsAdapter() {
            @Override
            public void lockControls() {
                binding.frontBackCameraSwitcher.setEnabled(false);
                binding.recordButton.setEnabled(false);
                binding.flashSwitchView.setEnabled(false);
            }

            @Override
            public void unLockControls() {
                binding.frontBackCameraSwitcher.setEnabled(true);
                binding.recordButton.setEnabled(true);
                binding.flashSwitchView.setEnabled(true);
            }

            @Override
            public void allowCameraSwitching(boolean allow) {
                binding.frontBackCameraSwitcher.setVisibility(allow ? View.VISIBLE : View.GONE);
            }

            @Override
            public void allowRecord(boolean allow) {
                binding.recordButton.setEnabled(allow);
            }

            @Override
            public void setMediaActionSwitchVisible(boolean visible) {
            }
        });


    }

    private int PICK_IMAGE_REQUEST = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG,"uri "+uri.getEncodedPath()+" "+uri.toString()+uri.getPath());
            String real= getRealPathFromURI(getActivity(),uri);
            if(real.isEmpty()){
                Toast.makeText(getActivity(),"file is empty",Toast.LENGTH_LONG).show();
                return;
            }
            startActivity(PreparePhotoActivity.newIntentPhoto(getActivity(),real,0));
            getActivity().finish();
        }
    }

    public void openChoosePhotoGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        cameraFragment.onPause();
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

}
