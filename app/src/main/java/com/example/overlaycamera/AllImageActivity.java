package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class AllImageActivity extends AppCompatActivity {
    ImageView sec1side1,sec1side2,sec1side3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_image);
        sec1side1 = findViewById(R.id.iv_sec1_side1);
        sec1side2 = findViewById(R.id.iv_sec1_side2);
        sec1side3 = findViewById(R.id.iv_sec1_side3);

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