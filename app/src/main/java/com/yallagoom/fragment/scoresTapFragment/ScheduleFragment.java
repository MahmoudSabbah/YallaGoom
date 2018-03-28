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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yallagoom.R;
import com.yallagoom.action.RecyclerItemClickListener;
import com.yallagoom.adapter.RecycleViewMainListSportScheduleFragment;
import com.yallagoom.adapter.RecycleViewDaysScheduleFragment;
import com.yallagoom.api.GetMatchesApiAsyncTask;
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


public class ScheduleFragment extends Fragment {


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
    private ArrayList<FinalResultData_Data> leagueMatchesListData;
    private RecycleViewMainListSportScheduleFragment recycleViewLeagueNamesScheduleFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private int checkSelect;
    private RelativeLayout no_data_layout;
    private ImageView no_data_image;


    public ScheduleFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        daysList = new ArrayList<>();
        no_data_layout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        no_data_image = (ImageView) view.findViewById(R.id.image);
        no_data_image.setImageResource(R.drawable.score_de);
        Matches_list = (RecyclerView) view.findViewById(R.id.sports_list);
      //  Matches_list.setNestedScrollingEnabled(false);
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
        daysList = ToolUtils.getAllDays();

        resultDay = ToolUtils.convertDateFromFormatToFormat(daysList.get(0), Constant.MMM_dd_EEE_yyyy, Constant.yyyy_MM_dd);
        ScheduleDay = ToolUtils.convertDateFromFormatToFormat(daysList.get(daysList.size() - 1), Constant.MMM_dd_EEE_yyyy, Constant.yyyy_MM_dd);
      /*  daysList.add(ToolUtils.convertDateFromFormatToFormat(ToolUtils.PreviousDate(ScheduleDay), Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
        daysList.add(ToolUtils.convertDateFromFormatToFormat(ScheduleDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
        daysList.add(ToolUtils.convertDateFromFormatToFormat(ToolUtils.NextDate(ScheduleDay), Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
      */
        // currentDate = ToolUtils.converDateToString(new Date(), "MMM-dd-EEE");

        recycleViewDayScheduleFragment = new RecycleViewDaysScheduleFragment(days_list, daysList, "" + currentDate);
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
                        // ((LinearLayoutManager) mLayoutManager2).startSmoothScroll(smoothScroller);
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
                Log.e("RecyclerView", "finish scrolling");
                //    checkSelect = 0;

            }
        }));
        Matches_list.addOnScrollListener(new CustomScrollListener(new OnScrollCallBack() {
            @Override
            public void scrollState(int state) {
                switch (state) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //  Log.e("RecyclerView","The RecyclerView is not scrolling");

                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.e("RecyclerView", "Scrolling now");
                        View pastVisiblesItems = ((LinearLayoutManager) (mLayoutManager2)).findViewByPosition(((LinearLayoutManager) (mLayoutManager2)).findLastVisibleItemPosition());
                        if (pastVisiblesItems != null) {
                            TextView time = (TextView) pastVisiblesItems.findViewById(R.id.time);
                            Log.e("checkSelect", "" + time.getText().toString());
                            for (int i = 0; i < daysList.size(); i++) {
                                if (daysList.get(i).equalsIgnoreCase(
                                        ToolUtils.convertDateFromFormatToFormat(time.getText().toString(),
                                                Constant.EEEE_dd_MMM_yyyy, Constant.MMM_dd_EEE_yyyy))) {

                                    if (lastView != null) {
                                        LinearLayout check0 = lastView.findViewById(R.id.layout_select);
                                        check0.setSelected(false);
                                    }
                                    if (days_list.getChildAt(i) != null) {
                                        LinearLayout check1 = days_list.getChildAt(i).findViewById(R.id.layout_select);
                                        check1.setSelected(true);
                                        lastView = days_list.getChildAt(i);

                                    }

                                    days_list.smoothScrollToPosition(i);
                                    break;
                                }
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.e("RecyclerView", "Scroll Settling");
                        break;

                }

            }
        }));


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


        GetMatchesApiAsyncTask getMatchesApiAsyncTask = new GetMatchesApiAsyncTask(ScheduleFragment.this.getActivity(), type, new MatchesApiCallback() {

            @Override
            public void processFinish(ArrayList<FinalResultData_Data> leagueMatchesList) {
                if (type.equalsIgnoreCase("Default")) {
                    if (leagueMatchesList != null) {
                        leagueMatchesListData = leagueMatchesList;
                        recycleViewLeagueNamesScheduleFragment = new RecycleViewMainListSportScheduleFragment(leagueMatchesListData, getActivity());
                        Matches_list.setAdapter(recycleViewLeagueNamesScheduleFragment);
                        setCurrentDate();
                    }
                    ScheduleDay = ToolUtils.NextDate(ScheduleDay);
                    resultDay = ToolUtils.PreviousDate(resultDay);

                } else if (type.equalsIgnoreCase("NewMatches")) {
                    if (leagueMatchesList != null) {
                        leagueMatchesListData.addAll(leagueMatchesList);
                        recycleViewLeagueNamesScheduleFragment.notifyDataSetChanged();
                    }
                    daysList.add(ToolUtils.convertDateFromFormatToFormat(ScheduleDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
                    ScheduleDay = ToolUtils.NextDate(ScheduleDay);
                    recycleViewDayScheduleFragment.notifyDataSetChanged();

                } else {
                    if (leagueMatchesList != null) {
                        //    ArrayList<FinalResultData_Data> leagueMatchesListData2 = new ArrayList<>();
                        //    leagueMatchesListData2.addAll(leagueMatchesListData);
                        //     leagueMatchesListData.clear();
                        leagueMatchesListData.addAll(0, leagueMatchesList);
                        recycleViewLeagueNamesScheduleFragment.notifyDataSetChanged();
                    }
                    //  leagueMatchesListData.addAll(leagueMatchesListData2);
                    daysList.add(0, ToolUtils.convertDateFromFormatToFormat(resultDay, Constant.yyyy_MM_dd, Constant.MMM_dd_EEE_yyyy));
                    resultDay = ToolUtils.PreviousDate(resultDay);
                    recycleViewDayScheduleFragment.notifyDataSetChanged();
                //   setPreDate();

                }
                swipyrefreshlayout.setRefreshing(false);
                if (leagueMatchesListData.size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);

                }
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
                checkCurrentDate = true;
                // ((LinearLayoutManager) mLayoutManager2).startSmoothScroll(smoothScroller);
                ((LinearLayoutManager) mLayoutManager2).scrollToPositionWithOffset(i, 20);
                break;
            }
        }
        for (int i = 0; i < daysList.size(); i++) {

            if (checkCurrentDate) {
                if (daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(new Date(), Constant.MMM_dd_EEE_yyyy))) {
                    LinearLayout check1 = days_list.getLayoutManager().getChildAt(i).findViewById(R.id.layout_select);
                    check1.setSelected(true);
                    if (lastView != null) {
                        LinearLayout checkPre = lastView.findViewById(R.id.layout_select);
                        checkPre.setSelected(false);
                    }
                    lastView = days_list.getChildAt(i);
                    days_list.smoothScrollToPosition(i);
                    break;
                }
            } else if (leagueMatchesListData.size() != 0 && daysList.get(i).equalsIgnoreCase(ToolUtils.converDateToString(ToolUtils.converStringToDate(
                    leagueMatchesListData.get(0).getStart_date(), Constant.yyyy_MM_dd
            ), Constant.MMM_dd_EEE_yyyy))) {
                //  if (days_list.getChildAt(i) != null) {
                LinearLayout check1 = days_list.getLayoutManager().getChildAt(i).findViewById(R.id.layout_select);
                check1.setSelected(true);
                //  }

                if (lastView != null) {
                    LinearLayout checkPre = lastView.findViewById(R.id.layout_select);
                    checkPre.setSelected(false);
                }
                lastView = days_list.getChildAt(i);
                days_list.smoothScrollToPosition(i);
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
                Log.e("daysList", "" + daysList.size());

                LinearLayout check1 = days_list.getChildAt(i).findViewById(R.id.layout_select);
                check1.setSelected(true);
                //  }
                if (lastView != null) {
                    LinearLayout checkPre = lastView.findViewById(R.id.layout_select);
                    checkPre.setSelected(false);
                }
                lastView = days_list.getChildAt(i);
                days_list.smoothScrollToPosition(i);
                break;
            }
        }
    }
}
