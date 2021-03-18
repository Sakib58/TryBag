package com.example.overlaycamera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<BagProperties> bags;
    public ViewPagerAdapter(FragmentManager fm, List<BagProperties> bags) {
        super(fm);
        this.bags = bags;
    }

    @Override
    public int getCount() {
        return bags.size();
    }

    @Override
    public Fragment getItem(int position) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parent", String.valueOf(position));
        ArrayList<String> sides = new ArrayList<>();
        sides.add(bags.get(position).side1);
        sides.add(bags.get(position).side2);
        sides.add(bags.get(position).side3);
        bundle.putStringArrayList("side_images",sides);

        contentFragment.setArguments(bundle);
        return contentFragment;
    }
}
