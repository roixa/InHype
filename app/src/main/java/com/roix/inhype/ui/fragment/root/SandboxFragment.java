package com.roix.inhype.ui.fragment.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roix.inhype.BaseSupportFragment;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.FragmentSandboxBinding;
import com.roix.inhype.databinding.FragmentTopBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.GetAllSelectionCategoriesResponse;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.roix.inhype.presentation.view.root.SandboxView;
import com.roix.inhype.presentation.presenter.root.SandboxPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.renderers.ProfileRenderer;
import com.roix.inhype.ui.renderers.SandboxRenderer;

public class SandboxFragment extends BaseSupportFragment implements SandboxView, RoixRecyclerAdapter.EventListener{

    public static final String TAG = "SandboxFragment";

    @InjectPresenter
    SandboxPresenter mSandboxPresenter;

    FragmentSandboxBinding binding;
    RoixRecyclerAdapter adapter;
    public static SandboxFragment newInstance() {
        SandboxFragment fragment = new SandboxFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_sandbox,container,false);
        adapter=new RoixRecyclerAdapter(getActivity(),binding.rv,this);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSandboxPresenter.loadChooseFirstPagePhotos();
    }

    @Override
    public void onLoadItems(GetAllSelectionCategoriesResponse photos) {
        if(photos.get_1()!=null&&photos.get_1().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_1(),1), SandboxRenderer.class);
        if(photos.get_2()!=null&&photos.get_2().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_2(),2), SandboxRenderer.class);
        if(photos.get_3()!=null&&photos.get_3().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_3(),3), SandboxRenderer.class);
        if(photos.get_4()!=null&&photos.get_4().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_4(),4), SandboxRenderer.class);
        if(photos.get_5()!=null&&photos.get_5().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_5(),5), SandboxRenderer.class);
        if(photos.get_6()!=null&&photos.get_6().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_6(),6), SandboxRenderer.class);
        if(photos.get_7()!=null&&photos.get_7().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_7(),7), SandboxRenderer.class);
        if(photos.get_8()!=null&&photos.get_8().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_8(),8), SandboxRenderer.class);
        if(photos.get_9()!=null&&photos.get_9().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_9(),9), SandboxRenderer.class);
        if(photos.get_10()!=null&&photos.get_10().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_10(),10), SandboxRenderer.class);
        if(photos.get_11()!=null&&photos.get_11().size()!=0)
            adapter.addItem(new PhotosCollection(photos.get_11(),11), SandboxRenderer.class);

    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getActivity(), DataConverter.handleErrorMessage(throwable),Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {
        if(type== ProfileRenderer.EVENT_CLICKED){
            Photo photo= (Photo) content;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            CommentsFragment newFragment = CommentsFragment.newInstance(photo);
            newFragment.show(ft, "dialog");

        }
    }
}
