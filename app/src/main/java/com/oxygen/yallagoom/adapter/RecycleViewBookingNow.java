package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Booking.BookingRowsData;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewBookingNow extends RecyclerView.Adapter<RecycleViewBookingNow.MyViewHolder> {

    private final ArrayList<BookingRowsData> rows_data;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView number;
        private final SmoothCheckBox checkbox_type;
        private final RecyclerView att_list;
        private final FloatingActionButton increase;
        private final FloatingActionButton decrease;
        private final LinearLayout layout_number;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            checkbox_type = (SmoothCheckBox) view.findViewById(R.id.checkbox_type);
            title = (TextView) view.findViewById(R.id.title);
            number = (TextView) view.findViewById(R.id.number);
            att_list = (RecyclerView) view.findViewById(R.id.att_list);
            increase = (FloatingActionButton) view.findViewById(R.id.increase);
            decrease = (FloatingActionButton) view.findViewById(R.id.decrease);
            layout_number = (LinearLayout) view.findViewById(R.id.layout_number);


        }
    }


    public RecycleViewBookingNow(ArrayList<BookingRowsData> rows_data) {
        this.rows_data=rows_data;

    }

    @Override
    public RecycleViewBookingNow.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_booking_now, parent, false);

        return new RecycleViewBookingNow.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewBookingNow.MyViewHolder holder, final int position) {

        RecycleViewAttribute recycleViewAttribute = new RecycleViewAttribute(rows_data.get(position).getOther_attributes_values(),rows_data.get(position).getPrice());
        holder.att_list.setAdapter(recycleViewAttribute);
        holder.title.setText(rows_data.get(position).getTitle());
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(holder.number.getText().toString());
                value=value+1;
                holder.number.setText((value)+"");
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(holder.number.getText().toString());
                if (value>0){
                    value=value-1;
                    holder.number.setText((value)+"");
                }

            }
        });
        holder.checkbox_type.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked){
                    holder.layout_number.setVisibility(View.VISIBLE);
                }else {
                    holder.layout_number.setVisibility(View.GONE);
                    holder.number.setText("0");
                }
            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return rows_data.size();
    }
}
