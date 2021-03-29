package com.example.overlaycamera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.lang.reflect.Method;
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
    int parent,child;

    public ChildFragment(){

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

        Bundle bundle = getArguments();

        parent = Integer.parseInt(bundle.getString("parent"));
        child = Integer.parseInt(bundle.getString("child"));

        String bms1 = BagTrialActivity.images.get(child).imageBitmap;
        Bitmap bm = StringToBitMap(bms1);
        trialBoyIV.setImageBitmap(bm);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) trialBagIV.getLayoutParams();
        layoutParams.height = BagTrialActivity.images.get(child).height;
        layoutParams.width = BagTrialActivity.images.get(child).width;
        layoutParams.leftMargin = BagTrialActivity.images.get(child).marginLeft;
        layoutParams.topMargin = BagTrialActivity.images.get(child).marginTop;

        trialBagIV.setLayoutParams(layoutParams);
        Glide.with(getContext())
                .load(Uri.parse(BagTrialActivity.bags.get(parent).sides.get(child)))
                .into(trialBagIV);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareImage(rlTrial);
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Parent: "+bundle.getString("parent")+" Child: "+bundle.getString("child"));
                saveImage(rlTrial);
            }
        });

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

    public void saveImage(RelativeLayout rlTrial) {
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

    public void shareImage(RelativeLayout rlTrial) {
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
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Cover Image"));
    }

  }
