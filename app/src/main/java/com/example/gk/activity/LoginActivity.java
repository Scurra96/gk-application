package com.example.gk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gk.HomeActivity;
import com.example.gk.R;

public class LoginActivity extends AppCompatActivity {

    Button button_Login;
    SharedPreferences sharedPreferences;
    EditText editText_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        editText_username = findViewById(R.id.editText_username);

        button_Login = findViewById(R.id.button_Login);
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                String username = editText_username.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USERNAME",username);
                editor.apply();
                startActivity(i);
                finish();
            }
        });
    }
}