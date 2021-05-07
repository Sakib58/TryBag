package com.example.overlaycamera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class ViewPagerActivity extends AppCompatActivity {
    public static List<ImageProperties> personList;
    public static List<BagProperties> bagList,childLikedBagList,adultLikedBagList;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    public FloatingActionButton fabShare,fabRedirect,fabSearch,fabChildLike,fabAdultLike;
    public com.getbase.floatingactionbutton.FloatingActionButton displayChildLikedBagList,displayAdultLikedList,displayAllBagList;
    public static Hashtable<Integer,Integer> dict;
    SharedPreferences.Editor editor ;
    public static Table<Integer, Integer, RelativeLayout> allRelativeLayout;
    OutputStream outputStream;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        fabShare = findViewById(R.id.fab_share);
        fabRedirect = findViewById(R.id.fab_redirect);
        fabSearch = findViewById(R.id.fab_search);
        fabChildLike = findViewById(R.id.fab_child_fav);
        fabChildLike.setFocusable(true);
        fabAdultLike = findViewById(R.id.fab_adult_fav);
        displayChildLikedBagList = findViewById(R.id.fab_child_fav_list);
        displayAdultLikedList = findViewById(R.id.fab_adult_fav_list);
        displayAllBagList = findViewById(R.id.ib_all_filtered_bags);
        databaseHelper = new DatabaseHelper(this);
        imageButton = findViewById(R.id.menu_settings);

        personList = new ArrayList<>();
        bagList = new ArrayList<>();
        dict = new Hashtable<>();
        childLikedBagList = new ArrayList<>();
        adultLikedBagList = new ArrayList<>();
        allRelativeLayout = HashBasedTable.create();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        sharedPreferences = getSharedPreferences("app_data",0);
        editor = sharedPreferences.edit();
        editor.putInt("current_parent",0);
        editor.putInt("current_child",0);
        editor.apply();

        if (sharedPreferences.getBoolean("home_mode",true)){
            Helper.log("FUCKED UP IN HOME MODE");
            cursor = databaseHelper.getBagData();
        }else if (sharedPreferences.getBoolean("child_mode",false)){
            Helper.log("FUCKED UP IN CHILD MODE");
            cursor = databaseHelper.getChildLikedBagData();
        }else if (sharedPreferences.getBoolean("adult_mode",false)){
            Helper.log("FUCKED UP IN ADULT MODE");
            cursor = databaseHelper.getAdultLikedBagData();
        }
        SharedPreferences sharedPreferences1 = getSharedPreferences("height_pref",0);

        while (cursor.moveToNext()){
            Helper.log("CHILD CHECKING>>>"+cursor.getString(5)+">>"+cursor.getString(7));
            BagProperties bagProperties = new BagProperties(cursor.getString(0),cursor.getString(5),cursor.getString(4),cursor.getString(6));
            bagProperties.setSide1(StringToBitMap(cursor.getString(1)));
            bagProperties.setSide2(StringToBitMap(cursor.getString(2)));
            bagProperties.setSide3(StringToBitMap(cursor.getString(3)));
            if (cursor.getInt(7)==1)bagProperties.setChildLiked(true);
            else bagProperties.setChildLiked(false);
            if (cursor.getInt(8)==1)bagProperties.setAdultLiked(true);
            else bagProperties.setAdultLiked(false);
            if (sharedPreferences1.getBoolean(cursor.getString(4),false) && sharedPreferences1.getBoolean(cursor.getString(5),false)){
                bagList.add(bagProperties);
            }

        }

        Cursor imageData = databaseHelper.getData();

        while (imageData.moveToNext()){
            personList.add(new ImageProperties(StringToBitMap(imageData.getString(2)),
                    imageData.getInt(3),imageData.getInt(4),
                    imageData.getInt(5),imageData.getInt(6),
                    imageData.getInt(0),imageData.getInt(1)));
        }

        progressDialog.dismiss();

        if (bagList.size()>0){
            if (bagList.get(0).isChildLiked()){
                fabChildLike.requestFocus();
                fabChildLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
            }
            if (bagList.get(0).isAdultLiked()){
                fabAdultLike.requestFocus();
                fabAdultLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
            }


        /*String sBagList = sharedPreferences.getString("bag_list","{}");
        String sPersonList = sharedPreferences.getString("person_list","{}");
        String sChildLikedBagList = sharedPreferences.getString("child_liked_bag_list","{}");
        String sAdultLikedBagList = sharedPreferences.getString("adult_liked_bag_list","{}");

        progressDialog.dismiss();
        Gson gson = new Gson();
        Type type = new TypeToken<List<BagProperties>>() {
        }.getType();
        bagList = gson.fromJson(sBagList, type);

        Type type2 = new TypeToken<List<ImageProperties>>() {
        }.getType();
        Gson gson2 = new Gson();
        personList = gson2.fromJson(sPersonList,type2);

        Gson gson3 = new Gson();
        Type type3 = new TypeToken<List<BagProperties>>() {
        }.getType();
        if (sChildLikedBagList != "{}") childLikedBagList = gson3.fromJson(sChildLikedBagList, type3);

        Gson gson4 = new Gson();
        Type type4 = new TypeToken<List<BagProperties>>() {
        }.getType();
        if (sAdultLikedBagList != "{}") adultLikedBagList = gson4.fromJson(sAdultLikedBagList, type4);*/


            VerticalViewPager viewPager = findViewById(R.id.viewPager);
            //viewPager.setOffscreenPageLimit();
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0,true);
            viewPager.setOffscreenPageLimit(1000);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (bagList.get(position).isChildLiked()){
                        fabChildLike.requestFocus();
                        fabChildLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }else {
                        fabChildLike.setBackgroundTintList(fabSearch.getBackgroundTintList());
                    }

                    if (bagList.get(position).isAdultLiked()){
                        fabAdultLike.requestFocus();
                        fabAdultLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }else {
                        fabAdultLike.setBackgroundTintList(fabSearch.getBackgroundTintList());
                    }

                    Helper.log("Parent: "+position);
                    editor.putInt("current_parent",position);
                    if (dict.get(position) != null){
                        editor.putInt("current_child",dict.get(position));
                    }else{
                        dict.put(position,0);
                        editor.putInt("current_child",0);
                    }
                    editor.apply();

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            fabShare.setOnClickListener(new View.OnClickListener() {
                int cParent,cChild;
                @Override
                public void onClick(View view) {

                    cParent = sharedPreferences.getInt("current_parent",-1);
                    cChild = sharedPreferences.getInt("current_child",-1);
                    shareImage(cParent,cChild);

                }
            });

            fabRedirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cParent = sharedPreferences.getInt("current_parent",-1);
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(ViewPagerActivity.bagList.get(cParent).site));
                    startActivity(viewIntent);
                }
            });

            fabSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ViewPagerActivity.this,SearchActivity.class));
                }
            });

            fabChildLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cParent = sharedPreferences.getInt("current_parent", -1);
                    if (bagList.get(cParent).isChildLiked()){
                        bagList.get(cParent).setChildLiked(false);
                        fabChildLike.setBackgroundTintList(fabSearch.getBackgroundTintList());
                        databaseHelper.updateChildLikeBagData(bagList.get(cParent).docId,0);
                    }else {
                        bagList.get(cParent).setChildLiked(true);
                        fabChildLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                        databaseHelper.updateChildLikeBagData(bagList.get(cParent).docId,1);

                    }
                }
            });

            fabAdultLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cParent = sharedPreferences.getInt("current_parent", -1);
                    if (bagList.get(cParent).isAdultLiked()){
                        bagList.get(cParent).setAdultLiked(false);
                        fabAdultLike.setBackgroundTintList(fabSearch.getBackgroundTintList());
                        databaseHelper.updateAdultLikeBagData(bagList.get(cParent).docId,0);
                    }else {
                        bagList.get(cParent).setAdultLiked(true);
                        fabAdultLike.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                        databaseHelper.updateAdultLikeBagData(bagList.get(cParent).docId,1);

                    }
                }
            });

        }else {
            Toast.makeText(this, "No bags found!", Toast.LENGTH_SHORT).show();
        }

        displayChildLikedBagList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("current_parent",0);
                editor.putInt("current_child",0);
                editor.putBoolean("home_mode",false);
                editor.putBoolean("child_mode",true);
                editor.putBoolean("adult_mode",false);
                editor.apply();
                //startActivity(getIntent());
                //finish();
                //overridePendingTransition(0, 0);
                //recreate();
                finish();
                startActivity(getIntent());
            }
        });

        displayAdultLikedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("current_parent",0);
                editor.putInt("current_child",0);
                editor.putBoolean("home_mode",false);
                editor.putBoolean("child_mode",false);
                editor.putBoolean("adult_mode",true);
                editor.apply();
                //startActivity(getIntent());
                //finish();
                //overridePendingTransition(0, 0);
                //recreate();
                finish();
                startActivity(getIntent());

            }
        });

        displayAllBagList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("current_parent",0);
                editor.putInt("current_child",0);
                editor.putBoolean("home_mode",true);
                editor.putBoolean("child_mode",false);
                editor.putBoolean("adult_mode",false);
                editor.apply();
                //startActivity(getIntent());
                //finish();
                //overridePendingTransition(0, 0);
                //recreate();
                finish();
                startActivity(getIntent());

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewPagerActivity.this,SettingsActivity.class));
            }
        });


    }
    public void shareImage(int parent,int child){
        RelativeLayout rlTrial = allRelativeLayout.get(parent,child);
        rlTrial.setDrawingCacheEnabled(true);
        rlTrial.buildDrawingCache();
        Bitmap bitmap = rlTrial.getDrawingCache();
        //byte[] bitmapByte = allRelativeLayout.get(parent,child);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByte , 0, bitmapByte .length);
        File dir = Environment.getExternalStoragePublicDirectory("/TryBag/");
        //File dir = new File(filepath+"/TryBag/");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,System.currentTimeMillis()+".jpg");

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Cover Image"));
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