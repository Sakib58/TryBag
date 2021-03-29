package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    ImageView iv1,iv2,iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        iv1 = findViewById(R.id.iv_sec1_side1);
        iv2 = findViewById(R.id.iv_sec1_side2);
        iv3 = findViewById(R.id.iv_sec1_side3);

        iv1.setImageBitmap(StringToBitMap(BagTrialActivity.images.get(0).imageBitmap));
        iv2.setImageBitmap(StringToBitMap(BagTrialActivity.images.get(1).imageBitmap));
        iv3.setImageBitmap(StringToBitMap(BagTrialActivity.images.get(2).imageBitmap));
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