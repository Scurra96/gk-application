package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.gk.R;
import com.example.gk.adapter.ListOfSitesAdapter;
import com.example.gk.adapter.UserProfileAdapter;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteLocationModel;
import com.example.gk.model.SiteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfSiteActivity extends AppCompatActivity {

    RecyclerView recyclerView_siteOfList;
    ArrayList<SiteLocationModel> siteLocationModels;
    DatabaseReference databaseReference;
    ListOfSitesAdapter listOfSitesAdapter;
    RelativeLayout relativeLayout_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_site);

        siteLocationModels = new ArrayList<>();

        recyclerView_siteOfList = findViewById(R.id.recyclerView_siteOfList);
        relativeLayout_arrow = findViewById(R.id.relativeLayout_arrow);
        relativeLayout_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView_siteOfList.setHasFixedSize(true);
        recyclerView_siteOfList.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("SiteLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siteLocationModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SiteLocationModel siteModel = dataSnapshot.getValue(SiteLocationModel.class);
                    siteLocationModels.add(siteModel);
                }
                listOfSitesAdapter = new ListOfSitesAdapter(getApplicationContext(),siteLocationModels);
                recyclerView_siteOfList.setAdapter(listOfSitesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}