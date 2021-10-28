package com.example.gk.admin;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gk.R;
import com.example.gk.activity.LoginActivity;
import com.example.gk.adapter.ListOfSitesAdapter;
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
import java.util.Collection;
import java.util.Collections;

public class AdminHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView_siteLocation,recyclerView_userProfile;
    TextView textView_viewAll;
    DatabaseReference databaseReference;
    ArrayList<SiteLocationModel> siteLocationModels;
    ArrayList<RegisterModel> registeredModels;
    SiteLocationAdapter siteLocationAdapter;
    UserProfileAdapter userProfileAdapter;
    Toolbar toolbar_admin;

    RecyclerView recyclerView_siteOfList;
    ListOfSitesAdapter listOfSitesAdapter;
    RelativeLayout relativeLayout_arrow;
    SharedPreferences pref;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_home);

           pref  = getApplicationContext().getSharedPreferences(
                    "MyPref", MODE_PRIVATE);

            registeredModels = new ArrayList<>();
            siteLocationModels = new ArrayList<>();
//            Collections.reverse(siteLocationModels);

            toolbar_admin = findViewById(R.id.toolbar_admin);
            setSupportActionBar(toolbar_admin);
            toolbar_admin.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setTitle("Home");

        recyclerView_siteLocation = findViewById(R.id.recyclerView_siteLocation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        recyclerView_siteLocation.setHasFixedSize(true);
        recyclerView_siteLocation.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference("SiteLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SiteLocationModel siteLocationModel = new SiteLocationModel();
//                int size = (int) snapshot.getChildrenCount();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    siteLocationModel.setSiteName(dataSnapshot.child("siteName").getValue(String.class));
                    siteLocationModel.setCheckIn(dataSnapshot.child("checkIn").getValue(String.class));
                    siteLocationModel.setCheckOut(dataSnapshot.child("checkOut").getValue(String.class));
                    siteLocationModel.setDate(dataSnapshot.child("date").getValue(String.class));
                    siteLocationModel.setMobile(dataSnapshot.child("mobile").getValue(String.class));
                    siteLocationModel.setSiteLocation(dataSnapshot.child("siteLocation").getValue(String.class));
                    siteLocationModel.setUsername(dataSnapshot.child("username").getValue(String.class));
                    siteLocationModels.add(siteLocationModel);
//                    Log.d("qwerty",""+size);

                }
                siteLocationAdapter = new SiteLocationAdapter(getApplicationContext(),siteLocationModels);
                recyclerView_siteLocation.setAdapter(siteLocationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            recyclerView_siteOfList = findViewById(R.id.recyclerView_siteOfList);
            recyclerView_siteOfList.setHasFixedSize(true);
            recyclerView_siteOfList.setLayoutManager(new LinearLayoutManager(this));
            databaseReference = FirebaseDatabase.getInstance().
                    getReference("SiteLocation");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    siteLocationModels.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        SiteLocationModel siteModel = dataSnapshot.getValue(SiteLocationModel.class);
                        siteLocationModels.add(siteModel);
                    }
                    Log.d("String1234",siteLocationModels.toString());

                    listOfSitesAdapter = new ListOfSitesAdapter(getApplicationContext(),siteLocationModels);
                    recyclerView_siteOfList.setAdapter(listOfSitesAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.apply();
                editor.clear();
                finish();
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }

        }
}