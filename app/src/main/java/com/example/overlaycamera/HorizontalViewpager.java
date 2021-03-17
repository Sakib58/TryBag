package com.example.overlaycamera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HorizontalViewpager extends Fragment {

    ViewPager viewPager;
    PagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.horizontal_viewpager,container,false);
        viewPager = view.findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        list.add(new FPage1());
        list.add(new FPage2());
        list.add(new FPage3());
        adapter = new SlidePageAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(adapter);
        return view;
    }
}
