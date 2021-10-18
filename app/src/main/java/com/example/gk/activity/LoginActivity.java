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
import com.example.gk.admin.AdminHomeActivity;

public class LoginActivity extends AppCompatActivity {

    Button button_Login;
//    SharedPreferences sharedPreferences;
    EditText editText_username,editText_password;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        editText_username = findViewById(R.id.editText_username);
        editText_password = findViewById(R.id.editText_password);

//         username = editText_username.getText().toString();
//         password = editText_password.getText().toString();

        button_Login = findViewById(R.id.button_Login);
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_username.getText().toString().equalsIgnoreCase("admin")
                        && editText_password.getText().toString().equalsIgnoreCase("12345")){
                    Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("USERNAME", editText_username.getText().toString());
                    editor.apply();
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}