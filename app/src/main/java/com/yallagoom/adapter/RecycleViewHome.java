package com.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Event;

import java.util.ArrayList;

public class RecycleViewHome extends RecyclerView.Adapter<RecycleViewHome.MyViewHolder> {

    private ArrayList<Event.DataEvent> nearEvent;
    public Context context;
    // private List<Driver> drivers;
    public int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView cat_type;
        private final TextView title;
        private final TextView start_date;
        private final TextView cost;


        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            cat_type = (TextView) view.findViewById(R.id.cat_type);
            title = (TextView) view.findViewById(R.id.title);
            start_date = (TextView) view.findViewById(R.id.start_date);
            cost = (TextView) view.findViewById(R.id.cost);


        }
    }


    public RecycleViewHome(ArrayList<Event.DataEvent> nearEvent) {
        this.nearEvent = nearEvent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home_fragment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.cat_type.setText("#" + nearEvent.get(position).getCategory().getCategoryName());
        holder.title.setText(nearEvent.get(position).getEventTitle());
        holder.start_date.setText(nearEvent.get(position).getStartEventDate() + " " + nearEvent.get(position).getStartEventTime());
        Log.e("getCost",""+nearEvent.get(position).getCost());

        if (nearEvent.get(position).getCost() == null) {
            holder.cost.setText(context.getString(R.string.free));

        } else if (Double.parseDouble(nearEvent.get(position).getCost()) == 0) {
            holder.cost.setText(context.getString(R.string.free));
        } else {
            holder.cost.setText("$" + nearEvent.get(position).getCost());
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return nearEvent.size();
    }
}