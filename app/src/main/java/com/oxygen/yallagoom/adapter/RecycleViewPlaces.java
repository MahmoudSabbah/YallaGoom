package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;

import java.util.HashMap;
import java.util.List;

public class RecycleViewPlaces extends RecyclerView.Adapter<RecycleViewPlaces.MyViewHolder> {

    private List<HashMap<String, String>> places;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView address;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            address = (TextView) view.findViewById(R.id.address);

        }
    }


    public RecycleViewPlaces(List<HashMap<String, String>> places) {
        this.places = places;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_places_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.address.setText(places.get(position).get("description"));
    }


    @Override
    public int getItemCount() {

        return places.size();
    }
}