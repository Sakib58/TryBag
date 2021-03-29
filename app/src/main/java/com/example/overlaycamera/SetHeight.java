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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_height);
        spinner = findViewById(R.id.sp_height_select);
        button = findViewById(R.id.btn_start_trial);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                height = (90+(5*position));
                SharedPreferences sharedPref = getSharedPreferences("height_pref",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("current_side",1);
                editor.putInt("height",height);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.reInitialize();

                startActivity(new Intent(SetHeight.this,CameraSide1.class));
            }
        });
    }
}