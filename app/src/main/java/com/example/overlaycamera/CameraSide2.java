package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CameraSide2 extends AppCompatActivity {

    FrameLayout frameLayout;
    Camera camera;
    ShowCamera showCamera;
    Button takePicture,btnCancel,btnSave,btnNext;
    RelativeLayout rlMainImage;

    private ViewGroup mainLayout;

    public ImageView bagImage,boyImage,ivTakenImage;
    private int xDelta;
    private int yDelta;
    int height;
    int count= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_side2);
        //Toast.makeText(this, SetHeight.height, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("height_pref",0);
        height = sharedPreferences.getInt("height",0);
        takePicture = findViewById(R.id.button1);
        boyImage = findViewById(R.id.iv_boy);
        btnCancel = findViewById(R.id.btn_cancel);
        ivTakenImage = findViewById(R.id.iv_taken_image);
        rlMainImage = findViewById(R.id.rl_main_image);
        btnSave = findViewById(R.id.btn_save);
        btnCancel.setVisibility(View.INVISIBLE);
        btnNext = findViewById(R.id.btn_next);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null){

                    boyImage.setVisibility(View.INVISIBLE);
                    ivTakenImage.setVisibility(View.VISIBLE);
                    takePicture.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    //btnSave.setVisibility(View.VISIBLE);
                    camera.takePicture(null,null,mPictureCallBack);
                }
            }
        });




        bagImage = findViewById(R.id.iv_bag);
        mainLayout = findViewById(R.id.main2);

        setBagSize();

        bagImage.setOnTouchListener(onTouchListener());

        frameLayout = findViewById(R.id.framelayout);
        camera = Camera.open();

        showCamera = new ShowCamera(this,camera);
        frameLayout.addView(showCamera);
    }

    private Bitmap createBitmapFromLayout(View tv) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv.measure(spec, spec);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredWidth(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
    }


    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camerax) {
            Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap pic= Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);


            ivTakenImage.setImageBitmap(pic);
            frameLayout.setVisibility(View.INVISIBLE);
            // bagImage.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlMainImage.setDrawingCacheEnabled(true);
                    rlMainImage.buildDrawingCache();
                    Bitmap bm = rlMainImage.getDrawingCache();
                    System.out.println("Bitmap");
                    String bms = BitMapToString(bm);
                    SharedPreferences sharedPreferences = getSharedPreferences("image_prefs",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("image_bitmap_side2",bms);
                    editor.apply();
                    Intent intent = new Intent(CameraSide2.this,CameraSide3.class);
                    //intent.putExtra("bms",bms);
                    startActivity(intent);
                }
            });



            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlMainImage.setDrawingCacheEnabled(true);
                    rlMainImage.buildDrawingCache();
                    Bitmap bm = rlMainImage.getDrawingCache();
                    System.out.println("Bitmap");
                    String bms = BitMapToString(bm);
                    SharedPreferences sharedPreferences = getSharedPreferences("image_prefs",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("image_bitmap_side2",bms);
                    editor.apply();
                    Intent intent = new Intent(CameraSide2.this,DisplayImageActivity.class);
                    //intent.putExtra("bms",bms);
                    startActivity(intent);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnNext.setVisibility(View.INVISIBLE);
                    camera.startPreview();
                    takePicture.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.INVISIBLE);
                    boyImage.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    ivTakenImage.setVisibility(View.INVISIBLE);
                    btnSave.setVisibility(View.INVISIBLE);
                }
            });

        }
    };

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void setBagSize() {
        if (height>0){
            bagImage.requestLayout();
            bagImage.getLayoutParams().height = bagImage.getLayoutParams().height-(height-90);
            Toast.makeText(this,"Width"+ bagImage.getHeight(), Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"OPS!",Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;



                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
}