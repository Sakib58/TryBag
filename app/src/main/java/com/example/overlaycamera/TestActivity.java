package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TestActivity extends AppCompatActivity {

    ImageView testIv,testBagIv;
    DatabaseHelper databaseHelper;
    Cursor data;
    String bms;
    int height,width,leftMargin,topMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        databaseHelper = new DatabaseHelper(this);
        data = databaseHelper.getData();
        testBagIv = findViewById(R.id.iv_test_bag);
        while (data.moveToNext()) {
            bms = data.getString(2);
            height = data.getInt(3);
            width = data.getInt(4);
            leftMargin = data.getInt(5);
            topMargin = data.getInt(6);
            Helper.log("Database testing... MarginLeft:"+leftMargin+" MarginTop:"+topMargin);
        }
    }
}