package com.example.gk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gk.R;
import com.example.gk.model.RegisterModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText editText_username,editText_dob,editText_mailID,editText_mobileNumber,
            editText_Address,editText_password;
    Button buttonSignIn;
    TextView textViewLogin;
    DatabaseReference databaseReference;
    String username,dob,mailID,mobileNumber,address,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText_username = findViewById(R.id.editText_username);
        editText_dob = findViewById(R.id.editText_dob);
        editText_mailID = findViewById(R.id.editText_mailID);
        editText_mobileNumber = findViewById(R.id.editText_mobileNumber);
        editText_Address = findViewById(R.id.editText_Address);
        editText_password = findViewById(R.id.editText_password);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewLogin = findViewById(R.id.textViewLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterDetails");

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRegisterData();
            }
        });
    }

    private void addRegisterData() {
        username = editText_username.getText().toString();
        dob = editText_dob.getText().toString();
        mailID = editText_mailID.getText().toString();
        mobileNumber = editText_mobileNumber.getText().toString();
        address = editText_Address.getText().toString();
        password = editText_password.getText().toString();

        RegisterModel registerModel = new RegisterModel(username,dob,mailID,mobileNumber,
                address,password);
        databaseReference.push().setValue(registerModel);
        Toast.makeText(RegisterActivity.this, "Added on Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();
    }
}