package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Matches.DetailsList;

import java.util.ArrayList;

public class RecycleViewClickMatchSegmentedDetails extends RecyclerView.Adapter<RecycleViewClickMatchSegmentedDetails.MyViewHolder>  {


    private final ArrayList<DetailsList> details;
    public Context context;


    public RecycleViewClickMatchSegmentedDetails(ArrayList<DetailsList> details) {
        this.details = details;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_click_match_segmented, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(details.get(position).getDetails().getName());
        holder.value.setText(details.get(position).getDetails_value()+"");
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView value;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = (TextView) itemView.findViewById(R.id.name);
            value = (TextView) itemView.findViewById(R.id.value);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}