package com.example.gk.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gk.R;
import com.example.gk.admin.UserProfileActivity;
import com.example.gk.model.RegisterModel;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteModel;

import java.util.ArrayList;
import java.util.Locale;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.MyViewHolder> {

    Context context;
    ArrayList<RegisterModel> registeredModels;

    public UserProfileAdapter(Context applicationContext, ArrayList<RegisterModel> registeredModels) {
        this.context = applicationContext;
        this.registeredModels = registeredModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_card_user_profile,
                parent,false);
        return new UserProfileAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RegisterModel registeredModel = registeredModels.get(position);
        holder.textView_username.setText(registeredModel.getUsername());
        holder.textView_unique_id.setText(registeredModel.getUniqueID());
        if(registeredModel.getStatus().equalsIgnoreCase("Active")){
            holder.textView_active_inactive_status.setText(registeredModel.getStatus());
            holder.imageView_statusActiveColor.setVisibility(View.VISIBLE);
            holder.imageView_statusInactiveColor.setVisibility(View.GONE);
        }else{
            holder.textView_active_inactive_status.setText(registeredModel.getStatus());
        }
        char result = registeredModel.getUsername().charAt(0);
        holder.textView_firstLetter.setText(String.valueOf(result)
                .toUpperCase(Locale.ROOT));
        holder.relativeLayout_userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Mobile", registeredModel.getMobileNo());

                Intent i = new Intent(context, UserProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("user_MobileNumber",registeredModel.getMobileNo());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return registeredModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_firstLetter,textView_username,textView_unique_id,
                textView_active_inactive_status;
        RelativeLayout relativeLayout_userProfile;
        ImageView imageView_statusInactiveColor,imageView_statusActiveColor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_firstLetter = itemView.findViewById(R.id.textView_firstLetter);
            textView_username = itemView.findViewById(R.id.textView_username);
            textView_unique_id = itemView.findViewById(R.id.textView_unique_id);
            imageView_statusInactiveColor = itemView.findViewById(R.id.imageView_statusInactiveColor);
            imageView_statusActiveColor = itemView.findViewById(R.id.imageView_statusActiveColor);
            textView_active_inactive_status = itemView.findViewById(R.id.textView_active_inactive_status);
            relativeLayout_userProfile = itemView.findViewById(R.id.relativeLayout_userProfile);
        }
    }
}
