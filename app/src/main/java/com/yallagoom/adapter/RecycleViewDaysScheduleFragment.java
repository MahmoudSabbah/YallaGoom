package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.utils.Constant;

import java.util.List;


/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDaysScheduleFragment extends RecyclerView.Adapter<RecycleViewDaysScheduleFragment.MyViewHolder> {

    private final RecyclerView recyclerView;
    private final List<String> allDays;
    private final String currentDate;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView day_name;
        private final TextView day_number;
        private final TextView month_name;
        private final TextView full_time;
        private final LinearLayout layout_select;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            month_name = (TextView) view.findViewById(R.id.month_name);
            full_time = (TextView) view.findViewById(R.id.full_time);
            day_number = (TextView) view.findViewById(R.id.day_number);
            day_name = (TextView) view.findViewById(R.id.day_name);
            layout_select = (LinearLayout) view.findViewById(R.id.layout_select);

        }
    }


    public RecycleViewDaysScheduleFragment(RecyclerView recyclerView, List<String> allDays, String currentDate) {
       this.recyclerView = recyclerView;
        this.allDays = allDays;
        this.currentDate = currentDate;
    }

    @Override
    public RecycleViewDaysScheduleFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_days_list, parent, false);

        return new RecycleViewDaysScheduleFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDaysScheduleFragment.MyViewHolder holder, final int position) {

        String[] format = allDays.get(position).split("-");
        holder.month_name.setText(format[0]);
        holder.day_number.setText(format[1]);
        holder.day_name.setText(format[2]);
        holder.full_time.setText(allDays.get(position));
     /*   if (currentDate.equalsIgnoreCase(allDays.get(position))&& Constant.lastPos==-1){
            Log.e("lastPos",""+Constant.lastPos);
            holder.layout_select.setSelected(true);
            recyclerView.smoothScrollToPosition(position);
            Constant.lastPos= position;
            Log.e("lastPosAfter",""+Constant.lastPos);

        }*/

  }
   /*   @Override
    public long getItemId(int position) {
        return position;
    }*/

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return allDays.size();
    }

}
