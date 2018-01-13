package com.roix.inhype.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.PhotosCollection;
import com.roix.inhype.ui.fragment.root.SandboxPageFragment;
import com.roix.inhype.ui.renderers.SandboxRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roix on 24.06.2017.
 */

public class SandboxPagerAdapter extends FragmentPagerAdapter{

    private List<Photo> photos;
    private RoixRecyclerAdapter.ItemEventListener listener;

    public SandboxPagerAdapter(FragmentManager fm, RoixRecyclerAdapter.ItemEventListener eventListener) {
        super(fm);
        Log.d("SandboxPagerAdapter"," SandboxPagerAdapter ");
        listener= eventListener;

    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentBasedOnPosition(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
        //return photos.size();
    }

    public void addItems(PhotosCollection collection){
        if(photos==null)photos=new ArrayList<>();
        Log.d("SandboxPagerAdapter"," addItems "+collection.getList().size());

        photos.addAll(collection.getList());
        notifyDataSetChanged();

    }

    private Fragment getFragmentBasedOnPosition(int position) {
        int fragmentPos = position % photos.size();
        Log.d("SandboxPagerAdapter"," getFragmentBasedOnPosition pos "+position+" frag pos "+fragmentPos);
        if(fragmentPos==(photos.size()-1))listener.OnEvent(SandboxRenderer.EVENT_SCROLLED,photos.get(0).getFirstCategory());
        return SandboxPageFragment.newInstance(PhotosCollection.getParsedCollection(fragmentPos,photos));
    }


}
