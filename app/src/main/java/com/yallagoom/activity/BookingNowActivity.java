package com.yallagoom.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewBookingNow;
import com.yallagoom.entity.Booking.BookingSettings;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.compactcalendarview.CompactCalendarView;
import com.yallagoom.widget.compactcalendarview.domain.Event;

import java.util.Calendar;
import java.util.Date;

public class BookingNowActivity extends AppCompatActivity {

    private RelativeLayout parent;
    private TextView name_header;
    private TextView right_text;
    private TextView left_text;
    private RecyclerView booking_list;
    private BookingSettings bookingData;
    private TextView previous;
    private TextView next;
    private CompactCalendarView compactCalendarView;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_now);
        parent = (RelativeLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(BookingNowActivity.this);
        ToolUtils.setLightStatusBar(parent, BookingNowActivity.this);
        Bundle bundle = getIntent().getExtras();
        bookingData=new Gson().fromJson(  bundle.getString("dataResult"), BookingSettings.class);
        scroll = (ScrollView) findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);
        previous = (TextView) findViewById(R.id.previous);
        next = (TextView) findViewById(R.id.next);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view_start);
     //   compactCalendarView.
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Event ev1 = new Event(ContextCompat.getColor(getApplicationContext(),R.color.color_df488a),cal.getTimeInMillis(), "Some extra data that I want to store.");
       compactCalendarView.addEvent(ev1,false);
        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.GONE);
        name_header.setText(getString(R.string.tickets));
        booking_list = (RecyclerView) findViewById(R.id.booking_list);
        RecycleViewBookingNow recycleViewBookingNow = new RecycleViewBookingNow(bookingData.getRows_data().getData());
        booking_list.setFocusable(false);
        booking_list.setAdapter(recycleViewBookingNow);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });
    }

    public void Back(View view) {
        finish();
    }
}
