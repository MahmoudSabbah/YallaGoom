package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Matches.MatchesList;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDetailsMatchesScheduleFragment extends RecyclerView.Adapter<RecycleViewDetailsMatchesScheduleFragment.MyViewHolder> {

    private final MatchesList[] matchesLists;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView home;
        private final TextView away;
        private final TextView away_value;
        private final TextView home_value;
        private final TextView time_value;
        private final LinearLayout score_layout;
        private final RelativeLayout time_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            home = (TextView) view.findViewById(R.id.home);
            away = (TextView) view.findViewById(R.id.away);
            away_value = (TextView) view.findViewById(R.id.away_value);
            home_value = (TextView) view.findViewById(R.id.home_value);
            time_value = (TextView) view.findViewById(R.id.time_value);
            score_layout = (LinearLayout) view.findViewById(R.id.score_layout);
            time_layout = (RelativeLayout) view.findViewById(R.id.time_layout);
        }
    }


    public RecycleViewDetailsMatchesScheduleFragment(MatchesList[] matchesLists) {
        this.matchesLists = matchesLists;
    }

    @Override
    public RecycleViewDetailsMatchesScheduleFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_sport_details_list, parent, false);

        return new RecycleViewDetailsMatchesScheduleFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDetailsMatchesScheduleFragment.MyViewHolder holder, final int position) {
        holder.home.setText(matchesLists[position].getCompetitors_1().getCompetitors_name());
        holder.away.setText(matchesLists[position].getCompetitors_2().getCompetitors_name());
        if (matchesLists[position].getStatus().equalsIgnoreCase("closed")){
            if (matchesLists[position].getMatch_result()!=null){
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time_layout.setVisibility(View.GONE);
                holder.home_value.setText(matchesLists[position].getMatch_result().getHome_score()+"");
                holder.away_value.setText(matchesLists[position].getMatch_result().getAway_score()+"");
            }else {
                holder.score_layout.setVisibility(View.GONE);
                holder.time_layout.setVisibility(View.VISIBLE);
                holder.time_value.setText("-");
            }
        }else if (matchesLists[position].getStatus().equalsIgnoreCase("not_started")){
            holder.score_layout.setVisibility(View.GONE);
            holder.time_layout.setVisibility(View.VISIBLE);
            holder.time_value.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(matchesLists[position].getScheduled(),"yyyy-MM-dd'T'HH:mm:ssZ"), Constant.hh_mm_aa));
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return matchesLists.length;
    }
}
