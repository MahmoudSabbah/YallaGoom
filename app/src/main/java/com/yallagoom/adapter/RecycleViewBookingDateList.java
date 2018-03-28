package com.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.api.AddFriendAsyncTask;
import com.yallagoom.entity.Booking.TicketsDates;
import com.yallagoom.interfaces.AddFriendCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleViewBookingDateList extends RecyclerView.Adapter<RecycleViewBookingDateList.MyViewHolder>  {


    private final ArrayList<TicketsDates> tickets_dates;
    public Context context;


    public RecycleViewBookingDateList(ArrayList<TicketsDates> tickets_dates) {
        this.tickets_dates = tickets_dates;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_booking_date, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.date_value.setText(ToolUtils.convertDateFromFormatToFormat(tickets_dates.get(position).getDate(), Constant.yyyy_MM_dd,Constant.dd_MM_yyyy));

    }

    @Override
    public int getItemCount() {
        return tickets_dates.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView date_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            date_value = (TextView) itemView.findViewById(R.id.date_value);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}