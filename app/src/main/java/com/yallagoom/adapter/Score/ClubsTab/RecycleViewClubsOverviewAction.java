package com.yallagoom.adapter.Score.ClubsTab;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Matches.Player.Player_incident_data_count;

import java.util.ArrayList;

public class RecycleViewClubsOverviewAction extends RecyclerView.Adapter<RecycleViewClubsOverviewAction.MyViewHolder>  {


    private final Activity mContext;
    private final ArrayList<Player_incident_data_count> incident_count;
    public Context context;

    public RecycleViewClubsOverviewAction(Activity activity, ArrayList<Player_incident_data_count> incident_count) {
        this.mContext = activity;
        this.incident_count = incident_count;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_player_details_action, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.title.setText(incident_count.get(position).getIncident_name());
    holder.number.setText(incident_count.get(position).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return incident_count.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final View view;
        private final TextView title;


        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            number = (TextView) itemView.findViewById(R.id.number);
            title = (TextView) itemView.findViewById(R.id.title);
            view = (View) itemView.findViewById(R.id._view);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}