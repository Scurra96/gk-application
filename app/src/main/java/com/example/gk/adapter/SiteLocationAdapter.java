package com.example.gk.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gk.R;
import com.example.gk.admin.AdminHomeActivity;
import com.example.gk.admin.SiteLocationActivity;
import com.example.gk.model.SiteLocationModel;
import com.example.gk.model.SiteModel;

import java.util.ArrayList;

public class SiteLocationAdapter extends RecyclerView.Adapter<SiteLocationAdapter.MyViewHolder> {

    Context context;
    ArrayList<SiteLocationModel> siteLocationModels;

    public SiteLocationAdapter(Context applicationContext, ArrayList<SiteLocationModel> siteLocationModels) {
        this.context = applicationContext;
        this.siteLocationModels = siteLocationModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_card_site_location,
                parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SiteLocationModel siteModel = siteLocationModels.get(position);
        holder.textView_dateAndTime.setText(siteModel.getDate()+","+siteModel.getCheckIn());
        holder.textView_siteLocation.setText(siteModel.getSiteLocation());
        holder.textView_siteUsername.setText(siteModel.getSiteName());
        holder.textView_username.setText(siteModel.getUsername());

        Log.d("String123",""+siteModel.getSiteLocation());


        holder.relativeLayout_siteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SiteLocationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("siteLocation",siteModel.getSiteLocation());
                i.putExtra("siteLocationUsername",siteModel.getUsername());
                i.putExtra("siteDate",siteModel.getDate());
                i.putExtra("siteCheckIn",siteModel.getCheckIn());
                i.putExtra("siteCheckOut",siteModel.getCheckOut());
                i.putExtra("siteMobile",siteModel.getMobile());
                i.putExtra("siteName",siteModel.getSiteName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return siteLocationModels.size();
    }
//    public int getItemCount() {
//        return 4;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_dateAndTime,textView_siteLocation,textView_username,textView_siteUsername;
        RelativeLayout relativeLayout_siteCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_dateAndTime = itemView.findViewById(R.id.textView_dateAndTime);
            textView_siteLocation = itemView.findViewById(R.id.textView_siteLocation);
            textView_username = itemView.findViewById(R.id.textView_username);
            textView_siteUsername = itemView.findViewById(R.id.textView_siteUsername);
            relativeLayout_siteCard = itemView.findViewById(R.id.relativeLayout_siteCard);
        }
    }
}
