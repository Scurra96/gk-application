package com.example.gk.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ProfileFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String registerUserDetails = "RegisterDetails";
    String username;
    TextView textView_username,textView_mailId,textView_dob,textView_mobileNumber,textView_address,
            textView_firstLetter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences pref = requireActivity().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        username = pref.getString("USERNAME","Gopinathan N");

        textView_username = root.findViewById(R.id.textView_username);
        textView_mailId = root.findViewById(R.id.textView_mailId);
        textView_dob = root.findViewById(R.id.textView_dob);
        textView_mobileNumber = root.findViewById(R.id.textView_mobileNumber);
        textView_address = root.findViewById(R.id.textView_address);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(registerUserDetails);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("username").getValue().equals(username)){
                        textView_username.setText(username);
                        textView_mailId.setText(ds.child("emailID").getValue(String.class));
                        textView_dob.setText(ds.child("dob").getValue(String.class));
                        textView_mobileNumber.setText(ds.child("mobileNo").getValue(String.class));
                        textView_address.setText(ds.child("address").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textView_firstLetter = root.findViewById(R.id.textView_firstLetter);

        char result = username.charAt(0);
        textView_firstLetter.setText(String.valueOf(result));

        return root;
    }

}