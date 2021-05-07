package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CheckBox cbRed,cbBlue,cbIndigo,cbAbc,cbDef,cbGhi;
    Button btnSearch;
    public boolean red,blue,indigo,abc,def,ghi;
    public List<BagProperties> searchedBag,allBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPreferences = getSharedPreferences("height_pref",0);
        editor = sharedPreferences.edit();

        cbRed = findViewById(R.id.cb_red);
        cbBlue = findViewById(R.id.cb_blue);
        cbIndigo = findViewById(R.id.cb_indigo);
        cbAbc = findViewById(R.id.cb_abc);
        cbDef = findViewById(R.id.cb_def);
        cbGhi = findViewById(R.id.cb_ghi);
        btnSearch = findViewById(R.id.btn_search);

        allBags = new ArrayList<>();
        searchedBag = new ArrayList<>();

        cbRed.setChecked(sharedPreferences.getBoolean("red",true));
        cbBlue.setChecked(sharedPreferences.getBoolean("blue",true));
        cbIndigo.setChecked(sharedPreferences.getBoolean("indigo",true));
        cbAbc.setChecked(sharedPreferences.getBoolean("Abc",true));
        cbDef.setChecked(sharedPreferences.getBoolean("Def",true));
        cbGhi.setChecked(sharedPreferences.getBoolean("Ghi",true));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SearchActivity.this,BagTrialActivity.class));
                sharedPreferences = getSharedPreferences("height_pref",0);
                red = sharedPreferences.getBoolean("red",true);
                blue = sharedPreferences.getBoolean("blue",true);
                indigo = sharedPreferences.getBoolean("indigo",true);
                abc = sharedPreferences.getBoolean("Abc",true);
                def = sharedPreferences.getBoolean("Def",true);
                ghi = sharedPreferences.getBoolean("Ghi",true);

                for (BagProperties bag : BrightnessActivity3.allBagsList){
                    if (sharedPreferences.getBoolean(bag.color,false) && sharedPreferences.getBoolean(bag.name,false)) searchedBag.add(bag);
                }

                SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(searchedBag);
                editor.putString("bag_list",json);
                editor.putString("original_bag_list",json);
                editor.apply();
                startActivity(new Intent(SearchActivity.this,ViewPagerActivity.class));
            }
        });

        cbRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("red",b);
                editor.apply();
            }
        });
        cbBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("blue",b);
                editor.apply();
            }
        });
        cbIndigo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("indigo",b);
                editor.apply();
            }
        });
        cbAbc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Abc",b);
                editor.apply();
            }
        });
        cbDef.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Def",b);
                editor.apply();
            }
        });
        cbGhi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Ghi",b);
                editor.apply();
            }
        });

    }
}