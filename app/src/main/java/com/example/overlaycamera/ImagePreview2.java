package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ImagePreview2 extends AppCompatActivity {

    ImageView imageView,setBagImage;
    FloatingActionButton moveUp,moveDown,moveLeft,moveRight;
    Button btnNext,btnBack;
    public static RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview2);

        imageView = findViewById(R.id.iv_set_image_2);
        setBagImage = findViewById(R.id.iv_set_bag_image_2);
        moveUp = findViewById(R.id.fab_move_up2);
        moveDown = findViewById(R.id.fab_move_down2);
        moveLeft = findViewById(R.id.fab_move_left2);
        moveRight = findViewById(R.id.fab_move_right2);
        btnNext = findViewById(R.id.btn_next_ip2);
        btnBack = findViewById(R.id.btn_back_ip2);


        imageView.setImageBitmap(CameraSide2.takenPicture);
        layoutParams = (RelativeLayout.LayoutParams) setBagImage.getLayoutParams();
        layoutParams.height = CameraSide2.bagHeight;
        layoutParams.width = CameraSide2.bagWidth;
        layoutParams.topMargin = CameraSide2.bagMarginTop;
        layoutParams.leftMargin = CameraSide2.bagMarginLeft;
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
                startActivity(new Intent(ImagePreview2.this,BrightnessActivity2.class));
            }
        });
    }
}