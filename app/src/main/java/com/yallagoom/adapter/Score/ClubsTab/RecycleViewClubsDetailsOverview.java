package com.yallagoom.adapter.Score.ClubsTab;


import android.app.Activity;
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

public class RecycleViewClubsDetailsOverview extends RecyclerView.Adapter<RecycleViewClubsDetailsOverview.MyViewHolder>  {


    private final Activity mContext;
    private final ArrayList<MatchesList> all_matches_in_last_season;
    public Context context;

    public RecycleViewClubsDetailsOverview(Activity activity, ArrayList<MatchesList> all_matches_in_last_season) {
        this.mContext = activity;
      this.all_matches_in_last_season = all_matches_in_last_season;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_clubs_details_overview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position==0){
            holder.label_match.setText(mContext.getString(R.string.last_match_result));
        }else {
            holder.label_match.setText(mContext.getString(R.string.next_match));
        }
       holder.matche_time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(all_matches_in_last_season.get(position).getStart_date(), Constant.yyyy_MM_dd), Constant.EEEE_dd_MMM_yyyy));
        if (all_matches_in_last_season.get(position).getParticipants_list().size() == 2) {
            holder.home.setText(all_matches_in_last_season.get(position).getParticipants_list().get(0).getParticipants_data().getShort_name());
            holder.away.setText(all_matches_in_last_season.get(position).getParticipants_list().get(1).getParticipants_data().getShort_name());
            if (all_matches_in_last_season.get(position).getStatus_type().equalsIgnoreCase("scheduled")) {
                holder.score_layout.setVisibility(View.GONE);
                holder.time_layout.setVisibility(View.VISIBLE);
                holder.time_value.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(all_matches_in_last_season.get(position).getStart_date(), Constant.yyyy_MM_dd__HH_mm), Constant.hh_mm_aa));
            } else {
                holder.score_layout.setVisibility(View.VISIBLE);
                holder.time_layout.setVisibility(View.GONE);
                for (int j = 0; j < all_matches_in_last_season.get(position).getResults_list().size(); j++) {
                    //Log.e(""+ matchesLists.get(position).getResults_list().get(j).getParticipants_id(),""+matchesLists.get(position).getParticipants_list().get(0).getParticipant_id());
                    if (all_matches_in_last_season.get(position).getParticipants_list().get(0).getParticipant_id() == all_matches_in_last_season.get(position).getResults_list().get(j).getParticipants_id()
                            && all_matches_in_last_season.get(position).getResults_list().get(j).getResult_title().getName().equalsIgnoreCase("Result")) {
                        holder.home_value.setText(all_matches_in_last_season.get(position).getResults_list().get(j).getResult_value() + "");
                    } else if (all_matches_in_last_season.get(position).getParticipants_list().get(1).getParticipant_id() == all_matches_in_last_season.get(position).getResults_list().get(j).getParticipants_id()
                            && all_matches_in_last_season.get(position).getResults_list().get(j).getResult_title().getName().equalsIgnoreCase("Result")) {
                        holder.away_value.setText(all_matches_in_last_season.get(position).getResults_list().get(j).getResult_value() + "");
                    }
                }

            }

        }
    }

    @Override
    public int getItemCount() {
        return all_matches_in_last_season.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView matche_time;
        private final TextView home;
        private final TextView away;
        private final TextView away_value;
        private final TextView home_value;
        private final TextView time_value;
        private final TextView label_match;
        private final LinearLayout score_layout;
        private final RelativeLayout time_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            matche_time = (TextView) view.findViewById(R.id.matche_time);
            home = (TextView) view.findViewById(R.id.home);
            away = (TextView) view.findViewById(R.id.away);
            away_value = (TextView) view.findViewById(R.id.away_value);
            home_value = (TextView) view.findViewById(R.id.home_value);
            time_value = (TextView) view.findViewById(R.id.time_value);
            label_match = (TextView) view.findViewById(R.id.label_match);
            score_layout = (LinearLayout) view.findViewById(R.id.score_layout);
            time_layout = (RelativeLayout) view.findViewById(R.id.time_layout);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}