package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ImagePreview1 extends AppCompatActivity {
    ImageView imageView,setBagImage;
    FloatingActionButton moveUp,moveDown,moveLeft,moveRight;
    Button btnNext,btnBack;
    public static RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview1);

        imageView = findViewById(R.id.iv_set_image_1);
        setBagImage = findViewById(R.id.iv_set_bag_image_1);
        moveUp = findViewById(R.id.fab_move_up);
        moveDown = findViewById(R.id.fab_move_down);
        moveLeft = findViewById(R.id.fab_move_left);
        moveRight = findViewById(R.id.fab_move_right);
        btnNext = findViewById(R.id.btn_next_ip1);
        btnBack = findViewById(R.id.btn_back_ip1);


        imageView.setImageBitmap(CameraSide1.takenPicture);
        layoutParams = (RelativeLayout.LayoutParams) setBagImage.getLayoutParams();
        layoutParams.height = CameraSide1.bagHeight;
        layoutParams.width = CameraSide1.bagWidth;
        layoutParams.topMargin = CameraSide1.bagMarginTop;
        layoutParams.leftMargin = CameraSide1.bagMarginLeft;
        setBagImage.setLayoutParams(layoutParams);

        moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.topMargin = layoutParams.topMargin - 10;
                setBagImage.setLayoutParams(layoutParams);
            }
        });

        moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.topMargin = layoutParams.topMargin + 10;
                setBagImage.setLayoutParams(layoutParams);
            }
        });

        moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.leftMargin = layoutParams.leftMargin - 10;
                setBagImage.setLayoutParams(layoutParams);
            }
        });

        moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutParams.leftMargin = layoutParams.leftMargin + 10;
                setBagImage.setLayoutParams(layoutParams);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImagePreview1.this,BrightnessActivity1.class));
            }
        });

    }
}