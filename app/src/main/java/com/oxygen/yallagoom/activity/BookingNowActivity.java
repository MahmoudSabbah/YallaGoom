package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.action.RecyclerItemClickListener;
import com.oxygen.yallagoom.adapter.RecycleViewBookingDateList;
import com.oxygen.yallagoom.adapter.RecycleViewBookingNow;
import com.oxygen.yallagoom.entity.Booking.BookingSettings;
import com.oxygen.yallagoom.utils.ToolUtils;

public class BookingNowActivity extends AppCompatActivity {

    private RelativeLayout parent;
    private TextView name_header;
    private TextView right_text;
    private TextView left_text;
    private RecyclerView booking_list;
    private BookingSettings bookingData;
    private ScrollView scroll;
    private RecyclerView date_list;
    private RecycleViewBookingDateList recycleViewBookingDateList;
    private View lastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_now);
        parent = (RelativeLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(BookingNowActivity.this);
        ToolUtils.setLightStatusBar(parent, BookingNowActivity.this);
        Bundle bundle = getIntent().getExtras();
        bookingData = new Gson().fromJson(bundle.getString("dataResult"), BookingSettings.class);
        scroll = (ScrollView) findViewById(R.id.scroll);
        scroll.smoothScrollTo(0, 0);
        //   compactCalendarView.
        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.GONE);
        name_header.setText(getString(R.string.tickets));
        date_list = (RecyclerView) findViewById(R.id.date_list);
        recycleViewBookingDateList = new RecycleViewBookingDateList(bookingData.getTickets_dates());
        date_list.setAdapter(recycleViewBookingDateList);
        booking_list = (RecyclerView) findViewById(R.id.booking_list);
        RecycleViewBookingNow recycleViewBookingNow = new RecycleViewBookingNow(bookingData.getRows_data().getData());
        booking_list.setFocusable(false);
        booking_list.setAdapter(recycleViewBookingNow);
        date_list.addOnItemTouchListener(new RecyclerItemClickListener(BookingNowActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView date_value = (TextView) view.findViewById(R.id.date_value);
                date_value.setSelected(true);
                if (lastView!=null){
                    TextView date_value_Last = (TextView) lastView.findViewById(R.id.date_value);
                    date_value_Last.setSelected(false);
                }
                lastView=view;

            }
        }));

    }

    public void Back(View view) {
        finish();
    }
}
