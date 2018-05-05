package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.oxygen.yallagoom.R;

public class RecycleViewTest extends RecyclerView.Adapter<RecycleViewTest.MyViewHolder> {

    public Context context;

    public RecycleViewTest() {


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_incidents, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e("getItemViewType", ""+(holder.getItemViewType()));
        if (position % 2 == 0) {
            holder.mes_left.setVisibility(View.VISIBLE);
            holder.mes_right.setVisibility(View.GONE);
        } else {
            holder.mes_left.setVisibility(View.GONE);
            holder.mes_right.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            holder.small_circle.setVisibility(View.VISIBLE);
        } else {
            holder.small_circle.setVisibility(View.GONE);
        }
        if (position == 9) {
            holder.view2_lay.setVisibility(View.VISIBLE);
        } else {
            holder.view2_lay.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mes_left;
        private final LinearLayout mes_right;
        private final RelativeLayout view2_lay;
        private final RelativeLayout view1_lay;
        private final RelativeLayout small_circle;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mes_left = (RelativeLayout) itemView.findViewById(R.id.mes_left);
            view2_lay = (RelativeLayout) itemView.findViewById(R.id.view2_lay);
            view1_lay = (RelativeLayout) itemView.findViewById(R.id.view1_lay);
            small_circle = (RelativeLayout) itemView.findViewById(R.id.small_circle);
            mes_right = (LinearLayout) itemView.findViewById(R.id.mes_right);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}