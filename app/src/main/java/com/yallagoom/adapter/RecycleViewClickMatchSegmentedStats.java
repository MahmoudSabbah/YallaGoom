package com.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Matches.ResultsList;
import com.yallagoom.entity.Matches.StatsList;

import java.util.ArrayList;

public class RecycleViewClickMatchSegmentedStats extends RecyclerView.Adapter<RecycleViewClickMatchSegmentedStats.MyViewHolder>  {


    private final ArrayList<StatsList> statsLists;
    public Context context;


    public RecycleViewClickMatchSegmentedStats(ArrayList<StatsList> statsLists ) {
        this.statsLists = statsLists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_click_match_segmented, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.name.setText(statsLists.get(position).getStats_title().getName());
            holder.value.setText(statsLists.get(position).getResult_value()+"");

    }

    @Override
    public int getItemCount() {
        return statsLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView value;
        private final LinearLayout parent_;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.name);
            value = (TextView) itemView.findViewById(R.id.value);
            parent_ = (LinearLayout) itemView.findViewById(R.id.parent_);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}