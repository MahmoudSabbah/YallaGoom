package com.oxygen.yallagoom.adapter.Settings;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

public class RecycleViewSettingsStatus extends RecyclerView.Adapter<RecycleViewSettingsStatus.MyViewHolder> {


    private final Activity mContext;
    private final String[] data;
    private final String status;
    public Context context;
    private SmoothCheckBox lastCheckedRB = null;
    public String valueSelect = "";

    public RecycleViewSettingsStatus(Activity activity, String[] data, String status) {
        this.mContext = activity;
        this.data = data;
        this.status = status;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_settings_status_, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.value.setText(data[position]);
        if (status.equalsIgnoreCase(data[position])) {
            lastCheckedRB = holder.checkbox_select;
            holder.checkbox_select.setChecked(true);
        }
        holder.checkbox_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = holder.checkbox_select;
                holder.checkbox_select.setChecked(true);
                valueSelect = holder.value.getText().toString();
            }
        });
     /*   holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                Log.e("holder" +
                        "", "" + holder.value.getText().toString());


            }
        });*/

       /* holder.checkbox_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                if (holder.checkbox_select.isChecked()) {
                    holder.checkbox_select.setChecked(false);
                } else {
                    holder.checkbox_select.setChecked(true);
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView value;
        private final SmoothCheckBox checkbox_select;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            value = (TextView) view.findViewById(R.id.value);
            checkbox_select = (SmoothCheckBox) view.findViewById(R.id.checkbox_select);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}