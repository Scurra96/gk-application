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
import com.example.gk.admin.SiteLocationActivity;
import com.example.gk.model.SiteLocationModel;
import com.example.gk.model.SiteModel;

import java.util.ArrayList;
import java.util.Locale;

public class ListOfSitesAdapter extends RecyclerView.Adapter<ListOfSitesAdapter.MyViewHolder> {

    Context context;
    ArrayList<SiteLocationModel> siteLocationModels;

    public ListOfSitesAdapter(Context applicationContext, ArrayList<SiteLocationModel> siteLocationModels) {
        this.context = applicationContext;
        this.siteLocationModels = siteLocationModels;
    }

    @NonNull
    @Override
    public ListOfSitesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_card_list_of_site,
                parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfSitesAdapter.MyViewHolder holder, int position) {
        SiteLocationModel siteModel = siteLocationModels.get(position);
        holder.textView_siteLocation.setText(siteModel.getSiteName());
//        holder.textView_siteLocation.setText(siteModel.getUsername());
//        holder.textView_siteLocation.setText(siteModel.getSiteLocation());
//        holder.textView_siteLocation.setText(siteModel.getCheckIn_Out());

        char result = siteModel.getSiteName().charAt(0);
        holder.textView_firstLetter.setText(String.valueOf(result)
                .toUpperCase(Locale.ROOT));

        holder.relativeLayout_siteList_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SiteLocationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("siteLocation",siteModel.getSiteLocation());
                i.putExtra("siteLocationUsername",siteModel.getUsername());
                i.putExtra("siteName",siteModel.getSiteName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return siteLocationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_dateAndTime,textView_siteLocation,textView_username,textView_firstLetter;
        RelativeLayout relativeLayout_siteList_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_siteLocation = itemView.findViewById(R.id.textView_siteLocation);
            textView_firstLetter = itemView.findViewById(R.id.textView_firstLetter);
            relativeLayout_siteList_card = itemView.findViewById(R.id.relativeLayout_siteList_card);
        }
    }
}
