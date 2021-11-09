package com.example.gk.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {

    TextView textView_firstLetter,textView_username,textView_mailId,textView_mobileNumber,
            textView_address,textView_unique_id,textView_status;
    DatabaseReference databaseReference;
    RelativeLayout relativeLayoutActivate,relativeLayoutDelete,relativeLayout_arrow,relativeLayoutAdd;
    String userStatus, User_MobileNumber,username;
    private EditText editTextEmpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        User_MobileNumber = getIntent().getStringExtra("user_MobileNumber");

        databaseReference = FirebaseDatabase.getInstance().getReference("RegisterDetails");

        textView_firstLetter = findViewById(R.id.textView_firstLetter);
        textView_username = findViewById(R.id.textView_username);
        textView_mailId = findViewById(R.id.textView_mailId);
        textView_mobileNumber = findViewById(R.id.textView_mobileNumber);
        textView_address = findViewById(R.id.textView_address);
        textView_unique_id = findViewById(R.id.textView_unique_id);
        relativeLayoutActivate = findViewById(R.id.relativeLayoutActivate);
        relativeLayoutDelete = findViewById(R.id.relativeLayoutDelete);
        textView_status = findViewById(R.id.textView_status);
        relativeLayout_arrow = findViewById(R.id.relativeLayout_arrow);
        relativeLayout_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("mobileNo").getValue().equals(User_MobileNumber)){
                        textView_mailId.setText(ds.child("emailID").getValue(String.class));
                        textView_unique_id.setText(ds.child("uniqueID").getValue(String.class));
                        textView_mobileNumber.setText(ds.child("mobileNo").getValue(String.class));
                        textView_address.setText(ds.child("address").getValue(String.class));
                        textView_username.setText(ds.child("username").getValue(String.class));
                        textView_status.setText(ds.child("status").getValue(String.class));
                        userStatus = ds.child("status").getValue(String.class);
//                        username = ds.child("username").getValue(String.class);
                        char result = textView_username.toString().charAt(0);
                        textView_firstLetter.setText(String.valueOf(result).toUpperCase(Locale.ROOT));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        relativeLayoutActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(UserProfileActivity.this).inflate(
                        R.layout.layout_card_employee_number, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.
                        LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                editTextEmpID = popupView.findViewById(R.id.editTextEmpID);
                relativeLayoutAdd = popupView.findViewById(R.id.relativeLayoutAdd);
                relativeLayoutAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editTextEmpID.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Please Add Employee ID",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String addEmpID = editTextEmpID.getText().toString();
                            databaseReference.child(User_MobileNumber)
                                    .child("uniqueID").setValue(addEmpID);
                            if(activateStatus()){
                                Toast.makeText(getApplicationContext(), "Update user status on Activate",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            popupWindow.dismiss();
                        }
                    }
                });
            }
        });

        relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteStatus()){
                    Toast.makeText(getApplicationContext(), "Update user status on Delete",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean deleteStatus() {
        if(userStatus.equalsIgnoreCase("Active")){
            databaseReference.child(User_MobileNumber)
                    .child("status").setValue("Inactive");
            return true;
        } else {
            return false;
        }
    }

    private boolean activateStatus() {
        if (userStatus.equalsIgnoreCase("Inactive")) {
            databaseReference.child(User_MobileNumber)
                    .child("status").setValue("Active");
            return true;
        } else {
            return false;
        }
    }
}