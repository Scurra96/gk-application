package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gk.R;
import com.example.gk.adapter.ListOfSitesAdapter;
import com.example.gk.model.SiteLocationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TodaySiteLocationActivity extends AppCompatActivity {

    TextView textViewCurrentDate;
    String currentDate;
    RecyclerView recyclerViewCurrentDate;
    DatabaseReference databaseReference;
    ArrayList<SiteLocationModel> siteLocationModels = new ArrayList<>();
    ListOfSitesAdapter listOfSitesAdapter;
    RelativeLayout relativeLayout_arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_site_location);

        textViewCurrentDate = findViewById(R.id.textViewCurrentDate);
        recyclerViewCurrentDate = findViewById(R.id.recyclerViewCurrentDate);
        relativeLayout_arrow = findViewById(R.id.relativeLayout_arrow);

        relativeLayout_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SimpleDateFormat dateFormat1= new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        currentDate = dateFormat1.format(new Date());
        textViewCurrentDate.setText(currentDate);

        recyclerViewCurrentDate.setHasFixedSize(true);
        recyclerViewCurrentDate.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("SiteLocation").orderByChild("date").equalTo(currentDate);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siteLocationModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SiteLocationModel siteModel = dataSnapshot.getValue(SiteLocationModel.class);
                    siteLocationModels.add(siteModel);
                }
                listOfSitesAdapter = new ListOfSitesAdapter(getApplicationContext(),siteLocationModels);
                recyclerViewCurrentDate.setAdapter(listOfSitesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}