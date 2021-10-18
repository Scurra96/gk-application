package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SiteLocationActivity extends AppCompatActivity {

    TextView textView_siteName,textView_firstLetter,textView_username,textView_siteLocation,
            textView_unique_id,textView_mobileNumber;
    RelativeLayout relativeLayout_arrow;
    FirebaseDatabase database;
    String username;
    DatabaseReference databaseReference;
    String registerUserDetails = "RegisterDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_location);

        SharedPreferences pref = this.getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        username = pref.getString("USERNAME","");

        relativeLayout_arrow = findViewById(R.id.relativeLayout_arrow);
        textView_siteName = findViewById(R.id.textView_siteName);
        textView_firstLetter = findViewById(R.id.textView_firstLetter);
        textView_username = findViewById(R.id.textView_username);
        textView_siteLocation = findViewById(R.id.textView_siteLocation);
        textView_mobileNumber = findViewById(R.id.textView_mobileNumber);
        textView_unique_id = findViewById(R.id.textView_unique_id);

        textView_siteName.setText(getIntent().getStringExtra("siteName"));
        textView_username.setText(getIntent().getStringExtra("siteLocationUsername"));
        textView_siteLocation.setText(getIntent().getStringExtra("siteLocation"));

        char result = getIntent().getStringExtra("siteName").charAt(0);
        textView_firstLetter.setText(String.valueOf(result));

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(registerUserDetails);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
                        textView_username.setText(username);
                        textView_mobileNumber.setText(ds.child("mobileNo").getValue(String.class));
                        textView_unique_id.setText(ds.child("uniqueID").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}