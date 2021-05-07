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

public class SettingsActivity extends AppCompatActivity {

    ImageView iv1,iv2,iv3;

    LinearLayout addOrRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        iv1 = findViewById(R.id.iv_sec1_side1);
        iv2 = findViewById(R.id.iv_sec1_side2);
        iv3 = findViewById(R.id.iv_sec1_side3);
        addOrRemoveBtn = findViewById(R.id.add_others_photo);
        addOrRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,AllImageActivity.class));
            }
        });

        iv2.setImageBitmap(ViewPagerActivity.personList.get(1).imageBitmap);
        iv1.setImageBitmap(ViewPagerActivity.personList.get(0).imageBitmap);
        iv3.setImageBitmap(ViewPagerActivity.personList.get(2).imageBitmap);
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