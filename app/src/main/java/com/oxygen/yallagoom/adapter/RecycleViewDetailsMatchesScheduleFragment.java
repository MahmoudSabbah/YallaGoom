package com.oxygen.yallagoom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.ClickMatchActivity;
import com.oxygen.yallagoom.api.GetMatchDetailsApiAsyncTask;
import com.oxygen.yallagoom.entity.Matches.NewApi.FinalResultData_Data_Data_Data;
import com.oxygen.yallagoom.interfaces.MatchDetailsCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDetailsMatchesScheduleFragment extends RecyclerView.Adapter<RecycleViewDetailsMatchesScheduleFragment.MyViewHolder> {

    private final ArrayList<FinalResultData_Data_Data_Data> matchesLists;
    private final Activity mContext;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView home;
        private final TextView away;
        private final TextView away_value;
        private final TextView home_value;
        private final TextView time_value;
        private final LinearLayout score_layout;
        private final RelativeLayout time_layout;
        private final CardView parent_card;

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
            parent_card = (CardView) view.findViewById(R.id.parent_card);
        }
    }


    public RecycleViewDetailsMatchesScheduleFragment(ArrayList<FinalResultData_Data_Data_Data> matchesLists, Activity mContext) {
        this.matchesLists = matchesLists;
        this.mContext = mContext;
    }

    @Override
    public RecycleViewDetailsMatchesScheduleFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_sport_details_list_match, parent, false);

        return new RecycleViewDetailsMatchesScheduleFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDetailsMatchesScheduleFragment.MyViewHolder holder, final int position) {
        if (matchesLists.get(position).getParticipants_list().size() == 2) {
            holder.home.setText(matchesLists.get(position).getParticipants_list().get(0).getParticipants_data().getShort_name());
            holder.away.setText(matchesLists.get(position).getParticipants_list().get(1).getParticipants_data().getShort_name());
            if (matchesLists.get(position).getStatus_type().equalsIgnoreCase("scheduled")) {
                holder.score_layout.setVisibility(View.GONE);
                holder.time_layout.setVisibility(View.VISIBLE);
                holder.time_value.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(matchesLists.get(position).getStart_date(), Constant.yyyy_MM_dd__HH_mm), Constant.hh_mm_aa));
            } else {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time_layout.setVisibility(View.GONE);
                for (int j = 0; j < matchesLists.get(position).getResults_list().size(); j++) {
                    //Log.e(""+ matchesLists.get(position).getResults_list().get(j).getParticipants_id(),""+matchesLists.get(position).getParticipants_list().get(0).getParticipant_id());
                    if (matchesLists.get(position).getParticipants_list().get(0).getParticipant_id() == matchesLists.get(position).getResults_list().get(j).getParticipants_id()
                            && matchesLists.get(position).getResults_list().get(j).getResult_title().getName().equalsIgnoreCase("Result")) {
                        holder.home_value.setText(matchesLists.get(position).getResults_list().get(j).getResult_value() + "");
                    } else if (matchesLists.get(position).getParticipants_list().get(1).getParticipant_id() == matchesLists.get(position).getResults_list().get(j).getParticipants_id()
                            && matchesLists.get(position).getResults_list().get(j).getResult_title().getName().equalsIgnoreCase("Result")) {
                        holder.away_value.setText(matchesLists.get(position).getResults_list().get(j).getResult_value() + "");
                    }
                }
              /*  if (matchesLists[position].getParticipants_list().get(0).getParticipant_id() == matchesLists[position].getResults_list().get(2).getParticipants_id()) {
                    holder.home_value.setText(matchesLists[position].getResults_list().get(2).getResult_value() + "");
                    holder.away_value.setText(matchesLists[position].getResults_list().get(12).getResult_value() + "");
                } else {
                    holder.away_value.setText(matchesLists[position].getResults_list().get(2).getResult_value() + "");
                    holder.home_value.setText(matchesLists[position].getResults_list().get(12).getResult_value() + "");
                }*/
            }

        }
        holder.parent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMatchDetailsApiAsyncTask getMatchDetailsApiAsyncTask = new GetMatchDetailsApiAsyncTask(mContext, new MatchDetailsCallback() {
                    @Override
                    public void processFinish(String matchDetails) {
                        Intent intent = new Intent(mContext, ClickMatchActivity.class);
                        intent.putExtra("matchDetails", matchDetails);
                        mContext.startActivity(intent);
                    }
                });
                getMatchDetailsApiAsyncTask.execute(matchesLists.get(position).getId() + "");
            }
        });

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return matchesLists.size();
    }
}
