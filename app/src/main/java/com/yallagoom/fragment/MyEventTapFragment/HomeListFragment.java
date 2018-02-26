package com.yallagoom.fragment.MyEventTapFragment;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yallagoom.R;
import com.yallagoom.activity.HomeCreateNewEventActivity;
import com.yallagoom.adapter.RecycleViewHome;
import com.yallagoom.api.NearByEventAsyncTask;
import com.yallagoom.entity.Event;
import com.yallagoom.interfaces.NearEventCallback;
import com.yallagoom.utils.Constant;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class HomeListFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private RecyclerView event_list;
    private SmartRefreshLayout refreshLayout;
    private RecycleViewHome recycleViewHome;
    private FloatingActionButton add_event;

    public HomeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        event_list = (RecyclerView) view.findViewById(R.id.event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        event_list.setLayoutManager(mLayoutManager);
        event_list.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        add_event = (FloatingActionButton) view.findViewById(R.id.add_event);
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeListFragment.this.getActivity(), HomeCreateNewEventActivity.class);
                startActivityForResult(intent, 210);
            }
        });
       /* ToolUtils.buildAlertMessageNoGps(HomeListFragment.this.getActivity(), this, this, new CheckGPSCallback() {
            @Override
            public void processFinish(boolean check, Status status) {
                if (!check) {
                    try {
                        status.startResolutionForResult(
                                HomeListFragment.this.getActivity(), REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                } else {

                    getData();
                }
            }
        });*/
        getData();

        return view;

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getData();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
            case 210:
                if (resultCode == 102) {
                    getData();
                }
                break;
        }
    }

    private void getData() {
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.e("refreshlayout", "refreshlayout");
                SmartLocation.with(HomeListFragment.this.getActivity()).location()
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(final Location location) {
                                Log.e("location", "" + location);

                                NearByEventAsyncTask nearByEventAsyncTask = new NearByEventAsyncTask(HomeListFragment.this.getActivity(), 0, refreshLayout, new NearEventCallback() {
                                    @Override
                                    public void processFinish(Event nearEvent) {
/*
                                        Constant.eventsData = nearEvent.getData();
*/
                                        recycleViewHome = new RecycleViewHome(nearEvent.getData());
                                        event_list.setAdapter(recycleViewHome);
                                        refreshLayout.finishRefresh();
                                    }
                                });
                                nearByEventAsyncTask.execute(location.getLatitude() + "", location.getLongitude() + "", Constant.defultDistance);
                            }
                        });

                //  nearByEventAsyncTask.execute("31.5139654", "34.4481495", "1");


            }
        });

    }

}

