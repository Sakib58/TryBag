package com.example.overlaycamera;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BrightnessActivity3 extends AppCompatActivity {

    Button btnNext,btnBack;
    ImageView im_brightness;
    public static Bitmap bm;
    DatabaseHelper databaseHelper;

    FirebaseFirestore db;
    ProgressDialog progressDialog;
    ImageButton btnSettings;
    public int dataDownloaded=0;
    public int totalData ;

    public static List<BagProperties> bagsList,allBagsList;
    public static List<ImageProperties> personList;

    public BagProperties bagProperties;
    public boolean isComplete;
    SharedPreferences sharedPreferences;
    public boolean red,blue,indigo,abc,def,ghi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness3);

        SeekBar sb_value = (SeekBar) findViewById(R.id.sb_brightness_bar3);
        im_brightness = (ImageView) findViewById(R.id.iv_set_image_brightness_3);
        im_brightness.setImageBitmap(CameraSide3.takenPicture);
        ImageView im_bag = findViewById(R.id.iv_set_bag_image_brightness_3);
        btnNext = findViewById(R.id.btn_next_brightness_3);
        btnBack = findViewById(R.id.btn_back_brightness_3);
        databaseHelper = new DatabaseHelper(this);

        db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences("height_pref",0);
        red = sharedPreferences.getBoolean("red",true);
        blue = sharedPreferences.getBoolean("blue",true);
        indigo = sharedPreferences.getBoolean("indigo",true);
        abc = sharedPreferences.getBoolean("Abc",true);
        def = sharedPreferences.getBoolean("Def",true);
        ghi = sharedPreferences.getBoolean("Ghi",true);

        progressDialog = new ProgressDialog(this);

        personList = new ArrayList<>();

        isComplete = false;

        im_bag.setLayoutParams(ImagePreview3.layoutParams);
        sb_value.setProgress(100);
        sb_value.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                im_brightness.setColorFilter(setBrightness(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                View z = im_brightness;
                z.setDrawingCacheEnabled(true);
                z.buildDrawingCache();
                bm = Bitmap.createBitmap(z.getDrawingCache());
                boolean ok = databaseHelper.addData(getSharedPreferences("height_pref",0).getInt("current_section",1),3,BitMapToString(bm),ImagePreview3.layoutParams.height,
                        ImagePreview3.layoutParams.width,
                        ImagePreview3.layoutParams.leftMargin,
                        ImagePreview3.layoutParams.topMargin);
                if (ok){
                    getAllData();
                    //startActivity(new Intent(BrightnessActivity3.this,ViewPagerActivity.class));
                    //Toast.makeText(BrightnessActivity3.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BrightnessActivity3.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                //System.out.println(ImagePreview1.layoutParams);

            }
        });
    }

    private void getAllData() {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor imageData = databaseHelper.getData();

        while (imageData.moveToNext()){
            personList.add(new ImageProperties(StringToBitMap(imageData.getString(2)),
                    imageData.getInt(3),imageData.getInt(4),
                    imageData.getInt(5),imageData.getInt(6),
                    imageData.getInt(0),imageData.getInt(1)));
        }


        db.collection("bags")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            totalData = task.getResult().size();
                            bagsList = new ArrayList<>();
                            allBagsList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                //primeProgressDialog.show();

                                addNewBag(doc.getData().get("side1").toString(),
                                        doc.getData().get("side2").toString(),
                                        doc.getData().get("side3").toString(),
                                        new BagProperties(
                                                doc.getId(),
                                                doc.getData().get("color").toString(),
                                                doc.getData().get("name").toString(),
                                                doc.getData().get("site").toString()
                                        )
                                );
                            }
                            if(task.isComplete()){
                                //progressDialog.dismiss();
                                //viewPager = (VerticalViewPager) findViewById(R.id.viewPager);
                                //viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),bags);
                                //viewPager.setAdapter(viewPagerAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Helper.log("Failed to retrieve data from firebase because "+e.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static PorterDuffColorFilter setBrightness(int progress) {
        if (progress >=    100)
        {
            int value = (int) (progress-100) * 200 / 100;

            return new PorterDuffColorFilter(Color.argb(value, 200, 200, 200), PorterDuff.Mode.SRC_OVER);

        }
        else
        {
            int value = (int) (100-progress) * 200 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);


        }
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

    public void addNewBag(String side1,String side2,String side3,BagProperties bagProperties) {
        Picasso.get().load(side1).
                into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Helper.log("Side1 height: "+bitmap.getHeight());
                        bagProperties.setSide1(bitmap);
                        Picasso.get().load(side2).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                bagProperties.setSide2(bitmap);
                                Picasso.get().load(side3).into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        bagProperties.setSide3(bitmap);
                                        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                                        databaseHelper.addBagData(bagProperties.docId,BitMapToString(bagProperties.getSide1()),BitMapToString(bagProperties.getSide2()),BitMapToString(bagProperties.getSide3()),bagProperties.name,bagProperties.color,bagProperties.site,bagProperties.isChildLiked(),bagProperties.isAdultLiked());
                                        //if (sharedPreferences.getBoolean(bagProperties.color,true) && sharedPreferences.getBoolean(bagProperties.name,true)){
                                        //    bagsList.add(bagProperties);
                                        //}
                                        //allBagsList.add(bagProperties);
                                        //primeProgressDialog.dismiss();
                                        Helper.log("Data downloaded");
                                        dataDownloaded++;
                                        if (dataDownloaded == totalData){
                                            SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = prefs.edit();
                                            /*Gson gson = new Gson();
                                            String json = gson.toJson(bagsList);
                                            Gson gson1 = new Gson();
                                            String json2 = gson1.toJson(personList);
                                            editor.putString("bag_list",json);
                                            editor.putString("child_liked_bag_list","{}");
                                            editor.putString("adult_liked_bag_list","{}");
                                            editor.putString("original_bag_list",json);
                                            editor.putString("person_list",json2);*/
                                            editor.putBoolean("home_mode",true);
                                            editor.putBoolean("child_mode",false);
                                            editor.putBoolean("adult_mode",false);
                                            editor.apply();
                                            progressDialog.dismiss();
                                            startActivity(new Intent(BrightnessActivity3.this,ViewPagerActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}