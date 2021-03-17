package com.example.overlaycamera;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ChildFragment extends Fragment {
    public TextView tvParent,tvChild;
    Bitmap bm1,bm2,bm3;
    ImageView trialBoyIV;

    public ChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        trialBoyIV = view.findViewById(R.id.iv_boy_trial);
        // Inflate the layout for this fragment
        System.out.println("Hello world");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("image_prefs",0);
        String bms1 = sharedPreferences.getString("image_bitmap_side1","");
        String bms2 = sharedPreferences.getString("image_bitmap_side2","");
        String bms3 = sharedPreferences.getString("image_bitmap_side3","");
        bm1 = StringToBitMap(bms1);
        bm2 = StringToBitMap(bms2);
        bm3 = StringToBitMap(bms3);
        tvParent = (TextView) view.findViewById(R.id.tvParent);
        tvChild = (TextView) view.findViewById(R.id.tvChild);
        Bundle bundle = getArguments();
        tvParent.setText("Parent: " + bundle.getString("parent"));
        tvChild.setText("Child: " + bundle.getString("child"));
        if(bundle.getString("child").equals("1")){
            trialBoyIV.setImageBitmap(bm1);
        }else if(bundle.getString("child").equals("2")){
            trialBoyIV.setImageBitmap(bm2);
        } else {
            trialBoyIV.setImageBitmap(bm3);
        }

        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

  }
