package com.yallagoom.fragment.MyEventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.activity.ShowAllMyEventActivity;
import com.yallagoom.activity.ShowMoreFriendsEventActivity;
import com.yallagoom.adapter.RecycleFriendsEventByType;
import com.yallagoom.api.GetEventFriendAsyncTask;
import com.yallagoom.entity.FriendsEvents;
import com.yallagoom.interfaces.FriendsEventCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class FriendEventFragment extends Fragment {


    private LinearLayout list_month;
    private LinearLayout today_layout;
    private List<FriendsEvents.Data> friendsEventsToday;
    private SelectableRoundedImageView image_event;
    private ImageLoader imageLoader;
    private TextView event_time;
    private TextView event_name;
    private TextView creater_name;
    private FriendsEvents friendsEventsAllData;
    private String checkType = "this";
    private String LastDay;
    private List<String> months;

    public FriendEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_event, container, false);
        list_month = (LinearLayout) view.findViewById(R.id.list_month);
        today_layout = (LinearLayout) view.findViewById(R.id.today_layout);
        today_layout.setVisibility(View.GONE);
        event_time = (TextView) view.findViewById(R.id.event_time);
        event_name = (TextView) view.findViewById(R.id.event_name);
        creater_name = (TextView) view.findViewById(R.id.creater_name);
        image_event = (SelectableRoundedImageView) view.findViewById(R.id.image_event);
        friendsEventsToday = new ArrayList<FriendsEvents.Data>();
        imageLoader = ImageLoader.getInstance();
        getData();
        ToolUtils.getFirstDayOfCurrentMonth(ToolUtils.converStringToDate("22-05-2017", "dd-MM-yyyy"));
        return view;

    }

    private void getData() {
        months = new ArrayList<>();
        GetEventFriendAsyncTask getEventFriendAsyncTask = new GetEventFriendAsyncTask(FriendEventFragment.this.getActivity(), new FriendsEventCallback() {
            @Override
            public void processFinish(FriendsEvents friendsEvents) {
                friendsEventsAllData = friendsEvents;
                if (friendsEventsAllData.getData().size() > 0) {
                    LastDay = friendsEventsAllData.getData().get(friendsEventsAllData.getData().size() - 1).getStartEventDate();
                    Log.e("LastDay", "" + LastDay);
                }
                for (int i = 0; i < friendsEvents.getData().size(); i++) {
                    String monthsValue = ToolUtils.getMonth(ToolUtils.converStringToDate(
                            friendsEvents.getData().get(i).getStartEventDate(), "yyyy-MM-dd"));
                    if (!months.contains(monthsValue) && !monthsValue.equalsIgnoreCase(ToolUtils.getMonth(new Date()))) {
                        months.add(monthsValue);
                    }
                    if (ToolUtils.getDay(ToolUtils.converStringToDate(
                            friendsEvents.getData().get(i).getStartEventDate(), "yyyy-MM-dd"// HH:mm:ss
                    )).equalsIgnoreCase(ToolUtils.getDay(new Date()))) {//"27-02-2018"
                        friendsEventsToday.add(friendsEvents.getData().get(i));
                    }

                }
                if (friendsEventsToday.size() > 0) {
                    event_time.setText(ToolUtils.convert24TimeTo12(friendsEvents.getData().get(0).getStartEventTime()));
                    event_name.setText(friendsEvents.getData().get(0).getEventTitle());
                    creater_name.setText(friendsEvents.getData().get(0).getCreator_data().getFirst_name() + " " + friendsEvents.getData().get(0).getCreator_data().getLast_name());
                    if (friendsEvents.getData().get(0).getEventImage() != null) {
                        ToolUtils.setImage(Constant.imageUrl + friendsEvents.getData().get(0).getEventImage(), image_event, imageLoader);
                    }

                    today_layout.setVisibility(View.VISIBLE);


                } else {
                    today_layout.setVisibility(View.GONE);
                }
                getDataByMonth();
            }
        });
        getEventFriendAsyncTask.execute();
    }

    private void getDataByMonth() {
        if (friendsEventsAllData != null) {
            if (checkType.equalsIgnoreCase("this")) {
                ArrayList<FriendsEvents.Data> friendsEventsWeek = new ArrayList<>();
                ArrayList<FriendsEvents.Data> friendsEventsThisMonth = new ArrayList<>();
                for (int i = 0; i < friendsEventsAllData.getData().size(); i++) {
                    if (ToolUtils.converStringToDate(
                            friendsEventsAllData.getData().get(i).getStartEventDate(), "yyyy-MM-dd"
                    ).after(ToolUtils.getStartWeek()) &&
                            ToolUtils.converStringToDate(
                                    friendsEventsAllData.getData().get(i).getStartEventDate(), "yyyy-MM-dd"
                            ).before(ToolUtils.getEndWeek())) {
                        friendsEventsWeek.add(friendsEventsAllData.getData().get(i));
                    }
                    if (ToolUtils.converStringToDate(
                            friendsEventsAllData.getData().get(i).getStartEventDate(), "yyyy-MM-dd"
                    ).after(ToolUtils.getFirstDayOfCurrentMonth(new Date())) &&
                            ToolUtils.converStringToDate(
                                    friendsEventsAllData.getData().get(i).getStartEventDate(), "yyyy-MM-dd"
                            ).before(ToolUtils.getLastDayOfCurrentMonth(new Date()))) {
                        friendsEventsThisMonth.add(friendsEventsAllData.getData().get(i));
                    }

                }

                if (friendsEventsWeek.size() > 0) {
                    addLayout(friendsEventsWeek, getString(R.string.this_week));

                }
                if (friendsEventsThisMonth.size() > 0) {
                    addLayout(friendsEventsThisMonth, getString(R.string.this_month));
                }

            } else {


            }
        }
        SpecificMonth();
    }

    private void SpecificMonth() {
   /*     HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(months);
        months.clear();
        months.addAll(hashSet);*/
        Log.e("getMonth", "" + months);
        for (int i = 0; i < months.size(); i++) {
            String[] split = months.get(i).split("-");
            ArrayList<FriendsEvents.Data> monthEvent = new ArrayList<>();
            for (int j = 0; j < friendsEventsAllData.getData().size(); j++) {
                String monthsValue = ToolUtils.getMonth(ToolUtils.converStringToDate(
                        friendsEventsAllData.getData().get(j).getStartEventDate(), "yyyy-MM-dd"));
                if (monthsValue.equalsIgnoreCase(months.get(i))) {
                    monthEvent.add(friendsEventsAllData.getData().get(j));
                }
            }
            if (monthEvent.size() > 0) {
                addLayout(monthEvent, split[0].toUpperCase() + " " + split[2]);
            }

        }

    }

    private void addLayout(final ArrayList<FriendsEvents.Data> friendsEventsThisMonth, final String nameType) {
        LayoutInflater inflater = LayoutInflater.from(FriendEventFragment.this.getActivity());
        View inflatedLayout = inflater.inflate(R.layout.friends_event_months, null, false);
        TextView name_week_month = (TextView) inflatedLayout.findViewById(R.id.name_week_month);
        TextView show_more = (TextView) inflatedLayout.findViewById(R.id.show_more);
        RecyclerView this_week_list = (RecyclerView) inflatedLayout.findViewById(R.id.this_week_list);
        RecycleFriendsEventByType recycleFriendsEventByType = new RecycleFriendsEventByType(friendsEventsThisMonth, imageLoader);
        this_week_list.setAdapter(recycleFriendsEventByType);
        name_week_month.setText(nameType);
        show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendEventFragment.this.getActivity(), ShowMoreFriendsEventActivity.class);
                intent.putExtra("FriendEventData", friendsEventsThisMonth);
                intent.putExtra("title", nameType);
                startActivity(intent);
            }
        });
        list_month.addView(inflatedLayout);
    }


}
