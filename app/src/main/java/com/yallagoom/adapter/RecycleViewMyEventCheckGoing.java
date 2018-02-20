package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewMyEventCheckGoing extends RecyclerView.Adapter<RecycleViewMyEventCheckGoing.MyViewHolder> {

    public Context context;
    String listdData[] = {"May be", "Going", "Not interested"};

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView count;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
        }
    }


    public RecycleViewMyEventCheckGoing() {

    }

    @Override
    public RecycleViewMyEventCheckGoing.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_event_check_going, parent, false);

        return new RecycleViewMyEventCheckGoing.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewMyEventCheckGoing.MyViewHolder holder, final int position) {
        holder.title.setText(listdData[position]);
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return listdData.length;
    }
}
