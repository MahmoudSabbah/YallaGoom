package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;

import java.util.List;

public class RecycleViewGenderLevelAge extends RecyclerView.Adapter<RecycleViewGenderLevelAge.MyViewHolder> {

    private List<String> alertList;
    public Context context;
    public int selectPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView spinner_text;
        private final RelativeLayout parent;

        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            spinner_text = (TextView) view.findViewById(R.id.spinner_text);
            parent = (RelativeLayout) view.findViewById(R.id.parent);
        }
    }

    public RecycleViewGenderLevelAge(List<String> alertList) {
        this.alertList = alertList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.spinner_text.setText(alertList.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPosition = position;
            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return alertList.size();
    }
}