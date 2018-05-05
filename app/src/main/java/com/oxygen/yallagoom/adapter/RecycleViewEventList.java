package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Event;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewEventList extends RecyclerView.Adapter<RecycleViewEventList.MyViewHolder> {

    private final RecyclerView recyclerView;
    private final ArrayList<Event.DataEvent> dataList;
    public Context context;
   // public int prePosition = -1;
    //public int eventId = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView check;
        private final TextView id_value;
        private final LinearLayout parent;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.name);
            check = (TextView) view.findViewById(R.id.check);
            id_value = (TextView) view.findViewById(R.id.id_value);
            parent = (LinearLayout) view.findViewById(R.id.parent);

        }
    }


    public RecycleViewEventList(RecyclerView recyclerView, ArrayList<Event.DataEvent> data) {
        this.recyclerView = recyclerView;
        this.dataList = data;
    }

    @Override
    public RecycleViewEventList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_event_list, parent, false);

        return new RecycleViewEventList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewEventList.MyViewHolder holder, final int position) {
        holder.name.setText(dataList.get(position).getEventTitle());
        holder.id_value.setText(dataList.get(position).getId()+"");
    /*    holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prePosition != position) {

                    TextView check1 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.check);
                    if (prePosition != -1) {
                        TextView check2 = recyclerView.findViewHolderForAdapterPosition(prePosition).itemView.findViewById(R.id.check);
                        check2.setVisibility(View.GONE);
                    }
                    check1.setVisibility(View.VISIBLE);
                    prePosition = position;
                    eventId=dataList.get(position).getId();
                    viewEventListCallback.processFinish(position);



                }
            }
        });*/
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return dataList.size();
    }
}
