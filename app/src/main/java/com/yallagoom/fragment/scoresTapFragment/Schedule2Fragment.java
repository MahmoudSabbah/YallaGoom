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
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yallagoom.R;
import com.yallagoom.action.RecyclerItemClickListener;
import com.yallagoom.adapter.RecycleViewDetailsLeagueScheduleFragment;
import com.yallagoom.adapter.RecycleViewLeagueNameSchedule2Fragment;
import com.yallagoom.adapter.RecycleViewMainListSportScheduleFragment;
import com.yallagoom.adapter.RecycleViewDaysScheduleFragment;
import com.yallagoom.api.GetMatchesApiAsyncTask;
import com.yallagoom.entity.Matches.LeagueMatches;
import com.yallagoom.entity.Matches.NewApi.FinalResultData_Data;
import com.yallagoom.interfaces.MatchesApiCallback;
import com.yallagoom.interfaces.OnScrollCallBack;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CenterLayoutManager;
import com.yallagoom.widget.CustomScrollListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Schedule2Fragment extends Fragment {


    // private RecyclerView days_list;
    private View lastView = null;
    private RecycleViewDaysScheduleFragment recycleViewDayScheduleFragment;
    private List<String> daysList;
    private String currentDate;
    private SwipyRefreshLayout swipyrefreshlayout;
    private String resultDay;
    private String ScheduleDay;
    private RecyclerView.LayoutManager mLayoutManager;
    private int checkSelect;
    private LinearLayout list_matches;
    private ArrayList<FinalResultData_Data> leagueMatchesListData;
    private ScrollView scrollView;
    private ScrollView day_scroll;
    private LinearLayout days_list_linear;


    public Schedule2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_schedule2, container, false);
        daysList = new ArrayList<>();
        swipyrefreshlayout = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        day_scroll = (ScrollView) view.findViewById(R.id.day_scroll);

        list_matches = (LinearLayout) view.findViewById(R.id.list_matches);
        days_list_linear = (LinearLayout) view.findViewById(R.id.days_list_linear);
        //   days_list = (RecyclerView) view.findViewById(R.id.days_list);
        mLayoutManager = new CenterLayoutManager(getActivity());
        // days_list.setLayoutManager(mLayoutManager);
        // daysList = ToolUtils.getAllDays();
        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;

            }
        };

        daysList = ToolUtils.getAllDays();
        resultDay = ToolUtils.convertDateFromFormatToFormat(daysList.get(0), Constant.MMM_dd_EEE_yyyy, Constant.yyyy_MM_dd);
        ScheduleDay = ToolUtils.convertDateFromFormatToFormat(daysList.get(daysList.size() - 1), Constant.MMM_dd_EEE_yyyy, Constant.yyyy_MM_dd);

        currentDate = ToolUtils.converDateToString(new Date(), "MMM-dd-EEE");
        for (int i = 0; i < daysList.size(); i++) {
            LayoutInflater inflater_ = LayoutInflater.from(Schedule2Fragment.this.getActivity());
            final View view1 = inflater_.inflate(R.layout.list_item_schedule_days_list, null, false);
            LinearLayout parent = (LinearLayout) view1.findViewById(R.id.parent);
            TextView month_name = (TextView) view1.findViewById(R.id.month_name);
            final TextView full_time = (TextView) view1.findViewById(R.id.full_time);
            TextView day_number = (TextView) view1.findViewById(R.id.day_number);
            TextView day_name = (TextView) view1.findViewById(R.id.day_name);
            final LinearLayout check1 = view1.findViewById(R.id.layout_select);

            String[] format = daysList.get(i).split("-");
            month_name.setText(format[0]);
            day_number.setText(format[1]);
            day_name.setText(format[2]);
            full_time.setText(daysList.get(i));
            days_list_linear.addView(view1);
            final int finalI = i;
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < leagueMatchesListData.size(); j++) {
                        final int finalJ = j;
                        if (ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(finalJ).getStart_date(),
                                Constant.yyyy_MM_dd, Constant.dd_MMM_yyyy).equalsIgnoreCase(
                                ToolUtils.convertDateFromFormatToFormat(full_time.getText().toString(),
                                        Constant.MMM_dd_EEE_yyyy, Constant.dd_MMM_yyyy))) {
                            smoothScroller.setTargetPosition(j);
                            scrollView.post(new Runnable() {
                                public void run() {
                                    ToolUtils.scrollToView(scrollView, list_matches.getChildAt(finalJ));
                                }
                            });
                            break;
                        }
                    }

                    ToolUtils.scrollToView(day_scroll, days_list_linear.getChildAt(finalI));
                    check1.setSelected(true);

                    if (lastView != null && lastView != view) {
                        LinearLayout check0 = lastView.findViewById(R.id.layout_select);
                        check0.setSelected(false);
                    }

                    lastView = view1;
                }
            });

        }
     /*   recycleViewDayScheduleFragment = new RecycleViewDaysScheduleFragment(days_list, daysList, "" + currentDate);
        recycleViewDayScheduleFragment.setHasStableIds(true);
        days_list.setAdapter(recycleViewDayScheduleFragment);
        days_list.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkSelect = 1;
                TextView full_time = view.findViewById(R.id.full_time);
                for (int i = 0; i < leagueMatchesListData.size(); i++) {
                    if (ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(i).getStart_date(),
                            Constant.yyyy_MM_dd, Constant.dd_MMM_yyyy).equalsIgnoreCase(
                            ToolUtils.convertDateFromFormatToFormat(full_time.getText().toString(),
                                    Constant.MMM_dd_EEE_yyyy, Constant.dd_MMM_yyyy))) {
                        smoothScroller.setTargetPosition(i);
                        //   ((LinearLayoutManager) mLayoutManager2).scrollToPositionWithOffset(i, 20);
                        ToolUtils.scrollToView(scrollView, list_matches.getChildAt(i));

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
                Log.e("RecyclerView", "finish scrolling");
                //    checkSelect = 0;

            }
        }));*/
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY(); // For ScrollView
                int scrollX = scrollView.getScrollX(); // For HorizontalScrollView

                for (int i = 0; i < list_matches.getChildCount(); i++) {
                    View v = list_matches.getChildAt(i);
                   /* if(v.getHitRect().contains(scrollX, scrollY)){

                    }*/
                    // Do something with v.
                    // …
                }
             /*   list_matches.get
                for(View v : list_matches.getChildVisibleRect())
                {
                    // only checking ViewGroups (layout) obviously you can change
                    // this to suit your needs
                    if(!(v instanceof ViewGroup))
                        continue;

                    if(v.getHitRect().contains(x, y))
                        return v;
                }*/
                // DO SOMETHING WITH THE SCROLL COORDINATES
            }
        });
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
            public void processFinish(ArrayList<FinalResultData_Data> leagueMatchesList) {
                leagueMatchesListData = leagueMatchesList;
                for (int i = 0; i < leagueMatchesList.size(); i++) {
                    LayoutInflater inflater = LayoutInflater.from(Schedule2Fragment.this.getActivity());
                    View inflatedLayout = inflater.inflate(R.layout.list_item_schedule_sport_main_list, null, false);
                    RecyclerView second_sports_list = (RecyclerView) inflatedLayout.findViewById(R.id.second_sports_list);
                    second_sports_list.setNestedScrollingEnabled(false);
                    TextView time = (TextView) inflatedLayout.findViewById(R.id.time);

                    RecycleViewDetailsLeagueScheduleFragment recycleViewDetailsLeagueScheduleFragment = new RecycleViewDetailsLeagueScheduleFragment(leagueMatchesList.get(i).getData(), Schedule2Fragment.this.getActivity());
                    second_sports_list.setAdapter(recycleViewDetailsLeagueScheduleFragment);
                    time.setText(ToolUtils.converDateToString(ToolUtils.converStringToDate(leagueMatchesList.get(i).getStart_date(), Constant.yyyy_MM_dd), Constant.EEEE_dd_MMM_yyyy));
                    if (type.equalsIgnoreCase("Default")) {
                        list_matches.addView(inflatedLayout);
                    } else if (type.equalsIgnoreCase("NewMatches")) {
                        list_matches.addView(inflatedLayout);
                    } else {
                        list_matches.addView(inflatedLayout, 0);
                    }

                }
                if (type.equalsIgnoreCase("Default")) {
                    ScheduleDay = ToolUtils.NextDate(ScheduleDay);
                    resultDay = ToolUtils.PreviousDate(resultDay);


                    setCurrentDate();
                } else if (type.equalsIgnoreCase("NewMatches")) {
                    daysList.add(ToolUtils.convertDateFromFormatToFormat(ScheduleDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
                    ScheduleDay = ToolUtils.NextDate(ScheduleDay);
                    recycleViewDayScheduleFragment.notifyDataSetChanged();
                } else {
                    daysList.add(0, ToolUtils.convertDateFromFormatToFormat(resultDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
                    resultDay = ToolUtils.PreviousDate(resultDay);
                    recycleViewDayScheduleFragment.notifyDataSetChanged();
                }
                swipyrefreshlayout.setRefreshing(false);
            }
        });
        if (type.equalsIgnoreCase("Default")) {
            getMatchesApiAsyncTask.execute(resultDay, ScheduleDay);

        } else if (type.equalsIgnoreCase("NewMatches")) {
            getMatchesApiAsyncTask.execute(ScheduleDay, ScheduleDay);

        } else {
            getMatchesApiAsyncTask.execute(resultDay, resultDay);

        }
    }

    private void setCurrentDate() {

        boolean checkCurrentDate = false;
        for (int i = 0; i < leagueMatchesListData.size(); i++) {

            if (ToolUtils.convertDateFromFormatToFormat(leagueMatchesListData.get(i).getStart_date(),
                    Constant.yyyy_MM_dd, Constant.dd_MMM_yyyy).equalsIgnoreCase(ToolUtils.converDateToString(new Date(), Constant.dd_MMM_yyyy)
            )) {
                Log.e("test1", "test1");
                checkCurrentDate = true;
                // ((LinearLayoutManager) mLayoutManager2).startSmoothScroll(smoothScroller);
                ToolUtils.scrollToView(scrollView, list_matches.getChildAt(i));
                break;
            }
        }
        for (int i = 0; i < daysList.size(); i++) {
            if (checkCurrentDate) {
                Log.e("test2", "test2");
                if (daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(new Date(), Constant.MMM_dd_EEE_yyyy))) {
                    LinearLayout check1 = days_list_linear.getChildAt(i).findViewById(R.id.layout_select);
                    check1.setSelected(true);
                    if (lastView != null) {
                        LinearLayout checkPre = lastView.findViewById(R.id.layout_select);
                        checkPre.setSelected(false);
                    }
                    lastView = days_list_linear.getChildAt(i);
                    ToolUtils.scrollToView(day_scroll, days_list_linear.getChildAt(i));
                    break;
                }
            } else if (leagueMatchesListData.size() != 0 && daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(
                    leagueMatchesListData.get(leagueMatchesListData.size() - 1).getStart_date(), Constant.yyyy_MM_dd
            ), Constant.MMM_dd_EEE_yyyy))) {

                Log.e("test3", "test3");
                //  if (days_list.getChildAt(i) != null) {
                LinearLayout check1 = days_list_linear.getChildAt(i).findViewById(R.id.layout_select);
                check1.setSelected(true);
                //  }.
                ToolUtils.scrollToView(day_scroll, days_list_linear.getChildAt(i));
                scrollView.post(new Runnable() {
                    public void run() {
                        ToolUtils.scrollToView(scrollView, list_matches.getChildAt(leagueMatchesListData.size() - 1));
                    }
                });
                lastView = days_list_linear.getChildAt(i);
                break;
            }

        }
    }
    private void setPreDate() {

        for (int i = 0; i < daysList.size(); i++) {
            if (leagueMatchesListData.size() != 0 && daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(
                    leagueMatchesListData.get(0).getStart_date(), Constant.yyyy_MM_dd
            ), Constant.MMM_dd_EEE_yyyy))) {
                //   if (days_list.getChildAt(i) != null) {

                LinearLayout check1 = days_list_linear.getChildAt(i).findViewById(R.id.layout_select);
                check1.setSelected(true);
                //  }
                if (lastView != null) {
                    LinearLayout checkPre = lastView.findViewById(R.id.layout_select);
                    checkPre.setSelected(false);
                }
                lastView = days_list_linear.getChildAt(i);
                ToolUtils.scrollToView(day_scroll, days_list_linear.getChildAt(i));
                break;
            }
        }
    }
}
