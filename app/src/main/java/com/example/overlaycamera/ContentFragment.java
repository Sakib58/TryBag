package com.example.overlaycamera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class ContentFragment extends Fragment {

    public static HorizontalViewPager viewPager;
    public static HorizontalViewPagerAdapter horizontalViewPagerAdapter;
    public String parentInd;
    public int currentPosition;

    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        parentInd=getArguments().getString("parent");

        viewPager = (HorizontalViewPager) view.findViewById(R.id.vpHorizontal);
        horizontalViewPagerAdapter = new HorizontalViewPagerAdapter(getChildFragmentManager());
        horizontalViewPagerAdapter.setParentID(parentInd);
        viewPager.setAdapter(horizontalViewPagerAdapter);
        viewPager.setCurrentItem(0,true);
        viewPager.setOffscreenPageLimit(100);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            Boolean first = false;
            Boolean last = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Helper.log("Child: "+position);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("app_data",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("current_child",position);
                editor.apply();
                ViewPagerActivity.dict.put(sharedPreferences.getInt("current_parent",-1),position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        return view;
    }

}
