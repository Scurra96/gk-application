package com.example.gk.ui.attendance;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gk.R;
import com.example.gk.model.SiteLocationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends Fragment {

    TextView text_date_and_time,text_checkIn,textView_confirm;
    RelativeLayout relative_checkIn,relative_checkOut;
    EditText editText_siteName, editText_location;
    RelativeLayout relativeLayout_yes,relativeLayout_no,relativeLayout_Okay;
    DatabaseReference databaseReference;
    String username,dateAndTime,siteLocation,siteName;
    long i=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        text_date_and_time = root.findViewById(R.id.text_date_and_time);
        relative_checkIn = root.findViewById(R.id.relative_checkIn);
        relative_checkOut = root.findViewById(R.id.relative_checkOut);
        text_checkIn = root.findViewById(R.id.text_checkIn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SiteLocation");
      /*  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    i = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm aa",
                Locale.getDefault());
        String formatDate_time = dateFormat.format(new Date());
        text_date_and_time.setText(formatDate_time);

        relative_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative_checkIn.setVisibility(View.GONE);
                relative_checkOut.setVisibility(View.VISIBLE);
                View popupView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.layout_site_location, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.
                        LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();

                editText_siteName = popupView.findViewById(R.id.editText_siteName);
                editText_location = popupView.findViewById(R.id.editText_location);
                relativeLayout_yes = popupView.findViewById(R.id.relativeLayout_yes);
                relativeLayout_no = popupView.findViewById(R.id.relativeLayout_no);

                relativeLayout_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUserDataYes();
                        Toast.makeText(requireActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                relativeLayout_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUserDataNo();
                        Toast.makeText(requireActivity(), "No", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

            }
        });

        return root;
    }

    private void addUserDataNo() {
        username = text_date_and_time.getText().toString();
        dateAndTime = text_date_and_time.getText().toString();
        siteName = editText_siteName.getText().toString();
        siteLocation = editText_location.getText().toString();
        SiteLocationModel siteLocationModel = new SiteLocationModel(username,dateAndTime,siteName,
                siteLocation,"Check In");
        databaseReference.push().setValue(siteLocationModel);

        View popupView = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_confirm, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.
                LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();

        relativeLayout_Okay = popupView.findViewById(R.id.relativeLayout_Okay);
        textView_confirm = popupView.findViewById(R.id.textView_confirm);
        relativeLayout_Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void addUserDataYes() {

        username = text_date_and_time.getText().toString();
        dateAndTime = text_date_and_time.getText().toString();
        siteName = editText_siteName.getText().toString();
        siteLocation = editText_location.getText().toString();
        SiteLocationModel siteLocationModel = new SiteLocationModel(username,dateAndTime,siteName,
                siteLocation,"Check In");
        databaseReference.push().setValue(siteLocationModel);
//        databaseReference.child(String.valueOf(i+1)).setValue(siteLocationModel);
        View popupView = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_confirm, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.
                LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();

        relativeLayout_Okay = popupView.findViewById(R.id.relativeLayout_Okay);
        relativeLayout_Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });





    }
}