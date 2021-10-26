package com.example.gk.ui.attendance;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gk.R;
import com.example.gk.getAddress.GetAddressIntentService;
import com.example.gk.model.SiteLocationModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends Fragment {

    TextView text_date_and_time,text_checkIn,textView_confirm,textViewOnOff,text_daily;
    RelativeLayout relative_checkIn,relative_checkOut;
    EditText editText_siteName, editText_location;
    RelativeLayout relativeLayoutProceed,relativeLayoutCancel,relativeLayout_Okay;
    String username,dateAndTime,siteLocation,siteName,currentAddress;
    SwitchCompat switchCompat;
    LinearLayout linearLayout_site,linearLayout_location;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private AttendanceFragment.LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;
    String usernameValue,checkIn,checkOut,mobileNo;
    private String formatDate,checkInTime,checkOutTime;
    DatabaseReference databaseReference,databaseReference1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        text_date_and_time = root.findViewById(R.id.text_date_and_time);
        relative_checkIn = root.findViewById(R.id.relative_checkIn);
        relative_checkOut = root.findViewById(R.id.relative_checkOut);
        text_checkIn = root.findViewById(R.id.text_checkIn);
        text_daily = root.findViewById(R.id.text_daily);
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("SiteLocation");
        databaseReference1= FirebaseDatabase.getInstance().getReference("SiteLocation");
        SharedPreferences pref = requireActivity().getSharedPreferences(
                "MyPref", MODE_PRIVATE);
        username = pref.getString("user_UserName","");
        mobileNo = pref.getString("user_MobileNumber","");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, kk:mm aa",
                Locale.getDefault());

        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm aa",
                Locale.getDefault());
        SimpleDateFormat timeFormat_out = new SimpleDateFormat("kk:mm aa",
                Locale.getDefault());

        SimpleDateFormat dateFormat1= new SimpleDateFormat("ddMMMyyyy",
                Locale.getDefault());
        String formatDate_time = dateFormat.format(new Date());
        formatDate = dateFormat1.format(new Date());
        checkInTime = timeFormat.format(new Date());
        checkOutTime = timeFormat_out.format(new Date());
        text_date_and_time.setText(formatDate_time);

       /* databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernameValue = snapshot.child(username).child("username").getValue(String.class);
                Log.d("ASAP",""+usernameValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        if(!pref.getBoolean("checkIn_status",false)){
                          relative_checkOut.setVisibility(View.GONE);
                          relative_checkIn.setVisibility(View.VISIBLE);
        }else{
                            relative_checkOut.setVisibility(View.VISIBLE);
                            relative_checkIn.setVisibility(View.GONE);
        }

        relative_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
//                            linearLayout_location.setVisibility(View.VISIBLE);
                        }
                    }
                });

                relativeLayoutProceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUserDataYes();
                        popupWindow.dismiss();

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("checkIn_status", true);
                        editor.apply();

                        relative_checkOut.setVisibility(View.VISIBLE);
                        relative_checkIn.setVisibility(View.GONE);

                    }
                });

                relativeLayoutCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        addUserDataNo();
                        popupWindow.dismiss();


                    }
                });

            }
        });

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();

        relative_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance()
                        .getReference("SiteLocation/"+mobileNo);
                databaseReference.child("checkOut").setValue(checkOutTime);
                relative_checkOut.setVisibility(View.GONE);
                relative_checkIn.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(requireActivity(), "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(requireActivity(), GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        requireActivity().startService(intent);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(requireActivity(), "Location permission not granted, " +
                        "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(requireActivity(), "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            String currentAdd = resultData.getString("address_result");
            showResults(currentAdd);
        }
    }
    private void showResults(String currentAdd) {
        currentAddress = currentAdd;
//            text_daily.setText(currentAdd);
    }
    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdates();
    }
    @Override
    public void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void addUserDataYes() {

        dateAndTime = text_date_and_time.getText().toString();
        if(editText_siteName.getText().toString().isEmpty()){
            siteName = "Office";
        }
        else{
            siteName = editText_siteName.getText().toString();
        }
        SiteLocationModel siteLocationModel = new SiteLocationModel(username,formatDate,siteName,
                currentAddress,checkInTime,"checkOut",mobileNo);
        databaseReference1.child(mobileNo).setValue(siteLocationModel);
        View popupView = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_confirm, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.
                LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();

        relativeLayout_Okay = popupView.findViewById(R.id.relativeLayout_Okay);
        textView_confirm = popupView.findViewById(R.id.textView_confirm);
        textView_confirm.setText("Hi,"+username+"your check in Successfully, visit site location");
        relativeLayout_Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}