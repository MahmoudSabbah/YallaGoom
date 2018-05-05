package com.oxygen.yallagoom.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewTicketsDetailsMoreReviews;
import com.oxygen.yallagoom.api.BookingsettingsAsyncTask;
import com.oxygen.yallagoom.api.TicketDetailsAsyncTask;
import com.oxygen.yallagoom.entity.TicketClasses.ReviewListData;
import com.oxygen.yallagoom.entity.TicketClasses.TicketDetails;
import com.oxygen.yallagoom.interfaces.TicketDeatailsCallback;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import io.realm.RealmList;

public class TicketsdetailsMoreActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LinearLayout parent;
    private TextView name_header;
    private View header_view;
    private TextView left_text;
    private RecyclerView list_reviews;
    private LinearLayout reviews_layut;
    private LinearLayout description_layout;
    private LinearLayout map_layout;
    private RelativeLayout booking_bt1;
    private RelativeLayout booking_bt2;
    private RelativeLayout booking_bt3;
    private SegmentedGroup segmented;
    private RadioButton description_radio;
    private SupportMapFragment mapFragment;
    private Bundle bundle;
    private GoogleMap mMap;
    private int flag;
    private RadioButton reviews_radio;
    private RadioButton map_radio;
    private String mTicketsDetails;
    private TicketDetails ticketDetails;
    private TextView description_value;
    private RecycleViewTicketsDetailsMoreReviews recycleViewTicketsDetailsMoreReviews;
    private SmartRefreshLayout refreshLayout_;
    private int page = 2;
    private RelativeLayout right_text;
    private RealmList<ReviewListData> list_reviewsData;
    private RelativeLayout booking_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketsdetails_more);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(TicketsdetailsMoreActivity.this);
        ToolUtils.setLightStatusBar(parent, TicketsdetailsMoreActivity.this);
        bundle = getIntent().getExtras();
        flag = bundle.getInt("flag");
        mTicketsDetails = bundle.getString("ticketDetails");
        ticketDetails = new Gson().fromJson(mTicketsDetails, TicketDetails.class);

        name_header = (TextView) findViewById(R.id.name_header);
        name_header.setText(ticketDetails.getTicket_info().getTitle());

        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (RelativeLayout) findViewById(R.id.right_text);

        header_view = (View) findViewById(R.id.header_view);
        header_view.setVisibility(View.VISIBLE);
        left_text.setText(getString(R.string.back));
        description_value = (TextView) findViewById(R.id.description_value);
        ToolUtils.setHtmlToTextView(description_value, ticketDetails.getTicket_info().getFull_description());

        segmented = (SegmentedGroup) findViewById(R.id.segmented);
        description_radio = (RadioButton) findViewById(R.id.description_radio);
        reviews_radio = (RadioButton) findViewById(R.id.reviews_radio);
        map_radio = (RadioButton) findViewById(R.id.map_radio);
        refreshLayout_ = (SmartRefreshLayout) findViewById(R.id.refreshLayout_);
        list_reviews = (RecyclerView) findViewById(R.id.list_reviews);
        list_reviews.setNestedScrollingEnabled(false);
        list_reviews.setFocusable(false);
        list_reviewsData = ticketDetails.getReview_list().getData();
        recycleViewTicketsDetailsMoreReviews = new RecycleViewTicketsDetailsMoreReviews(list_reviewsData);
        list_reviews.setAdapter(recycleViewTicketsDetailsMoreReviews);
        reviews_layut = (LinearLayout) findViewById(R.id.reviews_layut);
        description_layout = (LinearLayout) findViewById(R.id.description_layout);
        map_layout = (LinearLayout) findViewById(R.id.map_layout);
        booking_bt1 = (RelativeLayout) findViewById(R.id.booking_bt1);
        booking_bt2 = (RelativeLayout) findViewById(R.id.booking_bt2);
        booking_bt3 = (RelativeLayout) findViewById(R.id.booking_bt3);
        booking_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingNow();
            }
        });
        booking_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingNow();
            }
        });
        booking_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingNow();
            }
        });
        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = segmented.getCheckedRadioButtonId();
                View radioButton = segmented.findViewById(radioButtonID);
                int idx = segmented.indexOfChild(radioButton);
                switch (idx) {
                    case 0:
                        description_layout.setVisibility(View.VISIBLE);
                        reviews_layut.setVisibility(View.GONE);
                        map_layout.setVisibility(View.GONE);

                        break;
                    case 1:
                        description_layout.setVisibility(View.GONE);
                        reviews_layut.setVisibility(View.VISIBLE);
                        map_layout.setVisibility(View.GONE);

                        break;
                    case 2:
                        description_layout.setVisibility(View.GONE);
                        reviews_layut.setVisibility(View.GONE);
                        map_layout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        if (flag == 0) {
            description_layout.setVisibility(View.VISIBLE);
            reviews_layut.setVisibility(View.GONE);
            map_layout.setVisibility(View.GONE);
            description_radio.setChecked(true);

        } else if (flag == 1) {
            description_layout.setVisibility(View.GONE);
            reviews_layut.setVisibility(View.VISIBLE);
            map_layout.setVisibility(View.GONE);
            reviews_radio.setChecked(true);
        } else if (flag == 2) {
            description_layout.setVisibility(View.GONE);
            reviews_layut.setVisibility(View.GONE);
            map_layout.setVisibility(View.VISIBLE);
            map_radio.setChecked(true);

        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(TicketsdetailsMoreActivity.this);

        refreshLayout_.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                setMoreReviews();
            }
        });
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "YallGoom");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void setMoreReviews() {
        refreshLayout_.autoLoadmore();
        TicketDetailsAsyncTask ticketDetailsAsyncTask = new TicketDetailsAsyncTask(TicketsdetailsMoreActivity.this, true, new TicketDeatailsCallback() {
            @Override
            public void processFinish(String result) {
                TicketDetails ticketResult = new Gson().fromJson(result, TicketDetails.class);
                Log.e("getReview_list", "" + ticketResult.getReview_list().getData().size());
                for (int i = 0; i < ticketResult.getReview_list().getData().size(); i++) {
                    list_reviewsData.add(ticketResult.getReview_list().getData().get(i));
                }
                recycleViewTicketsDetailsMoreReviews.notifyDataSetChanged();
                refreshLayout_.finishLoadmore();
                page++;
            }
        });

        ticketDetailsAsyncTask.execute(ticketDetails.getTicket_info().getId() + "", page + "");

    }

    public void Back(View view) {
        finish();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng location = new LatLng(Double.parseDouble(bundle.getString("lat")), Double.parseDouble(bundle.getString("lng")));
        final MarkerOptions markerOptions = new MarkerOptions().position(
                location).zIndex(1.0f);//fromBitmap(MapUtils.getbitmap(CONTEXT, MapUtils.carIconMarker))
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ToolUtils.getbitmap(TicketsdetailsMoreActivity.this, R.drawable.main_marker)));
        mMap.addMarker(markerOptions);
        final CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(location, 15);
        //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( targetPosition, 15 ), 1000, null);
        mMap.animateCamera(yourLocation);

    }

    private void BookingNow() {
        BookingsettingsAsyncTask bookingsettingsAsyncTask=new BookingsettingsAsyncTask(TicketsdetailsMoreActivity.this);
        bookingsettingsAsyncTask.execute(ticketDetails.getTicket_info().getId()+"");
    }
}
