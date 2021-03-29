package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ImagePreview3 extends AppCompatActivity {

    ImageView imageView,setBagImage;
    FloatingActionButton moveUp,moveDown,moveLeft,moveRight;
    Button btnNext,btnBack;
    public static RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview3);

        imageView = findViewById(R.id.iv_set_image_3);
        setBagImage = findViewById(R.id.iv_set_bag_image_3);
        moveUp = findViewById(R.id.fab_move_up3);
        moveDown = findViewById(R.id.fab_move_down3);
        moveLeft = findViewById(R.id.fab_move_left3);
        moveRight = findViewById(R.id.fab_move_right3);
        btnNext = findViewById(R.id.btn_next_ip3);
        btnBack = findViewById(R.id.btn_back_ip3);


        imageView.setImageBitmap(CameraSide3.takenPicture);
        layoutParams = (RelativeLayout.LayoutParams) setBagImage.getLayoutParams();
        layoutParams.height = CameraSide3.bagHeight;
        layoutParams.width = CameraSide3.bagWidth;
        layoutParams.topMargin = CameraSide3.bagMarginTop;
        layoutParams.leftMargin = CameraSide3.bagMarginLeft;
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
                startActivity(new Intent(ImagePreview3.this,BrightnessActivity3.class));
            }
        });
    }
}