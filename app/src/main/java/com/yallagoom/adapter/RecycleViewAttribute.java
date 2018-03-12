package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Booking.OtherAttributesValues;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewAttribute extends RecyclerView.Adapter<RecycleViewAttribute.MyViewHolder> {

    private final ArrayList<OtherAttributesValues> other_attributes_values;
    private final double price;
    public Context context;

    public RecycleViewAttribute(ArrayList<OtherAttributesValues> other_attributes_values, double price) {
        this.other_attributes_values=other_attributes_values;
        this.price=price;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView value;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);


        }
    }




    @Override
    public RecycleViewAttribute.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_attribute, parent, false);

        return new RecycleViewAttribute.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewAttribute.MyViewHolder holder, final int position) {
        if (position==0){
            holder.name.setText(R.string.price);
            holder.value.setText(price+"");
        }else {
            holder.name.setText(other_attributes_values.get(position-1).getAttribute_title().getAttribute());
            holder.value.setText(other_attributes_values.get(position-1).getValue());
        }

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (other_attributes_values.size() + 1);
    }
}
