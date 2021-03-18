package com.example.overlaycamera;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;


public class ChildFragment extends Fragment {
    public TextView tvParent,tvChild;
    Bitmap bm1,bm2,bm3;
    ImageView trialBoyIV,trialBagIV;
    FloatingActionButton btnDownload,btnShare;
    RelativeLayout rlTrial;
    OutputStream outputStream;

    public ChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        trialBoyIV = view.findViewById(R.id.iv_trial_boy);
        trialBagIV = view.findViewById(R.id.iv_trial_bag);
        btnDownload = view.findViewById(R.id.fab_download);
        rlTrial = view.findViewById(R.id.rl_trial);
        btnShare = view.findViewById(R.id.fab_share);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("image_prefs",0);

        Bundle bundle = getArguments();

        int child = Integer.parseInt(bundle.getString("child")) + 1;
        Log.d(TAG,"Child value is "+child);
        String bms1 = sharedPreferences.getString("image_bitmap_side"+child,"");
        Bitmap bm = StringToBitMap(bms1);
        trialBoyIV.setImageBitmap(bm);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) trialBagIV.getLayoutParams();
        layoutParams.width = sharedPreferences.getInt("width_side"+child,150);
        layoutParams.height = sharedPreferences.getInt("height_side"+child,210);
        layoutParams.leftMargin = sharedPreferences.getInt("marginLeft_side"+child,190);
        layoutParams.topMargin = sharedPreferences.getInt("marginTop_side"+child,190);

        trialBagIV.setLayoutParams(layoutParams);
        Glide.with(getContext())
                .load(Uri.parse(bundle.getStringArrayList("side_images")
                        .get(child-1)))
                .into(trialBagIV);

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

    public void saveImage() {
        rlTrial.setDrawingCacheEnabled(true);
        rlTrial.buildDrawingCache();
        Bitmap bitmap = rlTrial.getDrawingCache();
        File dir = Environment.getExternalStoragePublicDirectory("/TryBag/");
        //File dir = new File(filepath+"/TryBag/");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,System.currentTimeMillis()+".jpg");

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(getContext(), "Image saved as "+file, Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  }
