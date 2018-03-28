package com.yallagoom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.activity.ClickMatchActivity;
import com.yallagoom.api.GetMatchDetailsApiAsyncTask;
import com.yallagoom.entity.Matches.MatchesList;
import com.yallagoom.entity.Matches.NewApi.FinalResultData_Data_Data;
import com.yallagoom.interfaces.MatchDetailsCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDetailsLeagueScheduleFragment extends RecyclerView.Adapter<RecycleViewDetailsLeagueScheduleFragment.MyViewHolder> {

    private final ArrayList<FinalResultData_Data_Data> matchesLists;
    private final Activity mContext;
    public Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView competition_name;
        private final RecyclerView third_sports_list;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            competition_name = (TextView) view.findViewById(R.id.competition_name);
            third_sports_list = (RecyclerView) view.findViewById(R.id.third_sports_list);
            third_sports_list.setNestedScrollingEnabled(false);
        }
    }


    public RecycleViewDetailsLeagueScheduleFragment(ArrayList<FinalResultData_Data_Data> matchesLists, Activity mContext) {
        this.matchesLists = matchesLists;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();

    }

    @Override
    public RecycleViewDetailsLeagueScheduleFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_sport_details_list_league, parent, false);
        RecycleViewDetailsLeagueScheduleFragment.MyViewHolder viewHolder = new RecycleViewDetailsLeagueScheduleFragment.MyViewHolder(itemView);
        viewHolder.third_sports_list.setRecycledViewPool(recycledViewPool);
        return new RecycleViewDetailsLeagueScheduleFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDetailsLeagueScheduleFragment.MyViewHolder holder, final int position) {
        holder.competition_name.setText(matchesLists.get(position).getCompetition());
        RecycleViewDetailsMatchesScheduleFragment recycleViewDetailsMatchesScheduleFragment = new RecycleViewDetailsMatchesScheduleFragment(matchesLists.get(position).getData(), mContext);
        holder.third_sports_list.setAdapter(recycleViewDetailsMatchesScheduleFragment);
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return matchesLists.size();
    }
}
