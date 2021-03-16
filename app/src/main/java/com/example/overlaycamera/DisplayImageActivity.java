package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DisplayImageActivity extends AppCompatActivity {

    ImageView displayImage1,displayImage2,displayImage3;
    Bitmap bm1,bm2,bm3;
    FloatingActionButton floatingActionButton;

    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        displayImage1 = findViewById(R.id.iv_display_image_1);
        displayImage2 = findViewById(R.id.iv_display_image_2);
        displayImage3 = findViewById(R.id.iv_display_image_3);
        SharedPreferences sharedPreferences = getSharedPreferences("image_prefs",0);
        String bms1 = sharedPreferences.getString("image_bitmap_side1","");
        String bms2 = sharedPreferences.getString("image_bitmap_side2","");
        String bms3 = sharedPreferences.getString("image_bitmap_side3","");
        bm1 = StringToBitMap(bms1);
        bm2 = StringToBitMap(bms2);
        bm3 = StringToBitMap(bms3);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        displayImage1.setImageBitmap(bm1);
        displayImage2.setImageBitmap(bm2);
        displayImage3.setImageBitmap(bm3);

    }

    public void saveImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) displayImage1.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
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
        Toast.makeText(this, "Image saved as "+file, Toast.LENGTH_SHORT).show();
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