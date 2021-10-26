package com.example.gk;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gk.activity.LoginActivity;
import com.example.gk.activity.WelcomeActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    TextView textView_firstLetter,textView_username,textView_mailId,textViewUsername;
    RelativeLayout relativeLayoutSos;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String registerUserDetails = "RegisterDetails",username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences pref = HomeActivity.this.getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("AdminValue");

        setSupportActionBar(binding.appBarHome.toolbarId);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        textView_firstLetter = headerView.findViewById(R.id.textView_firstLetter);
        textView_username = headerView.findViewById(R.id.textView_username);
        textView_mailId = headerView.findViewById(R.id.textView_mailId);
        relativeLayoutSos = findViewById(R.id.relativeLayoutSos);
        textViewUsername = findViewById(R.id.textViewUsername);

        char result = pref.getString("user_UserName","").charAt(0);
        textView_firstLetter.setText(String.valueOf(result).toUpperCase(Locale.ROOT));

        textView_username.setText(pref.getString("user_UserName",""));
        textViewUsername.setText("Hi," + pref.getString("user_UserName",""));
        textView_mailId.setText(pref.getString("user_Email",""));

        Button buttonLogout = binding.buttonLogout;

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                SharedPreferences.Editor editor = pref.edit();
                editor.apply();
                editor.clear();
                startActivity(i);
                finish();
            }
        });

        relativeLayoutSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Sos", Toast.LENGTH_SHORT).show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String ss = snapshot.child("token").getValue(String.class);

                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            jsonObject1.put("title", pref.getString("user_UserName",""));
                            jsonObject1.put("body", "Emergency Alert!!!");
                            jsonObject1.put("mobile", pref.getString("user_MobileNumber",""));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("to", ss);
                            jsonObject.put("data", jsonObject1);
                            jsonObject.put("priority", "high");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

               new HomeActivity.AboutsAsyncTask(jsonObject).execute();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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


        class  AboutsAsyncTask extends AsyncTask<String, Void, String> {

            private InputStream in;
            private HttpURLConnection httpURLConnection;
            String stream = null;
            JSONObject jsonObject,fcmJsonObject;
            String product_exception;
            String accessToken;
            String heaalerid;
            String firstName = "";
            String lastName = "";

            AboutsAsyncTask(JSONObject jsonObject ) {
                this.jsonObject = jsonObject;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {


                StringBuilder sb;

                try {
                    URL Url = new URL("https://fcm.googleapis.com/fcm/send");
                    httpURLConnection = (HttpURLConnection) Url.openConnection();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty ("Authorization", "key=AAAA3yOyTIc:APA91bGwjk17M51VITRJ_0oBgeLQywuL7a9ya_RLFkOJSsNWFz6HGdtkhiD_-w388hO6bovpfshkYKT" +
                            "if8E5bvDmtOUGLLlKWt3ijmfYdD-hGHPeCQGYP2zoWrergMppJBqVLLeDp5aY");

                    OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    Log.d(TAG+"_AC","ACB" + jsonObject.toString());
                    wr.write(jsonObject.toString());
                    wr.close();

                    int repCode = httpURLConnection.getResponseCode();

                    if (repCode == HttpURLConnection.HTTP_CREATED || repCode == HttpURLConnection.HTTP_OK) {

                        in = new BufferedInputStream(httpURLConnection.getInputStream());
                        product_exception = "true";
                    } else if(repCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                        in = new BufferedInputStream(httpURLConnection.getErrorStream());
                        product_exception = "unAuthorized";
                    }
                    else if(repCode == HttpURLConnection.HTTP_FORBIDDEN) {

                        in = new BufferedInputStream(httpURLConnection.getErrorStream());
                        product_exception = "Forbidden";
                    }
                    else {
                        in = new BufferedInputStream(httpURLConnection.getErrorStream());
                        product_exception = "false";
                    }

                    // Read the BufferedInputStream
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    sb = new StringBuilder();

                    String line;
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                    }

                    stream = sb.toString();
                    Log.d(TAG+"_AC","ACR : " + stream);



                } catch (Exception e) {
                    e.printStackTrace();
                    product_exception = String.valueOf(false);
                } finally {
                    // Disconnect the HttpURLConnection & Close InputStream
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
                return stream;
            }

            @Override
            protected void onPostExecute(String stream) {


                if(product_exception.equals("true")){

                        try {
                            JSONObject jsonObject = new JSONObject(stream);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }else {
                    Toast.makeText(getApplicationContext(),"Please try again.",Toast.LENGTH_LONG).show();
                }

            }
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