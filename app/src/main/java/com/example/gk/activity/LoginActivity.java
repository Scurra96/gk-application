package com.example.gk.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button button_Login;
    EditText editText_username,editText_password;
    String username = "",password = "";
    DatabaseReference databaseReference;
    ValueEventListener listener;
    CheckBox checkBox_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", MODE_PRIVATE);

        databaseReference = FirebaseDatabase.getInstance().getReference("RegisterDetails");

        editText_username = findViewById(R.id.editText_username);
        editText_password = findViewById(R.id.editText_password);
        checkBox_view = findViewById(R.id.checkBox_view);

        button_Login = findViewById(R.id.button_Login);
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_username.getText().toString().equalsIgnoreCase("admin") &&
                editText_password.getText().toString().equalsIgnoreCase("12345")){
                    Intent i = new Intent(getApplicationContext(),AdminHomeActivity.class);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, "ADMIN", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(LoginActivity.this, "User", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("USERNAME", editText_username.getText().toString());
                    editor.putString("PASSWORD", editText_password.getText().toString());
                    editor.apply();
                }
            }
        });
        editText_username.setText(getIntent().getStringExtra("logoutUsername"));
        editText_password.setText(getIntent().getStringExtra("logoutPassword"));
//        button_Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(editText_username.getText().toString().equalsIgnoreCase("admin")
//                        && editText_password.getText().toString().equalsIgnoreCase("12345")){
//                    Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
//                    startActivity(i);
//                }else {
//                    if(!editText_username.getText().toString().isEmpty()){
//                        username = editText_username.getText().toString();
//                        password = editText_password.getText().toString();
//                        databaseReference.child(username).addListenerForSingleValueEvent(listener);
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "Enter fields", Toast.LENGTH_SHORT).show();
//                    }
//                    listener = new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                String firebase_password = snapshot.child("password")
//                                        .getValue(String.class);
//                                if(firebase_password.equalsIgnoreCase(password)){
//                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                    SharedPreferences.Editor editor = pref.edit();
//                                    editor.putString("USERNAME", editText_username.getText().toString());
//                                    editor.apply();
//                                    startActivity(i);
//                                    finish();
//                                }
//                                else{
//                                    Toast.makeText(LoginActivity.this, "Wrong Password",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "No Record Found",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    };
//                }
//            }
//        });
    }
}