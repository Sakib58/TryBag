package com.example.overlaycamera;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.ByteBuffer;
import java.util.Hashtable;


public class ChildFragment extends Fragment {
    public static ImageView trialBag,trialBoy;
    public RelativeLayout relativeLayout;
    public int parent,child;
    SharedPreferences sharedPreferences ;

    public ChildFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);

        trialBag = view.findViewById(R.id.iv_trial_bag);
        trialBoy = view.findViewById(R.id.iv_trial_boy);
        relativeLayout = view.findViewById(R.id.rl_trial_image);
        sharedPreferences = getActivity().getSharedPreferences("app_data",0);

        Bundle bundle = getArguments();
        parent = Integer.parseInt(bundle.getString("parent"));
        child = Integer.parseInt(bundle.getString("child"));


        trialBoy.setImageBitmap(ViewPagerActivity.personList.get(child).imageBitmap);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) trialBag.getLayoutParams();
        layoutParams.height = ViewPagerActivity.personList.get(child).height;
        layoutParams.width = ViewPagerActivity.personList.get(child).width;
        layoutParams.leftMargin = ViewPagerActivity.personList.get(child).marginLeft;
        layoutParams.topMargin = ViewPagerActivity.personList.get(child).marginTop;

        trialBag.setLayoutParams(layoutParams);

        if (ViewPagerActivity.bagList.size()>0){
            if (child==0) trialBag.setImageBitmap(ViewPagerActivity.bagList.get(parent).getSide1());
            if (child==1) trialBag.setImageBitmap(ViewPagerActivity.bagList.get(parent).getSide2());
            if (child==2) {trialBag.setImageBitmap(ViewPagerActivity.bagList.get(parent).getSide3());}
        }



        ViewPagerActivity.allRelativeLayout.put(parent,child,relativeLayout);
        return view;
    }


}
