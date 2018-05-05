package com.oxygen.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewDaysScheduleFragment;
import com.oxygen.yallagoom.adapter.RecycleViewMainListSportScheduleFragment;
import com.oxygen.yallagoom.entity.Matches.LeagueMatches;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Schedule3Fragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private RecyclerView Matches_list;
    private RecyclerView days_list;
    //public static int lastPos = -1;
    private View lastView = null;
    private RecycleViewMainListSportScheduleFragment recycleViewCountryMatchesScheduleFragment;
    private RecycleViewDaysScheduleFragment recycleViewDayScheduleFragment;
    private List<String> daysList;
    private String currentDate;
    private SwipyRefreshLayout swipyrefreshlayout;
    private String resultDay;
    private String ScheduleDay;
    private ArrayList<LeagueMatches> leagueMatchesListData;
    private RecycleViewMainListSportScheduleFragment recycleViewLeagueNamesScheduleFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private int checkSelect;
    private LinearLayout list_data;
    private ScrollView scrollView;


    public Schedule3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule3, container, false);
        list_data=(LinearLayout)view.findViewById(R.id.list_data);
        resultDay = ToolUtils.converDateToString(new Date(), Constant.yyyy_MM_dd);
        ScheduleDay = ToolUtils.converDateToString(new Date(), Constant.yyyy_MM_dd);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        swipyrefreshlayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                scrollView.post(new Runnable() {
                    public void run() {
                       // scrollView.scrollTo(0,20);
                      ToolUtils.scrollToView(scrollView,list_data.getChildAt(4));
                        swipyrefreshlayout.setRefreshing(false);
                    }
                });

            }
        });
        //getData("Default");
        return view;

    }

  /*  private void getData(final String type) {

        GetMatchesApiAsyncTask getMatchesApiAsyncTask = new GetMatchesApiAsyncTask(Schedule3Fragment.this.getActivity(), type, new MatchesApiCallback() {

            @Override
            public void processFinish(ArrayList<FinalResultData_Data> leagueMatchesList) {
                if (type.equalsIgnoreCase("Default")) {
                    leagueMatchesListData = leagueMatchesList.getLeagueMatches();
                    Log.e("555555555",""+leagueMatchesListData.size());
                    for (int position=0;position<5;position++) {
                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        View inflatedLayout = inflater.inflate(R.layout.list_item_schedule_sport_main_list, null, false);
                        TextView competition_name = (TextView) inflatedLayout.findViewById(R.id.competition_name);
                        TextView time = (TextView) inflatedLayout.findViewById(R.id.time);
                        RecyclerView second_sports_list = (RecyclerView) inflatedLayout.findViewById(R.id.second_sports_list);
                        second_sports_list.setNestedScrollingEnabled(false);

                        second_sports_list.setHasFixedSize(true);

                        RecycleViewDetailsMatchesScheduleFragment recycleViewDetailsMatchesScheduleFragment = new RecycleViewDetailsMatchesScheduleFragment(leagueMatchesListData.get(position).getMatchesLists2(), getActivity());
                        second_sports_list.setAdapter(recycleViewDetailsMatchesScheduleFragment);
                        competition_name.setText(leagueMatchesListData.get(position).getLeagueName());
                        Log.e("", "");
                        if (position > 0) {
                            if (!ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesListData.get(position - 1).getMatchesLists2()[0].getStart_date(),  Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy)
                                    .equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesListData.get(position).getMatchesLists2()[0].getStart_date(),  Constant.yyyy_MM_dd__HH_mm), Constant.EEEE_dd_MMM_yyyy))) {
                                //  holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
                            } else {
                                //   holder.time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(position).getMatchesLists().get(0).getScheduled(), "yyyy-MM-dd'T'HH:mm:ssZ"), Constant.EEEE_dd_MMM_yyyy));
                                time.setVisibility(View.GONE);
                            }
                        } else {

                        }

                *//*    recycleViewLeagueNamesScheduleFragment = new RecycleViewMainListSportScheduleFragment(leagueMatchesListData);
                    Matches_list.setAdapter(recycleViewLeagueNamesScheduleFragment);*//*

                        // setCurrentDate();
                        list_data.addView(inflatedLayout);
                    }
                } else if (type.equalsIgnoreCase("NewMatches")) {
                    recycleViewDayScheduleFragment.notifyDataSetChanged();
                    leagueMatchesListData.addAll(leagueMatchesList.getLeagueMatches());
                    recycleViewLeagueNamesScheduleFragment.notifyDataSetChanged();
                } else {
                    recycleViewDayScheduleFragment.notifyDataSetChanged();
                    ArrayList<LeagueMatches> leagueMatchesListData2 = new ArrayList<>();
                    leagueMatchesListData2.addAll(leagueMatchesListData);
                    leagueMatchesListData.clear();
                    leagueMatchesListData.addAll(leagueMatchesList.getLeagueMatches());
                    leagueMatchesListData.addAll(leagueMatchesListData2);
                    recycleViewLeagueNamesScheduleFragment.notifyDataSetChanged();
                }
                swipyrefreshlayout.setRefreshing(false);
            }
        });
        if (type.equalsIgnoreCase("Default")) {
            ScheduleDay = ToolUtils.NextDate(ScheduleDay);
            resultDay = ToolUtils.PreviousDate(resultDay);
            getMatchesApiAsyncTask.execute(resultDay, ScheduleDay);

        } else if (type.equalsIgnoreCase("NewMatches")) {
            ScheduleDay = ToolUtils.NextDate(ScheduleDay);
            daysList.add(ToolUtils.convertDateFromFormatToFormat(ScheduleDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
            getMatchesApiAsyncTask.execute(ScheduleDay, ScheduleDay);

        } else {
            resultDay = ToolUtils.PreviousDate(resultDay);
            List<String> daysList2 = new ArrayList<>();
            daysList2.addAll(daysList);
            daysList.clear();
            daysList.add(ToolUtils.convertDateFromFormatToFormat(resultDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
            daysList.addAll(daysList2);
            getMatchesApiAsyncTask.execute(resultDay, resultDay);

        }
    }

    private void setCurrentDate() {
        for (int i = 0; i < leagueMatchesListData.size(); i++) {
            Log.e("ttt1", ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(i).getMatchesLists2()[0].getStart_date(),
                    Constant.yyyy_MM_dd__HH_mm, Constant.dd_MMM_yyyy));
            Log.e("ttt2", ToolUtils.converDateToString(new Date(), Constant.dd_MMM_yyyy));
            if (ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(i).getMatchesLists2()[0].getStart_date(),
                    Constant.yyyy_MM_dd__HH_mm, Constant.dd_MMM_yyyy).equalsIgnoreCase(ToolUtils.converDateToString(new Date(), Constant.dd_MMM_yyyy)
            )) {
                // ((LinearLayoutManager) mLayoutManager2).startSmoothScroll(smoothScroller);
                ((LinearLayoutManager) mLayoutManager2).scrollToPositionWithOffset(i, 20);
                break;
            }
        }
        for (int i = 0; i < daysList.size(); i++) {
            if (daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(new Date(), Constant.MMM_dd_EEE_yyyy))) {
                LinearLayout check1 = days_list.getChildAt(i).findViewById(R.id.layout_select);
                check1.setSelected(true);
                lastView = days_list.getChildAt(i);
                days_list.smoothScrollToPosition(i);
                break;
            }

        }
    }*/
}
