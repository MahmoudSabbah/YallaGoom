package com.yallagoom.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Matches.NewApi.FinalResultData_Data;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewMainListSportScheduleFragment extends RecyclerView.Adapter<RecycleViewMainListSportScheduleFragment.MyViewHolder> {

    private final ArrayList<FinalResultData_Data> leagueMatchesList;
    private final Activity mContext;
    public Context context;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView second_sports_list;
        private final TextView competition_name;
        private final ImageView country_image;
        private final TextView time;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            country_image = (ImageView) view.findViewById(R.id.country_image);
            competition_name = (TextView) view.findViewById(R.id.competition_name);
            time = (TextView) view.findViewById(R.id.time);
            second_sports_list = (RecyclerView) view.findViewById(R.id.second_sports_list);
            second_sports_list.setHasFixedSize(true);
            /*second_sports_list.setItemViewCacheSize(20);
            second_sports_list.setDrawingCacheEnabled(true);
            second_sports_list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/
        }
    }


    public RecycleViewMainListSportScheduleFragment(ArrayList<FinalResultData_Data> leagueMatchesList, Activity mContext) {
        this.leagueMatchesList = leagueMatchesList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        this.mContext=mContext;

    }

    @Override
    public RecycleViewMainListSportScheduleFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_sport_main_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        viewHolder.second_sports_list.setRecycledViewPool(recycledViewPool);
        return new RecycleViewMainListSportScheduleFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewMainListSportScheduleFragment.MyViewHolder holder, final int position) {
        RecycleViewDetailsLeagueScheduleFragment recycleViewDetailsLeagueScheduleFragment =new RecycleViewDetailsLeagueScheduleFragment(leagueMatchesList.get(position).getData(),mContext);
        holder.second_sports_list.setAdapter(recycleViewDetailsLeagueScheduleFragment);
       /* RecycleViewDetailsMatchesScheduleFragment recycleViewDetailsMatchesScheduleFragment = new RecycleViewDetailsMatchesScheduleFragment(leagueMatchesList.get(position).getMatchesLists2(),mContext);
        holder.second_sports_list.setAdapter(recycleViewDetailsMatchesScheduleFragment);
        holder.competition_name.setText(leagueMatchesList.get(position).getLeagueName());
        Log.e("","");
        if (position>0){
            if (!ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position-1).getMatchesLists2()[0].getStart_date(), Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy)
                    .equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists2()[0].getStart_date(), Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy))) {
              //  holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
            }else {
             //   holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
                holder.time.setVisibility(View.GONE);
            }
            }else {

        }*/
/*
        Log.e("leagueMatchesList1",""+ leagueMatchesList.get(position).getMatchesLists2()[0].getScheduled());
        Log.e("leagueMatchesList2",""+ ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists2()[0].getScheduled(), "yyyy-MM-dd"));

*/

       holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getStart_date(), Constant.yyyy_MM_dd), Constant.EEEE_dd_MMM_yyyy));

    }
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return leagueMatchesList.size();
    }
}
/*
  for (int i=0;i<3;i++){
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.list_item_schedule_sport_details_list_match, null, false);
        holder.second_sports_list.addView(inflatedLayout);
        }*/
