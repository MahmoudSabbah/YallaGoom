package com.oxygen.yallagoom.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Matches.NewApi.FinalResultData_Data;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class ListTestMAtch extends BaseAdapter {
    private final ArrayList<FinalResultData_Data> leagueMatchesList;
    private final Activity context;
    LayoutInflater inflater;

    public ListTestMAtch(Activity context, ArrayList<FinalResultData_Data> leagueMatchesList) {
        this.leagueMatchesList = leagueMatchesList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public int getCount() {
        return leagueMatchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView name;
        RecyclerView second_sports_list;
        public TextView time;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_schedule_sport_main_list, parent, false);

           // convertView = inflater.inflate(R.layout.list_item_schedule_sport_main_list, null);
            holder.second_sports_list = (RecyclerView) convertView.findViewById(R.id.second_sports_list);
            RecycleViewDetailsLeagueScheduleFragment recycleViewDetailsLeagueScheduleFragment = new RecycleViewDetailsLeagueScheduleFragment(leagueMatchesList.get(position).getData(), context);
            holder.second_sports_list.setAdapter(recycleViewDetailsLeagueScheduleFragment);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getStart_date(), Constant.yyyy_MM_dd), Constant.EEEE_dd_MMM_yyyy));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
