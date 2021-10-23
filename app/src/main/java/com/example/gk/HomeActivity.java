package com.example.gk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.gk.activity.LoginActivity;
import com.example.gk.databinding.ActivityHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    TextView textView_firstLetter,textView_username,textView_mailId;
    /*FirebaseDatabase database;
    DatabaseReference databaseReference;
    String registerUserDetails = "RegisterDetails",username;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference(registerUserDetails);

        SharedPreferences pref = HomeActivity.this.getSharedPreferences(
                "MyPref", MODE_PRIVATE);
//        username = pref.getString("USERNAME","");

        setSupportActionBar(binding.appBarHome.toolbarId);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;

        textView_firstLetter = navigationView.findViewById(R.id.textView_firstLetter);
        textView_username = navigationView.findViewById(R.id.textView_username);
        textView_mailId = navigationView.findViewById(R.id.textView_mailId);

       /* databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
                        textView_username.setText(username);
                        textView_mailId.setText(ds.child("emailID").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        char result = username.charAt(0);
        textView_firstLetter.setText(String.valueOf(result));*/

        Button buttonLogout = binding.buttonLogout;

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("logoutUsername",pref.getString("USERNAME",""));
                i.putExtra("logoutPassword",pref.getString("PASSWORD",""));
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(i);
                finish();
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}