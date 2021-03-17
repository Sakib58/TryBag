package com.example.overlaycamera;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

public class ScrollDownPageAdapter  extends FragmentPagerAdapter {

    List<HorizontalViewpager> list;

    public ScrollDownPageAdapter(@NonNull FragmentManager fm,List<HorizontalViewpager> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

}

