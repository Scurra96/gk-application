package com.example.gk.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gk.R;

public class UserProfileActivity extends AppCompatActivity {

    TextView textView_firstLetter,textView_username,textView_mailId,textView_mobileNumber,
            textView_address,textView_unique_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textView_firstLetter = findViewById(R.id.textView_firstLetter);
        textView_username = findViewById(R.id.textView_username);
        textView_mailId = findViewById(R.id.textView_mailId);
        textView_mobileNumber = findViewById(R.id.textView_mobileNumber);
        textView_address = findViewById(R.id.textView_address);
        textView_unique_id = findViewById(R.id.textView_unique_id);

        textView_username.setText(getIntent().getStringExtra("user_username"));
        textView_mailId.setText(getIntent().getStringExtra("user_emailID"));
        textView_mobileNumber.setText(getIntent().getStringExtra("user_mobileNo"));
        textView_address.setText(getIntent().getStringExtra("user_address"));
        textView_unique_id.setText(getIntent().getStringExtra("user_uniqueNo"));

        char result = getIntent().getStringExtra("user_username").charAt(0);
        textView_firstLetter.setText(String.valueOf(result));
    }
}