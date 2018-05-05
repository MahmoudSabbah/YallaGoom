package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Matches.ResultsList;

import java.util.ArrayList;

public class RecycleViewClickMatchSegmentedResult extends RecyclerView.Adapter<RecycleViewClickMatchSegmentedResult.MyViewHolder>  {


    private final ArrayList<ResultsList> resultsLists;
    public Context context;


    public RecycleViewClickMatchSegmentedResult(ArrayList<ResultsList> resultsLists ) {
        this.resultsLists = resultsLists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_click_match_segmented, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.name.setText(resultsLists.get(position).getResult_title().getName());
            holder.value.setText(resultsLists.get(position).getResult_value()+"");

    }

    @Override
    public int getItemCount() {
        return resultsLists.size();
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