package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewWhoGoing;
import com.oxygen.yallagoom.api.UpdateInvitationStatusAsyncTask;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.entity.event.InvitationRecord;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.HashMap;

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
    private RadioButton may_be;
    private RadioButton going;
    private RadioButton not_interested;
    private SelectableRoundedImageView my_event_image;
    private ImageLoader imageLoader;
    private ArrayList<InvitationRecord> Invited_list;
    private HashMap<String, ArrayList<InvitationRecord>> invitedList;
    private LinearLayout content_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upcoming_event_details);
        imageLoader = ImageLoader.getInstance();
        dateFormat = new DateFormat();
        content_lay = (LinearLayout) findViewById(R.id.content_lay);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(UpcomingEventListClickActivity.this);
        ToolUtils.setLightStatusBar(parent, UpcomingEventListClickActivity.this);
        bundle = getIntent().getExtras();
        upcomingEvent = (Event.DataEvent) bundle.getSerializable("EventData");
        if (getIntent().hasExtra("Invited_list")) {
            Invited_list = (ArrayList<InvitationRecord>) bundle.getSerializable("Invited_list");
            invitedList = new HashMap<>();
            for (int i = 0; i < Invited_list.size(); i++) {
                switch (Invited_list.get(i).getInvitation_status()) {
                    case "going":
                        if (invitedList.containsKey("going")) {
                            invitedList.get("going").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("going", invitationRecords);
                        }
                        break;
                    case "maybe":
                        if (invitedList.containsKey("maybe")) {
                            invitedList.get("maybe").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("maybe", invitationRecords);
                        }
                        break;
                    case "not interested":
                        if (invitedList.containsKey("not interested")) {
                            invitedList.get("not interested").add(Invited_list.get(i));
                        } else {
                            ArrayList<InvitationRecord> invitationRecords = new ArrayList<>();
                            invitationRecords.add(Invited_list.get(i));
                            invitedList.put("not interested", invitationRecords);
                        }
                        break;
                }
            }
            for (int j = 0; j < invitedList.size(); j++) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View inflatedLayout = inflater.inflate(R.layout.who_respond_invitation, null, false);
                RecyclerView who_going_list_data = (RecyclerView) inflatedLayout.findViewById(R.id.who_going_list);
                TextView title = (TextView) inflatedLayout.findViewById(R.id.title);
                RecycleViewWhoGoing recycleViewWhoGoing = new RecycleViewWhoGoing(invitedList.keySet().toArray()[j] + "", invitedList.get(invitedList.keySet().toArray()[j]));
                who_going_list_data.setAdapter(recycleViewWhoGoing);
                switch (invitedList.keySet().toArray()[j] + "") {
                    case "going":
                        title.setText(getString(R.string.respond_going));
                        break;
                    case "maybe":
                        title.setText(getString(R.string.respond_maybe));
                        break;
                    case "not interested":
                        title.setText(getString(R.string.respond_not_interested));
                        break;
                }
                content_lay.addView(inflatedLayout);
            }
        }


        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        location_name = (TextView) findViewById(R.id.location_name);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setVisibility(View.INVISIBLE);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        my_event_image = (SelectableRoundedImageView) findViewById(R.id.my_event_image);
        if (upcomingEvent.getEventImage() != null) {
            ToolUtils.setImage(Constant.imageUrl + upcomingEvent.getEventImage(), my_event_image, imageLoader);
        }
        header_title.setText(upcomingEvent.getEventTitle());
        right_text.setText(getString(R.string.ok));
        segmented1 = (SegmentedGroup) findViewById(R.id.segmented1);
        may_be = (RadioButton) findViewById(R.id.may_be);
        going = (RadioButton) findViewById(R.id.going);
        not_interested = (RadioButton) findViewById(R.id.not_interested);

        location_lay = (RelativeLayout) findViewById(R.id.location_lay);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        date_text = (TextView) findViewById(R.id.date_text);
        date_text.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, ToolUtils.converStringToDate(upcomingEvent.getStartEventDate(), "yyyy-MM-dd")) + "");


        title.setText(upcomingEvent.getEventTitle());
        description.setText(upcomingEvent.getEventDescription());
        start_time.setText(ToolUtils.convert24TimeTo12(upcomingEvent.getStartEventTime()));
        if (upcomingEvent.getEndEventTime() != null) {
            end_time.setText(ToolUtils.convert24TimeTo12(upcomingEvent.getEndEventTime()));

        } else {
            end_time.setVisibility(View.INVISIBLE);
        }
        if (upcomingEvent.getMy_invitation_record() != null && upcomingEvent.getMy_invitation_record().size() > 0) {
            switch (upcomingEvent.getMy_invitation_record().get(0).getInvitation_status()) {
                case "going":
                    going.setChecked(true);
                    break;
                case "maybe":
                    may_be.setChecked(true);
                    break;
                case "not interested":
                    not_interested.setChecked(true);
                    break;
            }
        }
        if ((upcomingEvent.getMy_invitation_record() != null && upcomingEvent.getMy_invitation_record().size() == 0
                && upcomingEvent.getPrivateOrPublic().equalsIgnoreCase("public")) || upcomingEvent.getMy_invitation_record() == null) {
            right_text.setVisibility(View.INVISIBLE);
            segmented1.setVisibility(View.GONE);
        }
        sat_value = (TextView) findViewById(R.id.sat_value);
        sun_value = (TextView) findViewById(R.id.sun_value);
        mon_value = (TextView) findViewById(R.id.mon_value);
        tue_value = (TextView) findViewById(R.id.tue_value);
        wen_value = (TextView) findViewById(R.id.wen_value);
        thu_value = (TextView) findViewById(R.id.thu_value);
        fri_value = (TextView) findViewById(R.id.fri_value);
        ToolUtils.getCalenderView(ToolUtils.converStringToDate(upcomingEvent.getStartEventDate(), Constant.yyyy_MM_dd),
                sat_value, sun_value, mon_value, tue_value, wen_value, thu_value, fri_value);
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
