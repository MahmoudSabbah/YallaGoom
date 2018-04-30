package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.AllSport;
import com.yallagoom.entity.SportObject;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewSportList extends RecyclerView.Adapter<RecycleViewSportList.MyViewHolder> {

    private final RecyclerView recyclerView;
    private ArrayList<SportObject> dataArrayList;
    public Context context;
    // private List<Driver> drivers;
    public int lastPosition = -1;
    private int prePosition=-1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name_sport;
        private final TextView id_sport;
        private final RelativeLayout parent;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name_sport = (TextView) view.findViewById(R.id.name_sport);
            id_sport = (TextView) view.findViewById(R.id.id_sport);
            parent = (RelativeLayout) view.findViewById(R.id.parent);


        }
    }


    public RecycleViewSportList(RecyclerView recyclerView,ArrayList<SportObject> dataArrayList) {
        this.dataArrayList = dataArrayList;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecycleViewSportList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sports_list, parent, false);

        return new RecycleViewSportList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewSportList.MyViewHolder holder, final int position) {
        holder.name_sport.setText(dataArrayList.get(position).getName_en());
        holder.id_sport.setText(dataArrayList.get(position).getSport_id()+"");
     /*   holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("textNew","textNew");
                prePosition = position;

               *//* if (prePosition != position) {
                    TextView textNew = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.name_sport);
                    if (prePosition != -1) {
                        TextView textLast = recyclerView.findViewHolderForAdapterPosition(prePosition).itemView.findViewById(R.id.name_sport);
                        textLast.setSelected(false);
                        Log.e("textLast",""+textLast.getText().toString());
                    }
                    Log.e("textNew",""+textNew.getText().toString());
                    textNew.setSelected(true);

                    prePosition = position;
                }*//*
            }
        });*/
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return dataArrayList.size();
    }
}
