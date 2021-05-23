package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChangeOrRemoveActivity extends AppCompatActivity {
    ImageView iv1,iv2,iv3;
    Button remove,change;
    int section;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_or_remove);
        iv1 = findViewById(R.id.iv_alter_image1);
        iv2 = findViewById(R.id.iv_alter_image2);
        iv3 = findViewById(R.id.iv_alter_image3);
        remove = findViewById(R.id.btn_remove_photo);
        change = findViewById(R.id.btn_change_photo);
        section = getIntent().getIntExtra("section",-1);
        databaseHelper = new DatabaseHelper(this);


        for (ImageProperties image : ViewPagerActivity.personList) {
            if (image.section == section) {
                if (image.side == 1) iv1.setImageBitmap(image.imageBitmap);
                else if (image.side == 2) iv2.setImageBitmap(image.imageBitmap);
                else if (image.side == 3) iv3.setImageBitmap(image.imageBitmap);
            }
        }

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deletePerson(section);
                getSharedPreferences("height_pref",0).edit().putInt("current_section",section).apply();
                startActivity(new Intent(ChangeOrRemoveActivity.this,SetHeight.class));
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deletePerson(section);
                startActivity(new Intent(ChangeOrRemoveActivity.this,ViewPagerActivity.class));
            }
        });

    }
}