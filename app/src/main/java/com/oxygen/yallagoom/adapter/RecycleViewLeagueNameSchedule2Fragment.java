package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Matches.LeagueMatches;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewLeagueNameSchedule2Fragment extends RecyclerView.Adapter<RecycleViewLeagueNameSchedule2Fragment.MyViewHolder> {

    private final ArrayList<LeagueMatches> leagueMatchesListData;
    public Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;
    public  int lastPos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView second_sports_list;
        private final TextView competition_name;
        private final ImageView country_image;
        private final TextView date_day;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            country_image = (ImageView) view.findViewById(R.id.country_image);
            competition_name = (TextView) view.findViewById(R.id.competition_name);
            date_day = (TextView) view.findViewById(R.id.date_day);
            second_sports_list = (RecyclerView) view.findViewById(R.id.second_sports_list);
             second_sports_list.setHasFixedSize(true);
            second_sports_list.setItemViewCacheSize(20);
            second_sports_list.setDrawingCacheEnabled(true);
            second_sports_list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        }
    }


    public RecycleViewLeagueNameSchedule2Fragment(ArrayList<LeagueMatches> leagueMatchesListData) {
      this.leagueMatchesListData = leagueMatchesListData;
        recycledViewPool = new RecyclerView.RecycledViewPool();

    }

    @Override
    public RecycleViewLeagueNameSchedule2Fragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule2_league_main_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        viewHolder.second_sports_list.setRecycledViewPool(recycledViewPool);
        return new RecycleViewLeagueNameSchedule2Fragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewLeagueNameSchedule2Fragment.MyViewHolder holder, final int position) {
        lastPos=position;
        RecycleViewDetailsLeagueSchedule2Fragment recycleViewDetailsLeagueSchedule2Fragment=new RecycleViewDetailsLeagueSchedule2Fragment(leagueMatchesListData.get(position).getMatchesLists2());
        recycleViewDetailsLeagueSchedule2Fragment.setHasStableIds(true);
        holder.second_sports_list.setAdapter(recycleViewDetailsLeagueSchedule2Fragment);
        holder.competition_name.setText(leagueMatchesListData.get(position).getLeagueName());
        Log.e("","");
        if (position>0){
            if (!ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesListData.get(position-1).getMatchesLists2()[0].getStart_date(), Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy)
                    .equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesListData.get(position).getMatchesLists2()[0].getStart_date(),  Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy))) {
                //  holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
            }else {
                //   holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
                holder.date_day.setVisibility(View.GONE);
            }
        }else {

        }
/*
        Log.e("leagueMatchesList1",""+ leagueMatchesList.get(position).getMatchesLists2()[0].getScheduled());
        Log.e("leagueMatchesList2",""+ ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists2()[0].getScheduled(), "yyyy-MM-dd"));

*/

        holder.date_day.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesListData.get(position).getMatchesLists2()[0].getStart_date(),  Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy));

     //   holder.competition_name.setText(result.get(position).getName());
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return leagueMatchesListData.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}

/*
  for (int i=0;i<3;i++){
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.list_item_schedule_sport_details_list, null, false);
        holder.second_sports_list.addView(inflatedLayout);
        }*/
