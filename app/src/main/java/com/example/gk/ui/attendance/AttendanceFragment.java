package com.example.gk.ui.attendance;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
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

    TextView text_date_and_time,text_checkIn,textView_confirm,textViewOnOff;
    RelativeLayout relative_checkIn,relative_checkOut;
    EditText editText_siteName, editText_location;
    RelativeLayout relativeLayoutProceed,relativeLayoutCancel,relativeLayout_Okay;
    DatabaseReference databaseReference;
    String username,dateAndTime,siteLocation,siteName;
    SwitchCompat switchCompat;
    LinearLayout linearLayout_site,linearLayout_location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        text_date_and_time = root.findViewById(R.id.text_date_and_time);
        relative_checkIn = root.findViewById(R.id.relative_checkIn);
        relative_checkOut = root.findViewById(R.id.relative_checkOut);
        text_checkIn = root.findViewById(R.id.text_checkIn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SiteLocation");
        SharedPreferences pref = requireActivity().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        username = pref.getString("USERNAME","");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, kk:mm aa",
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
                relativeLayoutProceed = popupView.findViewById(R.id.relativeLayoutProceed);
                relativeLayoutCancel = popupView.findViewById(R.id.relativeLayoutCancel);
                switchCompat = popupView.findViewById(R.id.switchCompat);
                textViewOnOff = popupView.findViewById(R.id.textViewOnOff);
                linearLayout_site = popupView.findViewById(R.id.linearLayout_site);
                linearLayout_location = popupView.findViewById(R.id.linearLayout_location);

                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!isChecked){
                            textViewOnOff.setText("Office");
                            linearLayout_site.setVisibility(View.GONE);
                            linearLayout_location.setVisibility(View.GONE);
                        }
                        else{
                            textViewOnOff.setText("Site Location");
                            linearLayout_site.setVisibility(View.VISIBLE);
                            linearLayout_location.setVisibility(View.VISIBLE);
                        }
                    }
                });

                relativeLayoutProceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUserDataYes();
                        Toast.makeText(requireActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

                relativeLayoutCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        addUserDataNo();
                        Toast.makeText(requireActivity(), "No", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                        relative_checkOut.setVisibility(View.GONE);
                        relative_checkIn.setVisibility(View.VISIBLE);

                    }
                });

            }
        });

        return root;
    }

    private void addUserDataNo() {

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
        relativeLayout_Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}