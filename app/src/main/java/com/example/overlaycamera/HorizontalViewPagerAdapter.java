package com.example.overlaycamera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HorizontalViewPagerAdapter extends FragmentStatePagerAdapter {

    public String parentId;
    public ArrayList<String> sideImages;

    public void setParentId(String parentID){
        this.parentId = parentID;
    }

    public HorizontalViewPagerAdapter(FragmentManager fm,ArrayList<String> sideImages) {
        super(fm);
        this.sideImages = sideImages;
    }

    @Override
    public Fragment getItem(int position) {
        ChildFragment childFragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parent",parentId);
        bundle.putString("child", String.valueOf(position));
        bundle.putStringArrayList("side_images",sideImages);
        childFragment.setArguments(bundle);

        return childFragment;
    }

    @Override
    public int getCount() {
        return sideImages.size();
    }
}
