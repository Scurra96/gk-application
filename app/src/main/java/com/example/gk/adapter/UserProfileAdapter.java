package com.example.gk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gk.R;
import com.example.gk.admin.UserProfileActivity;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteModel;

import java.util.ArrayList;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.MyViewHolder> {

    Context context;
    ArrayList<RegisteredModel> registeredModels;

    public UserProfileAdapter(Context applicationContext, ArrayList<RegisteredModel> registeredModels) {
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
        RegisteredModel registeredModel = registeredModels.get(position);
        holder.textView_username.setText(registeredModel.getUsername());
        holder.textView_unique_id.setText(registeredModel.getUniqueID());
        char result = registeredModel.getUsername().charAt(0);
        holder.textView_firstLetter.setText(String.valueOf(result));
        holder.relativeLayout_userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UserProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("user_username",registeredModel.getUsername());
                i.putExtra("user_emailID",registeredModel.getEmailID());
                i.putExtra("user_mobileNo",registeredModel.getMobileNo());
                i.putExtra("user_address",registeredModel.getAddress());
                i.putExtra("user_uniqueNo",registeredModel.getUniqueID());
                i.putExtra("user_status",registeredModel.getStatus());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return registeredModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_firstLetter,textView_username,textView_unique_id;
        RelativeLayout relativeLayout_userProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_firstLetter = itemView.findViewById(R.id.textView_firstLetter);
            textView_username = itemView.findViewById(R.id.textView_username);
            textView_unique_id = itemView.findViewById(R.id.textView_unique_id);
            relativeLayout_userProfile = itemView.findViewById(R.id.relativeLayout_userProfile);
        }
    }
}
