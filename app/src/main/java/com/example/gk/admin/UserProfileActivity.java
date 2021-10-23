package com.example.gk.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gk.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {

    TextView textView_firstLetter,textView_username,textView_mailId,textView_mobileNumber,
            textView_address,textView_unique_id;
    DatabaseReference databaseReference;
    RelativeLayout relativeLayoutActivate,relativeLayoutDelete;
    String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("RegisterDetails");

        textView_firstLetter = findViewById(R.id.textView_firstLetter);
        textView_username = findViewById(R.id.textView_username);
        textView_mailId = findViewById(R.id.textView_mailId);
        textView_mobileNumber = findViewById(R.id.textView_mobileNumber);
        textView_address = findViewById(R.id.textView_address);
        textView_unique_id = findViewById(R.id.textView_unique_id);
        relativeLayoutActivate = findViewById(R.id.relativeLayoutActivate);
        relativeLayoutDelete = findViewById(R.id.relativeLayoutDelete);

        textView_username.setText(getIntent().getStringExtra("user_username"));
        textView_mailId.setText(getIntent().getStringExtra("user_emailID"));
        textView_mobileNumber.setText(getIntent().getStringExtra("user_mobileNo"));
        textView_address.setText(getIntent().getStringExtra("user_address"));
        textView_unique_id.setText(getIntent().getStringExtra("user_uniqueNo"));

        char result = getIntent().getStringExtra("user_username").charAt(0);
        textView_firstLetter.setText(String.valueOf(result));

        relativeLayoutActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activateStatus()){
                    Toast.makeText(getApplicationContext(), "Update user status on Activate",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteStatus()){
                    Toast.makeText(getApplicationContext(), "Update user status on Delete",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean deleteStatus() {
        if(getIntent().getStringExtra("user_status").equals("Activate")){
            databaseReference.child(getIntent().getStringExtra("user_uniqueNo"))
                    .child("status").setValue("review");
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean activateStatus() {
        if (getIntent().getStringExtra("user_status").equals("review")) {
            databaseReference.child(getIntent().getStringExtra("user_uniqueNo"))
                    .child("status").setValue("Activate");
            return true;
        } else {
            return false;
        }
    }
}