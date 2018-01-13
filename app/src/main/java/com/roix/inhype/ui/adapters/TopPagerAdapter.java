package com.roix.inhype.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.ListPhotoData;
import com.roix.inhype.ui.fragment.root.TopFragment;

import java.util.List;

/**
 * Created by roix on 18.06.2017.
 */

public class TopPagerAdapter extends FragmentPagerAdapter{

    List<Photo> photos;
    RoixRecyclerAdapter.ItemEventListener listener;
    FragmentManager fragmentManager;
    ViewPager viewPager;

    public TopPagerAdapter(FragmentManager fm, List<Photo> photos, RoixRecyclerAdapter.ItemEventListener listener, ViewPager viewPager){
        super(fm);
        this.photos=photos;
        this.listener=listener;
        fragmentManager=fm;
        this.viewPager=viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        Photo photo=photos.get(position);
        Fragment fragment=findFragmentByPosition(position);
        if(fragment==null)
            return TopFragment.newInstance(photo,listener);
        return fragment;
    }


    @Override
    public int getCount() {
        return photos.size();
    }

    public Fragment findFragmentByPosition(int position) {
        return fragmentManager.findFragmentByTag(
                "android:switcher:" +viewPager.getId() + ":"
                        + getItemId(position));
    }

}
