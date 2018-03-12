package com.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yallagoom.R;
import com.yallagoom.action.RecyclerItemClickListener;
import com.yallagoom.adapter.RecycleViewLeagueNameSchedule2Fragment;
import com.yallagoom.adapter.RecycleViewLeagueNamesScheduleFragment;
import com.yallagoom.adapter.RecycleViewDaysScheduleFragment;
import com.yallagoom.api.GetMatchesApiAsyncTask;
import com.yallagoom.entity.Matches.LeagueMatches;
import com.yallagoom.entity.Matches.LeagueMatchesList;
import com.yallagoom.interfaces.MatchesApiCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CenterLayoutManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Schedule2Fragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private RecyclerView Matches_list;
    private RecyclerView days_list;
    //public static int lastPos = -1;
    private View lastView = null;
    private RecycleViewLeagueNamesScheduleFragment recycleViewCountryMatchesScheduleFragment;
    private RecycleViewDaysScheduleFragment recycleViewDayScheduleFragment;
    private List<String> daysList;
    private String currentDate;
    private SwipyRefreshLayout swipyrefreshlayout;
    private String resultDay;
    private String ScheduleDay;
    private ArrayList<LeagueMatches> leagueMatchesListData;
    private RecycleViewLeagueNameSchedule2Fragment recycleViewLeagueNamesScheduleFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private int checkSelect;

    public Schedule2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule2, container, false);
        daysList = new ArrayList<>();
        resultDay = ToolUtils.converDateToString(new Date(), Constant.yyyy_MM_dd);
        ScheduleDay = ToolUtils.converDateToString(new Date(), Constant.yyyy_MM_dd);

        Matches_list = (RecyclerView) view.findViewById(R.id.league_list);
        mLayoutManager2 = new CenterLayoutManager(getActivity());
        Matches_list.setLayoutManager(mLayoutManager2);
        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        swipyrefreshlayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        days_list = (RecyclerView) view.findViewById(R.id.days_list);
        mLayoutManager = new CenterLayoutManager(getActivity());
        days_list.setLayoutManager(mLayoutManager);
        // daysList = ToolUtils.getAllDays();
        daysList.add(ToolUtils.convertDateFromFormatToFormat(ToolUtils.PreviousDate(ScheduleDay), Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
        daysList.add(ToolUtils.convertDateFromFormatToFormat(ScheduleDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
        daysList.add(ToolUtils.convertDateFromFormatToFormat(ToolUtils.NextDate(ScheduleDay), Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
        currentDate = ToolUtils.converDateToString(new Date(), "MMM-dd-EEE");

        recycleViewDayScheduleFragment = new RecycleViewDaysScheduleFragment(days_list, daysList, "" + currentDate);
        recycleViewDayScheduleFragment.setHasStableIds(true);
        days_list.setAdapter(recycleViewDayScheduleFragment);

        days_list.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkSelect = 1;
                TextView full_time = view.findViewById(R.id.full_time);
                for (int i = 0; i < leagueMatchesListData.size(); i++) {
                    if (ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(i).getMatchesLists2()[0].getScheduled(),
                            Constant.yyyy_MM_dd, Constant.dd_MMM_yyyy).equalsIgnoreCase(
                            ToolUtils.convertDateFromFormatToFormat(full_time.getText().toString(),
                                    Constant.MMM_dd_EEE_yyyy, Constant.dd_MMM_yyyy))) {
                        smoothScroller.setTargetPosition(i);
                        ((LinearLayoutManager) mLayoutManager2).scrollToPositionWithOffset(i, 20);
                        break;
                    }
                }

                LinearLayout check1 = view.findViewById(R.id.layout_select);
                days_list.smoothScrollToPosition(position);
                check1.setSelected(true);

                if (lastView != null && lastView != view) {
                    LinearLayout check0 = lastView.findViewById(R.id.layout_select);
                    check0.setSelected(false);
                }

                lastView = view;
                //    checkSelect = 0;

            }
        }));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Matches_list.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (checkSelect == 0) {

                        View pastVisiblesItems = ((LinearLayoutManager) (mLayoutManager2)).findViewByPosition(recycleViewLeagueNamesScheduleFragment.lastPos);
                        if (pastVisiblesItems != null) {
                            TextView time = (TextView) pastVisiblesItems.findViewById(R.id.time);
                            for (int i = 0; i < daysList.size(); i++) {

                                if (daysList.get(i).equalsIgnoreCase(
                                        ToolUtils.convertDateFromFormatToFormat(time.getText().toString(),
                                                Constant.EEEE_dd_MMM_yyyy, Constant.MMM_dd_EEE_yyyy))) {
                                    Log.e("checkSelect","checkSelect");

                                    if (lastView != null ) {
                                        LinearLayout check0 = lastView.findViewById(R.id.layout_select);
                                        check0.setSelected(false);
                                    }
                                    LinearLayout check1 = days_list.getChildAt(i).findViewById(R.id.layout_select);
                                    check1.setSelected(true);
                                    lastView = days_list.getChildAt(i);

                                    days_list.smoothScrollToPosition(i);
                                    break;
                                }
                            }
                        }
                        checkSelect=1;
                    }                }
            });
        } else {
            Matches_list.setOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (checkSelect == 0) {

                        View pastVisiblesItems = ((LinearLayoutManager) (mLayoutManager2)).findViewByPosition(recycleViewLeagueNamesScheduleFragment.lastPos);
                        if (pastVisiblesItems != null) {
                            TextView date_day = (TextView) pastVisiblesItems.findViewById(R.id.date_day);
                            for (int i = 0; i < daysList.size(); i++) {

                                if (daysList.get(i).equalsIgnoreCase(
                                        ToolUtils.convertDateFromFormatToFormat(date_day.getText().toString(),
                                                Constant.EEEE_dd_MMM_yyyy, Constant.MMM_dd_EEE_yyyy))) {
                                    Log.e("checkSelect","checkSelect");

                                    if (lastView != null ) {
                                        LinearLayout check0 = lastView.findViewById(R.id.layout_select);
                                        check0.setSelected(false);
                                    }
                                    LinearLayout check1 = days_list.getChildAt(i).findViewById(R.id.layout_select);
                                    check1.setSelected(true);
                                    lastView = days_list.getChildAt(i);

                                    days_list.smoothScrollToPosition(i);
                                    break;
                                }
                            }
                        }
                        checkSelect=1;
                    }
                }
            });
        }

        swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    getData("LastMatches");
                } else {
                    getData("NewMatches");
                }

            }
        });
        getData("Default");
        return view;

    }
    private void getData(final String type) {

        GetMatchesApiAsyncTask getMatchesApiAsyncTask = new GetMatchesApiAsyncTask(Schedule2Fragment.this.getActivity(), type, new MatchesApiCallback() {

            @Override
            public void processFinish(LeagueMatchesList leagueMatchesList) {
                if (type.equalsIgnoreCase("Default")) {
                    leagueMatchesListData = leagueMatchesList.getLeagueMatches();
                    recycleViewLeagueNamesScheduleFragment = new RecycleViewLeagueNameSchedule2Fragment(leagueMatchesListData);
                    Matches_list.setAdapter(recycleViewLeagueNamesScheduleFragment);
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


}
