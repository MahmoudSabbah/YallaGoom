package com.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewWhoGoing;
import com.yallagoom.api.UpdateInvitationStatusAsyncTask;
import com.yallagoom.entity.Event;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.segmented.SegmentedGroup;

public class UpcomingEventListClickActivity extends AppCompatActivity {
    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private RecyclerView who_going_list;
    private Bundle bundle;
    private Event.DataEvent upcomingEvent;
    private TextView title;
    private TextView description;
    private TextView location_name;
    private RelativeLayout location_lay;
    private TextView start_time;
    private TextView end_time;
    private SegmentedGroup segmented1;
    private TextView sat_value;
    private TextView sun_value;
    private TextView mon_value;
    private TextView tue_value;
    private TextView thu_value;
    private TextView wen_value;
    private TextView fri_value;
    private TextView date_text;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upcoming_event_details);
        dateFormat = new DateFormat();

        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(UpcomingEventListClickActivity.this);
        ToolUtils.setLightStatusBar(parent, UpcomingEventListClickActivity.this);
        bundle = getIntent().getExtras();
        upcomingEvent = (Event.DataEvent) bundle.getSerializable("upcomingEvent");

        who_going_list = (RecyclerView) findViewById(R.id.who_going_list);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        location_name = (TextView) findViewById(R.id.location_name);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(upcomingEvent.getEventTitle());
        right_text.setText(getString(R.string.ok));
        segmented1 = (SegmentedGroup) findViewById(R.id.segmented1);
        location_lay = (RelativeLayout) findViewById(R.id.location_lay);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        date_text = (TextView) findViewById(R.id.date_text);
        date_text.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(upcomingEvent.getStartEventDate(), "yyyy-MM-dd")) + "");

        RecycleViewWhoGoing recycleViewWhoGoing = new RecycleViewWhoGoing();
        who_going_list.setAdapter(recycleViewWhoGoing);
        title.setText(upcomingEvent.getEventTitle());
        description.setText(upcomingEvent.getEventDescription());
        start_time.setText(ToolUtils.convert24TimeTo12(upcomingEvent.getStartEventTime()));
        if (upcomingEvent.getEndEventTime() != null) {
            end_time.setText(ToolUtils.convert24TimeTo12(upcomingEvent.getEndEventTime()));

        } else {
            end_time.setVisibility(View.INVISIBLE);
        }
        sat_value = (TextView) findViewById(R.id.sat_value);
        sun_value = (TextView) findViewById(R.id.sun_value);
        mon_value = (TextView) findViewById(R.id.mon_value);
        tue_value = (TextView) findViewById(R.id.tue_value);
        wen_value = (TextView) findViewById(R.id.wen_value);
        thu_value = (TextView) findViewById(R.id.thu_value);
        fri_value = (TextView) findViewById(R.id.fri_value);
        ToolUtils.getCalenderView(ToolUtils.converStringToDate(upcomingEvent.getStartEventDate(), Constant.yyyy_MM_dd),
                sat_value,sun_value,mon_value,tue_value,wen_value,thu_value,fri_value);
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = segmented1.getCheckedRadioButtonId();
                View radioButton = segmented1.findViewById(radioButtonID);
                int idRadio = segmented1.indexOfChild(radioButton);
                if (idRadio != -1) {
                    String status = "";
                    if (idRadio == 0) {
                        status = "maybe";
                    } else if (idRadio == 1) {
                        status = "going";
                    } else {
                        status = "not interested";
                    }
                    UpdateInvitationStatusAsyncTask updateInvitationStatusAsyncTask = new UpdateInvitationStatusAsyncTask(UpcomingEventListClickActivity.this);
                    updateInvitationStatusAsyncTask.execute(upcomingEvent.getId() + "", status);
                } else {
                    ToolUtils.showSnak(UpcomingEventListClickActivity.this, getString(R.string.select_your_status));
                }
            }
        });

    }

    public void Back(View view) {
        finish();
    }

}
