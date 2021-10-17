package com.example.gk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gk.R;
import com.example.gk.model.RegisteredModel;
import com.example.gk.model.SiteModel;

import java.util.ArrayList;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.MyViewHolder> {

    Context context;
    ArrayList<RegisteredModel> registeredModels;

    public UserProfileAdapter(Context context, ArrayList<RegisteredModel> registeredModels) {
        this.context = context;
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
        holder.textView_unique_id.setText(registeredModel.getEmailID());
        char result = registeredModel.getUsername().charAt(0);
        holder.textView_firstLetter.setText(String.valueOf(result));
    }

    @Override
    public int getItemCount() {
        return registeredModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_firstLetter,textView_username,textView_unique_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_firstLetter = itemView.findViewById(R.id.textView_firstLetter);
            textView_username = itemView.findViewById(R.id.textView_username);
            textView_unique_id = itemView.findViewById(R.id.textView_unique_id);
        }
    }
}
