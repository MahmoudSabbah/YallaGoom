package com.oxygen.yallagoom.fragment.myEventTapFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.ShowAllMyEventActivity;
import com.oxygen.yallagoom.activity.ShowUpcomingEventActivity;
import com.oxygen.yallagoom.adapter.event.myEvent.RecycleViewMainMyEvent;
import com.oxygen.yallagoom.adapter.event.myEvent.RecycleViewMyEventCheckGoing;
import com.oxygen.yallagoom.adapter.event.myEvent.RecycleViewMyEventComingEvent;
import com.oxygen.yallagoom.api.event.MyEventsHomeAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.Event;
import com.oxygen.yallagoom.interfaces.MyEventHomeCallback;


public class MyEventFragment extends Fragment {

    private static final int SHOW_ALL_MY_EVENT_REQUEST = 404;
    private TextView header_title;
    private RecyclerView my_event_list;
    private RecyclerView check_going_list;
    private RecycleViewMyEventCheckGoing recycleViewMyEventCheckGoing;
    private RecyclerView coming_event_list;
    private TextView show_me;
    private TextView show_all;
    private RecycleViewMainMyEvent recycleViewMainMyEvent;
    private RecycleViewMyEventComingEvent recycleViewMyEventComingEvent;
    private Event myEventData;
    private Event upcomingEventData;
    private ScrollView content_lay;
    private View no_access_found;


    public MyEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_event, container, false);

        content_lay = (ScrollView) view.findViewById(R.id.content_lay);
        no_access_found = (View) view.findViewById(R.id.no_access_found);

        show_all = (TextView) view.findViewById(R.id.show_all);
        show_me = (TextView) view.findViewById(R.id.show_me);
        my_event_list = (RecyclerView) view.findViewById(R.id.my_event_list);
        check_going_list = (RecyclerView) view.findViewById(R.id.check_going_list);
        coming_event_list = (RecyclerView) view.findViewById(R.id.coming_event_list);
        recycleViewMyEventCheckGoing = new RecycleViewMyEventCheckGoing();
        check_going_list.setAdapter(recycleViewMyEventCheckGoing);


        show_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myEventData != null) {
                    Intent intent = new Intent(MyEventFragment.this.getActivity(), ShowAllMyEventActivity.class);
                    intent.putExtra("myEventData", myEventData);
                    startActivityForResult(intent, SHOW_ALL_MY_EVENT_REQUEST);
                }
            }
        });
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upcomingEventData != null) {
                    Intent intent = new Intent(MyEventFragment.this.getActivity(), ShowUpcomingEventActivity.class);
                    intent.putExtra("upcomingEventData", upcomingEventData);
                    startActivity(intent);

                }
            }
        });
        if (MainApplication.verification_check) {
            getData();
        } else {
            no_access_found.setVisibility(View.VISIBLE);
            content_lay.setVisibility(View.GONE);
        }
        return view;

    }

    private void getData() {
        MyEventsHomeAsyncTask myEventsHomeAsyncTask = new MyEventsHomeAsyncTask(MyEventFragment.this.getActivity(), new MyEventHomeCallback() {
            @Override
            public void processFinish(Event myEvent, Event upcomingEvent, KProgressHUD kProgressHUD) {
                myEventData = myEvent;
                upcomingEventData = upcomingEvent;
                recycleViewMainMyEvent = new RecycleViewMainMyEvent(myEventData);
                my_event_list.setAdapter(recycleViewMainMyEvent);
                recycleViewMyEventComingEvent = new RecycleViewMyEventComingEvent(upcomingEvent);
                coming_event_list.setAdapter(recycleViewMyEventComingEvent);
                kProgressHUD.dismiss();
            }
        });
        myEventsHomeAsyncTask.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SHOW_ALL_MY_EVENT_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.hasExtra("deletePosition")) {
                        myEventData.getData().remove(data.getExtras().getInt("deletePosition"));
                        recycleViewMainMyEvent.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
