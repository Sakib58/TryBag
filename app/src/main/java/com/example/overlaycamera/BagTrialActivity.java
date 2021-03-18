package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class BagTrialActivity extends AppCompatActivity {
    public ViewPagerAdapter viewPagerAdapter;
    public VerticalViewPager viewPager;

    List<BagProperties> bags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_trial);

        initViewPager();
    }



    public void initViewPager(){
        bags = new ArrayList<>();
        bags.add(new BagProperties("https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-1%2Fside1.png?alt=media&token=cb5cc761-a6f1-481c-b6a0-39b485d3f7eb",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-1%2Fside2.png?alt=media&token=422033dd-1cdc-448b-b07a-a146c50c4962",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-1%2Fside3.png?alt=media&token=75a6d91d-fc6f-409d-a786-a0dd3de558ca"));
        bags.add(new BagProperties("https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-2%2Fside1.png?alt=media&token=c8754c8c-effe-479b-98f8-10ef4c0f6655",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-2%2Fside2.png?alt=media&token=ea754018-824a-4011-92e8-d64ac1783b10",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-2%2Fside3.png?alt=media&token=29ab2e07-b137-4d26-bf8f-adb5402b56c6"));
        bags.add(new BagProperties("https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-3%2Fside1.png?alt=media&token=970e2299-aa8d-4320-9b91-e1f3e46e2de1",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-3%2Fside2.png?alt=media&token=7179bd68-d13f-48fa-b4ba-0e6b146820fd",
                "https://firebasestorage.googleapis.com/v0/b/try-bag.appspot.com/o/bag-3%2Fside3.png?alt=media&token=7ba1e92d-2d57-4732-b460-62c9c49f00ee"));
        viewPager = (VerticalViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),bags);
        viewPager.setAdapter(viewPagerAdapter);

    }
}