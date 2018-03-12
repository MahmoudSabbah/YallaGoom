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

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDetailsLeagueSchedule2Fragment extends RecyclerView.Adapter<RecycleViewDetailsLeagueSchedule2Fragment.MyViewHolder> {

    private final MatchesList[] matchesLists2;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView home;
        private final TextView away;
        private final TextView away_value;
        private final TextView home_value;
        private final TextView time_value;
        private final TextView status;
        private final LinearLayout score_layout;
        private final RelativeLayout time_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            home = (TextView) view.findViewById(R.id.home_team);
            away = (TextView) view.findViewById(R.id.away_team);
            away_value = (TextView) view.findViewById(R.id.away_value);
            home_value = (TextView) view.findViewById(R.id.home_value);
            time_value = (TextView) view.findViewById(R.id.time_value);
            status = (TextView) view.findViewById(R.id.status);
            score_layout = (LinearLayout) view.findViewById(R.id.score_layout);
            time_layout = (RelativeLayout) view.findViewById(R.id.time_layout);
        }
    }


    public RecycleViewDetailsLeagueSchedule2Fragment(MatchesList[] matchesLists2) {
        this.matchesLists2 = matchesLists2;
    }

    @Override
    public RecycleViewDetailsLeagueSchedule2Fragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule2_league_details_list, parent, false);

        return new RecycleViewDetailsLeagueSchedule2Fragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDetailsLeagueSchedule2Fragment.MyViewHolder holder, final int position) {
        holder.home.setText(matchesLists2[position].getCompetitors_1().getCompetitors_name());
        holder.away.setText(matchesLists2[position].getCompetitors_2().getCompetitors_name());
        holder.status.setText(matchesLists2[position].getStatus());
        if (matchesLists2[position].getStatus().equalsIgnoreCase("closed")){
            if (matchesLists2[position].getMatch_result()!=null){
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time_layout.setVisibility(View.GONE);
                holder.home_value.setText(matchesLists2[position].getMatch_result().getHome_score()+"");
                holder.away_value.setText(matchesLists2[position].getMatch_result().getAway_score()+"");
            }else {
                holder.score_layout.setVisibility(View.GONE);
                holder.time_layout.setVisibility(View.VISIBLE);
                holder.time_value.setText("-");
            }
        }else if (matchesLists2[position].getStatus().equalsIgnoreCase("not_started")){
            holder.score_layout.setVisibility(View.GONE);
            holder.time_layout.setVisibility(View.VISIBLE);
            holder.time_value.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(matchesLists2[position].getScheduled(),"yyyy-MM-dd'T'HH:mm:ssZ"), Constant.hh_mm_aa));
        }

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return matchesLists2.length;
    }
}
