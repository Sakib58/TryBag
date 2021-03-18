package com.example.overlaycamera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ContentFragment extends Fragment {

    public HorizontalViewPager viewPager;
    public HorizontalViewPagerAdapter horizontalViewPagerAdapter;
    public String parentInd;
    public ArrayList<String> sideImages;

    public ContentFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        parentInd=getArguments().getString("parent");
        sideImages = getArguments().getStringArrayList("side_images");

        viewPager = (HorizontalViewPager) view.findViewById(R.id.vpHorizontal);
        horizontalViewPagerAdapter = new HorizontalViewPagerAdapter(getChildFragmentManager(),sideImages);
        horizontalViewPagerAdapter.setParentId(parentInd);
        viewPager.setAdapter(horizontalViewPagerAdapter);
        Helper.log("Card Created : " + parentInd);
        viewPager.setCurrentItem(0);
        return view;
    }

}
