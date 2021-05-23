package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllImageActivity extends AppCompatActivity {
    ImageView sec1side1,sec1side2,sec1side3,sec2side1,sec2side2,sec2side3,sec3side1,sec3side2,sec3side3;
    LinearLayout sec1,sec2,sec3;
    TextView tvSec1,tvSec2,tvSec3;
    boolean one,two,three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_image);
        sec1side1 = findViewById(R.id.iv_sec1_side1);
        sec1side2 = findViewById(R.id.iv_sec1_side2);
        sec1side3 = findViewById(R.id.iv_sec1_side3);
        sec2side1 = findViewById(R.id.iv_sec2_side1);
        sec2side2 = findViewById(R.id.iv_sec2_side2);
        sec2side3 = findViewById(R.id.iv_sec2_side3);
        sec3side1 = findViewById(R.id.iv_sec3_side1);
        sec3side2 = findViewById(R.id.iv_sec3_side2);
        sec3side3 = findViewById(R.id.iv_sec3_side3);
        sec1 = findViewById(R.id.add_others_photo1);
        sec2 = findViewById(R.id.add_others_photo2);
        sec3 = findViewById(R.id.add_others_photo3);
        tvSec1 = findViewById(R.id.tv_sec1);
        tvSec2 = findViewById(R.id.tv_sec2);
        tvSec3 = findViewById(R.id.tv_sec3);
        one = two = three = false;

        for (ImageProperties image : ViewPagerActivity.personList){
            if (image.section == 1){
                one = true;
                tvSec1.setText("Change or Remove");
                if (image.side == 1)sec1side1.setImageBitmap(image.imageBitmap);
                else if (image.side == 2)sec1side2.setImageBitmap(image.imageBitmap);
                else if (image.side == 3)sec1side3.setImageBitmap(image.imageBitmap);
            }
            else if (image.section == 2){
                two = true;
                tvSec2.setText("Change or Remove");
                if (image.side == 1)sec2side1.setImageBitmap(image.imageBitmap);
                else if (image.side == 2)sec2side2.setImageBitmap(image.imageBitmap);
                else if (image.side == 3)sec2side3.setImageBitmap(image.imageBitmap);
            }
            else if (image.section == 3){
                three = true;
                tvSec3.setText("Change or Remove");
                if (image.side == 1)sec3side1.setImageBitmap(image.imageBitmap);
                else if (image.side == 2)sec3side2.setImageBitmap(image.imageBitmap);
                else if (image.side == 3)sec3side3.setImageBitmap(image.imageBitmap);
            }
        }

        sec1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (one){
                    startActivity(new Intent(AllImageActivity.this,ChangeOrRemoveActivity.class).putExtra("section",1));
                }
                else{
                    getSharedPreferences("height_pref",0).edit().putInt("current_section",1).apply();
                    startActivity(new Intent(AllImageActivity.this,SetHeight.class));
                }
            }
        });

        sec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (two){
                    startActivity(new Intent(AllImageActivity.this,ChangeOrRemoveActivity.class).putExtra("section",2));
                }
                else{
                    getSharedPreferences("height_pref",0).edit().putInt("current_section",2).apply();
                    startActivity(new Intent(AllImageActivity.this,SetHeight.class));
                }
            }
        });

        sec3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (three){
                    startActivity(new Intent(AllImageActivity.this,ChangeOrRemoveActivity.class).putExtra("section",3));
                }
                else{
                    getSharedPreferences("height_pref",0).edit().putInt("current_section",3).apply();
                    startActivity(new Intent(AllImageActivity.this,SetHeight.class));
                }
            }
        });

        /*sec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("height_pref",0).edit().putInt("current_section",2).apply();
                startActivity(new Intent(AllImageActivity.this,SetHeight.class));
            }
        });

        sec3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences("height_pref",0).edit().putInt("current_section",3).apply();
                startActivity(new Intent(AllImageActivity.this,SetHeight.class));
            }
        });*/

        //sec1side1.setImageBitmap(BagTrialActivity.images.get(0).imageBitmap);
        //sec1side2.setImageBitmap(BagTrialActivity.images.get(1).imageBitmap);
        //sec1side3.setImageBitmap(BagTrialActivity.images.get(2).imageBitmap);
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