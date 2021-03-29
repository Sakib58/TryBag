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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CameraSide2 extends AppCompatActivity {

    FrameLayout frameLayout;
    Camera camera;
    ShowCamera showCamera;
    ImageButton takePicture,helpBtn;

    public ImageView bagImage,capturedImage;
    public static Bitmap takenPicture;
    int height;
    public static int bagHeight,bagWidth,bagMarginTop,bagMarginLeft;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_side2);

        SharedPreferences sharedPreferences = getSharedPreferences("height_pref",0);
        height = sharedPreferences.getInt("height",0);

        frameLayout = findViewById(R.id.framelayout2);
        takePicture = findViewById(R.id.button2);
        helpBtn = findViewById(R.id.ib_help2);
        bagImage = findViewById(R.id.iv_bag2);
        capturedImage = findViewById(R.id.iv_captured_image_2);

        setBagSize();

        camera = Camera.open();

        showCamera = new ShowCamera(this,camera);
        frameLayout.addView(showCamera);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null){
                    camera.takePicture(null,null,mPictureCallBack);

                }
            }
        });
    }


    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camerax) {
            Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);

            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            takenPicture= Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bagImage.getLayoutParams();
            bagHeight = layoutParams.height;
            bagWidth = layoutParams.width;
            bagMarginTop = layoutParams.topMargin;
            bagMarginLeft = layoutParams.leftMargin;
            startActivity(new Intent(CameraSide2.this,ImagePreview2.class));
        }
    };

    private void setBagSize() {
        if (height>0){
            bagImage.requestLayout();
            bagImage.getLayoutParams().height = bagImage.getLayoutParams().height-(height-90);

        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}