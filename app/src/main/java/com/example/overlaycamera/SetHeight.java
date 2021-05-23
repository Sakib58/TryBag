package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetHeight extends AppCompatActivity {
    public int height;
    private Spinner spinner;
    private TextView button;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_height);
        spinner = findViewById(R.id.sp_height_select);
        button = findViewById(R.id.btn_start_trial);
        sharedPref = getSharedPreferences("height_pref",0);
        editor = sharedPref.edit();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                height = (90+(5*position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_section = sharedPref.getInt("current_section",-1);
                Helper.log("Section:"+current_section);
                if (current_section == -1) {
                    editor.putInt("current_section", 1);
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    databaseHelper.reInitialize();
                }else editor.putInt("current_section",current_section);
                editor.putInt("height",height);
                editor.putBoolean("red",true);
                editor.putBoolean("blue",true);
                editor.putBoolean("indigo",true);
                editor.putBoolean("Abc",true);
                editor.putBoolean("Def",true);
                editor.putBoolean("Ghi",true);

                editor.apply();

                startActivity(new Intent(SetHeight.this,CameraSide1.class));
            }
        });
    }
}