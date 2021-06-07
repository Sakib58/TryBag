package com.example.overlaycamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("app_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("home_mode",true);
        editor.putBoolean("child_mode",false);
        editor.putBoolean("adult_mode",false);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefs.getBoolean("navigate_to_home",false)){
                    startActivity(new Intent(SplashActivity.this,ViewPagerActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, SetHeight.class));
                }

            }
        }, 3000);
    }
}