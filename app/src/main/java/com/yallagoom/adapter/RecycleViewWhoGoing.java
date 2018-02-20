package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewWhoGoing extends RecyclerView.Adapter<RecycleViewWhoGoing.MyViewHolder> {

    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();


        }
    }


    public RecycleViewWhoGoing() {

    }

    @Override
    public RecycleViewWhoGoing.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_who_going, parent, false);

        return new RecycleViewWhoGoing.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewWhoGoing.MyViewHolder holder, final int position) {

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return 10;
    }
}
