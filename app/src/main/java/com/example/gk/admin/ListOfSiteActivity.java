package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gk.R;
import com.example.gk.adapter.ListOfSitesAdapter;
import com.example.gk.adapter.UserProfileAdapter;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfSiteActivity extends AppCompatActivity {

    RecyclerView recyclerView_siteOfList;
    ArrayList<SiteModel> siteModels;
    DatabaseReference databaseReference;
    ListOfSitesAdapter listOfSitesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_site);

        siteModels = new ArrayList<>();

        recyclerView_siteOfList = findViewById(R.id.recyclerView_siteOfList);
        recyclerView_siteOfList.setHasFixedSize(true);
        recyclerView_siteOfList.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("SiteLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siteModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SiteModel siteModel = dataSnapshot.getValue(SiteModel.class);
                    siteModels.add(siteModel);
                }
                listOfSitesAdapter = new ListOfSitesAdapter(getApplicationContext(),siteModels);
                recyclerView_siteOfList.setAdapter(listOfSitesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}