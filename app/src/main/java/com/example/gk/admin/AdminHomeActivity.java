package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gk.R;
import com.example.gk.adapter.SiteLocationAdapter;
import com.example.gk.adapter.UserProfileAdapter;
import com.example.gk.model.RegisterModel;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteLocationModel;
import com.example.gk.model.SiteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView_siteLocation,recyclerView_userProfile;
    TextView textView_viewAll;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    ArrayList<SiteLocationModel> siteLocationModels;
    ArrayList<RegisterModel> registeredModels;
    SiteLocationAdapter siteLocationAdapter;
    UserProfileAdapter userProfileAdapter;
    Toolbar toolbar_admin;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_home);

            siteLocationModels = new ArrayList<>();
            registeredModels = new ArrayList<>();

            toolbar_admin = findViewById(R.id.toolbar_admin);
            setSupportActionBar(toolbar_admin);
            toolbar_admin.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setTitle("Home");

        recyclerView_siteLocation = findViewById(R.id.recyclerView_siteLocation);
        recyclerView_siteLocation.setHasFixedSize(true);
        recyclerView_siteLocation.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        databaseReference = FirebaseDatabase.getInstance().getReference("SiteLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siteLocationModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SiteLocationModel siteLocationModel = dataSnapshot.getValue(SiteLocationModel.class);
                    siteLocationModels.add(siteLocationModel);
                }
                siteLocationAdapter = new SiteLocationAdapter(getApplicationContext(),siteLocationModels);
                recyclerView_siteLocation.setAdapter(siteLocationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView_userProfile = findViewById(R.id.recyclerView_userProfile);
        recyclerView_userProfile.setHasFixedSize(true);
        recyclerView_userProfile.setLayoutManager(new LinearLayoutManager(this));
            databaseReference1 = FirebaseDatabase.getInstance().getReference("RegisterDetails");
            databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                registeredModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RegisterModel registeredModel = dataSnapshot.getValue(RegisterModel.class);
                    registeredModels.add(registeredModel);
                }
                userProfileAdapter = new UserProfileAdapter(getApplicationContext(),registeredModels);
                recyclerView_userProfile.setAdapter(userProfileAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        textView_viewAll = findViewById(R.id.textView_viewAll);

        textView_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListOfSiteActivity.class);
                startActivity(intent);
            }
        });
        }
}