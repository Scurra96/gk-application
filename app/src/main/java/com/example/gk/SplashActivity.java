package com.example.gk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.gk.activity.UserStatusActivity;
import com.example.gk.activity.WelcomeActivity;
import com.example.gk.admin.AdminHomeActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            navigateScreen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        + grantResults[4]
                                        + grantResults[5]
                                        + grantResults[6]
                                        + grantResults[7]
                                        + grantResults[8]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ) {
                    // Permissions are granted
                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    navigateScreen();
                } else {
                    navigateScreen();
                }
            }
        }
    }


    private void navigateScreen() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(!pref.getString("user_login_status","").isEmpty()){
                    if(pref.getString("user_login_status","").equalsIgnoreCase("admin")){
                        Intent mainIntent = new Intent(SplashActivity.this, AdminHomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else {
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                else{
                    Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)

                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setMessage("Camera,Contacts, Storage, Location, SMS and Record Audio" + " permissions are required to Mobion.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                SplashActivity.this,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.WRITE_CONTACTS,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.SEND_SMS,
                                        Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_CONTACTS,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            navigateScreen();
        }
    }
}