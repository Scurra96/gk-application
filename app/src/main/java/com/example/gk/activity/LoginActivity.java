package com.example.gk.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gk.HomeActivity;
import com.example.gk.R;
import com.example.gk.admin.AdminHomeActivity;
import com.example.gk.model.RegisterModel;
import com.example.gk.model.RegisteredModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button button_Login;
    EditText editText_mobileNumber,editText_password;
    DatabaseReference databaseReference,databaseReferenceAdmin;
    CheckBox checkBox_view;
    Boolean isLoginChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        databaseReference = FirebaseDatabase.getInstance().getReference("RegisterDetails");

        editText_mobileNumber = findViewById(R.id.editText_mobileNumber);
        editText_password = findViewById(R.id.editText_password);
        checkBox_view = findViewById(R.id.checkBox_view);

        button_Login = findViewById(R.id.button_Login);
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_mobileNumber.getText().toString().equalsIgnoreCase("admin") &&
                        editText_password.getText().toString().equalsIgnoreCase("12345")){
                    if(isLoginChecked){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("user_login_status" , editText_mobileNumber.getText().toString());
                        editor.apply();
                    }
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }else {
                                        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("AdminValue");
                                        databaseReferenceAdmin.child("token").setValue(task.getResult().getToken());
                                        Log.d("Token", task.getResult().getToken());
                                        Intent i = new Intent(getApplicationContext(),AdminHomeActivity.class);
                                        startActivity(i);
                                        Toast.makeText(LoginActivity.this, "ADMIN", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else{
                    databaseReference.child(editText_mobileNumber.getText().toString())
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            RegisterModel registerModel = snapshot.getValue(RegisterModel.class);
                            if(isLoginChecked){
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("user_login_status" , editText_mobileNumber.getText().toString());
                                editor.apply();
                            }
                            if (registerModel != null) {

                                if(editText_password.getText().toString().equals(registerModel.getPassword())){
                                    if(registerModel.getStatus().equalsIgnoreCase("Active")){

                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("user_UserName", registerModel.getUsername());
                                        editor.putString("user_MobileNumber", registerModel.getMobileNo());
                                        editor.putString("user_Email", registerModel.getEmailID());
                                        editor.apply();
                                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Intent i = new Intent(getApplicationContext(),UserStatusActivity.class);
                                        startActivity(i);
                                    }

                                    Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "please check user credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        checkBox_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    isLoginChecked = true;

                }else {

                    isLoginChecked = false;
                }
            }
        });
        editText_mobileNumber.setText(getIntent().getStringExtra("logoutUsername"));
        editText_password.setText(getIntent().getStringExtra("logoutPassword"));
    }
}