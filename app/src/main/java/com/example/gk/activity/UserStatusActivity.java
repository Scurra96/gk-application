package com.example.gk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gk.HomeActivity;
import com.example.gk.R;

public class UserStatusActivity extends AppCompatActivity {

    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        buttonLogout = findViewById(R.id.buttonLogout);

        SharedPreferences pref = UserStatusActivity.this.getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),WelcomeActivity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                startActivity(i);
                finish();
            }
        });
    }
}