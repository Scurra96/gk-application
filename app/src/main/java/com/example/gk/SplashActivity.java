package com.example.gk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.gk.activity.UserStatusActivity;
import com.example.gk.activity.WelcomeActivity;
import com.example.gk.admin.AdminHomeActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(!pref.getString("user_login_status","").isEmpty()){
                    if(pref.getString("user_login_status","").equalsIgnoreCase("admin")){
                        Intent mainIntent = new Intent(SplashActivity.this, AdminHomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else {
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                else{
                    Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}