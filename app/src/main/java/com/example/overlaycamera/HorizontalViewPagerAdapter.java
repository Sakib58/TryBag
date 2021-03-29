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
    public static String image_src;
    public static String side_image;

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

        //image_src = BagTrialActivity.images.get(position).imageBitmap;
        //bundle.putInt("image_height",BagTrialActivity.images.get(position).height);
        //bundle.putInt("image_width",BagTrialActivity.images.get(position).width);
        //bundle.putInt("image_marginLeft",BagTrialActivity.images.get(position).marginLeft);
        //bundle.putInt("image_marginTop",BagTrialActivity.images.get(position).marginTop);
        //bundle.putString("side_image",sideImages.get(position));

        childFragment.setArguments(bundle);

        return childFragment;
    }

    @Override
    public int getCount() {
        return BagTrialActivity.images.size();
    }
}
