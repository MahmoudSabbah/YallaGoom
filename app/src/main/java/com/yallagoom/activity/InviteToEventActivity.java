package com.yallagoom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewChooseEventFriend;
import com.yallagoom.adapter.RecycleViewEventList;
import com.yallagoom.api.EventInvitationAsyncTask;
import com.yallagoom.api.MyEventAsyncTask;
import com.yallagoom.api.NewEventAsyncTask;
import com.yallagoom.entity.Event;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.NearEventCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InviteToEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private RecyclerView friend_list;
    private RelativeLayout my_event;
    private RelativeLayout new_event;
    private RecyclerView event_list;
    private ScrollView last_view;
    private RelativeLayout first_view;
    private LinearLayout parent;
    private EditText _sport_EditText;
    private EditText calendar_edit;
    private EditText location_edit;
    private SegmentedGroup segmented1;
    private SegmentedGroup segmented2;
    private LinearLayout _cost_liner;
    private TextView ok;
    private double lat = -1;
    private double lng = -1;
    private String start_date;
    private String end_date = "";
    private String checkDateType;
    private String selectDate;
    private int sport_id = -1;
    private EditText _title_EditText;
    private TextView title_remove;
    private TextView sport_remove;
    private TextView calendar_remove;
    private TextView cost_remove;
    private EditText cost_edit;
    private TextView location_remove;
    private EditText description_edit;
    private TextView description_remove;
    private ArrayList<Player.PlayerList> selectPlayerLists;
    private SwipeRefreshLayout swipe_refresh;
    private RecycleViewEventList recycleViewEventList;
    private EditText start_time_EditText;
    private TextView start_time_remove;
    private EditText end_time_EditText;
    private TextView end_time_remove;
    private Date startTimeValue;
    private ImageView image_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_event);
        ToolUtils.hideStatus(InviteToEventActivity.this);
        parent = (LinearLayout) findViewById(R.id.parent);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setEnabled(false);
        swipe_refresh.setRefreshing(true);

        image_event = (ImageView) findViewById(R.id.image_event);
        segmented1 = (SegmentedGroup) findViewById(R.id.segmented1);
        segmented2 = (SegmentedGroup) findViewById(R.id.segmented2);
        _cost_liner = (LinearLayout) findViewById(R.id._cost_liner);
        ToolUtils.setLightStatusBar(parent, InviteToEventActivity.this);
        selectPlayerLists = (ArrayList<Player.PlayerList>) getIntent().getSerializableExtra("selectPlayerLists");

        ok = (TextView) findViewById(R.id.ok);
        start_time_EditText = (EditText) findViewById(R.id.start_time);
        start_time_remove = (TextView) findViewById(R.id.start_time_remove);
        start_time_EditText.setFocusable(false);

        end_time_EditText = (EditText) findViewById(R.id.end_time);
        end_time_remove = (TextView) findViewById(R.id.end_time_remove);
        end_time_EditText.setFocusable(false);

        _title_EditText = (EditText) findViewById(R.id.title);
        title_remove = (TextView) findViewById(R.id.title_remove);

        _sport_EditText = (EditText) findViewById(R.id.search_sport);
        sport_remove = (TextView) findViewById(R.id.sport_remove);
        _sport_EditText.setFocusable(false);

        calendar_edit = (EditText) findViewById(R.id.calendar_edit);
        calendar_remove = (TextView) findViewById(R.id.calendar_remove);
        calendar_edit.setFocusable(false);

        cost_edit = (EditText) findViewById(R.id.cost);
        cost_remove = (TextView) findViewById(R.id.cost_remove);

        location_edit = (EditText) findViewById(R.id.location_edit);
        location_remove = (TextView) findViewById(R.id.location_remove);
        location_edit.setFocusable(false);

        description_edit = (EditText) findViewById(R.id.description);
        description_remove = (TextView) findViewById(R.id.description_remove);

        friend_list = (RecyclerView) findViewById(R.id.friend_list);
        event_list = (RecyclerView) findViewById(R.id.event_list);

        first_view = (RelativeLayout) findViewById(R.id.first_view);
        last_view = (ScrollView) findViewById(R.id.last_view);

        _title_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (_title_EditText.getText().toString().equalsIgnoreCase("")) {
                    title_remove.setVisibility(View.GONE);
                } else {
                    title_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        _sport_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (_sport_EditText.getText().toString().equalsIgnoreCase("")) {
                    sport_remove.setVisibility(View.GONE);
                } else {
                    sport_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        calendar_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (calendar_edit.getText().toString().equalsIgnoreCase("")) {
                    calendar_remove.setVisibility(View.GONE);
                } else {
                    calendar_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cost_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cost_edit.getText().toString().equalsIgnoreCase("")) {
                    cost_remove.setVisibility(View.GONE);
                } else {
                    cost_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        location_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (location_edit.getText().toString().equalsIgnoreCase("")) {
                    location_remove.setVisibility(View.GONE);
                } else {
                    location_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        description_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (description_edit.getText().toString().equalsIgnoreCase("")) {
                    description_remove.setVisibility(View.GONE);
                } else {
                    description_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        start_time_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (start_time_EditText.getText().toString().equalsIgnoreCase("")) {
                    start_time_remove.setVisibility(View.GONE);
                } else {
                    start_time_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        end_time_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (end_time_EditText.getText().toString().equalsIgnoreCase("")) {
                    end_time_remove.setVisibility(View.GONE);
                } else {
                    end_time_remove.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        RecycleViewChooseEventFriend recycleViewChooseEventFriend = new RecycleViewChooseEventFriend(selectPlayerLists);
        friend_list.setAdapter(recycleViewChooseEventFriend);
        my_event = (RelativeLayout) findViewById(R.id.my_event);
        my_event.setSelected(true);
        new_event = (RelativeLayout) findViewById(R.id.new_event);
        my_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_event.setSelected(true);
                new_event.setSelected(false);
                first_view.setVisibility(View.VISIBLE);
                last_view.setVisibility(View.GONE);
            }
        });
        new_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_event.setSelected(true);
                my_event.setSelected(false);
                first_view.setVisibility(View.GONE);
                last_view.setVisibility(View.VISIBLE);
            }
        });


        _sport_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteToEventActivity.this, SearchSportsActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        calendar_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteToEventActivity.this, CalenderActivity.class);
                startActivityForResult(intent, 102);
            }
        });
        location_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteToEventActivity.this, ChooseLocationActivity.class);
                intent.putExtra("check", true);
                startActivityForResult(intent, 103);
            }
        });
        segmented1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = segmented1.getCheckedRadioButtonId();
                View radioButton = segmented1.findViewById(radioButtonID);
                int idx = segmented1.indexOfChild(radioButton);
                if (idx == 0) {
                    _cost_liner.setVisibility(View.GONE);

                } else {
                    _cost_liner.setVisibility(View.VISIBLE);

                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (my_event.isSelected()) {
                    if (recycleViewEventList.eventId != -1) {
                        EventInvitationAsyncTask eventInvitationAsyncTask = new EventInvitationAsyncTask(InviteToEventActivity.this, selectPlayerLists, recycleViewEventList.eventId);
                        eventInvitationAsyncTask.execute();
                    } else {
                        ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_one_event));
                    }
                } else if (new_event.isSelected()) {
                    newEvent();
                }

            }
        });
        title_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _title_EditText.setText("");
            }
        });
        sport_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _sport_EditText.setText("");
            }
        });
        calendar_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar_edit.setText("");
            }
        });
        cost_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost_edit.setText("");
            }
        });
        description_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description_edit.setText("");
            }
        });
        location_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_edit.setText("");
            }
        });
        start_time_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time_EditText.setText("");
                startTimeValue = null;
            }
        });
        end_time_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end_time_EditText.setText("");
            }
        });
        start_time_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        InviteToEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "Start");

            }
        });
        end_time_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startTimeValue != null) {
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog timePickerDialog
                            = TimePickerDialog.newInstance(
                            InviteToEventActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND),
                            true
                    );
                    timePickerDialog.show(getFragmentManager(), "End");
                } else {
                    ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.add_start_time_check));
                }

            }
        });
        getMyEvent();
    }

    public void Cancel(View view) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 103:
                if (resultCode == 102) {
                    lat = data.getExtras().getDouble("lat");
                    lng = data.getExtras().getDouble("lng");
                    if (data.getExtras().getString("address").equalsIgnoreCase("")) {
                        location_edit.setText(lat + " , " + lng);

                    } else {
                        location_edit.setText(data.getExtras().getString("address"));
                    }

                }
                break;
            case 102:
                if (resultCode == 102) {
                    if (data.getExtras().getString("check").equalsIgnoreCase("between")) {
                        calendar_edit.setText(data.getExtras().getString("date"));
                        start_date = data.getExtras().getString("start_date");
                        end_date = data.getExtras().getString("end_date");
                        checkDateType = data.getExtras().getString("check");
                    } else {
                        calendar_edit.setText(data.getExtras().getString("date"));
                        start_date = data.getExtras().getString("selectDate");
                        checkDateType = data.getExtras().getString("check");
                    }
                }
                break;
            case 101:
                if (resultCode == 102) {
                    sport_id = data.getExtras().getInt("sport_id");
                    _sport_EditText.setText(data.getExtras().getString("sport_name"));
                }
                break;
        }

    }

    private void getMyEvent() {
        MyEventAsyncTask myEventAsyncTask = new MyEventAsyncTask(InviteToEventActivity.this, new NearEventCallback() {
            @Override
            public void processFinish(Event nearEvent) {

                recycleViewEventList = new RecycleViewEventList(event_list, nearEvent.getData());
                event_list.setAdapter(recycleViewEventList);
                swipe_refresh.setRefreshing(false);

            }
        });
        myEventAsyncTask.execute();
    }

    private void newEvent() {
        int radioButtonID = segmented1.getCheckedRadioButtonId();
        View radioButton = segmented1.findViewById(radioButtonID);
        int idx = segmented1.indexOfChild(radioButton);

        int radioButtonID2 = segmented2.getCheckedRadioButtonId();
        View radioButton2 = segmented2.findViewById(radioButtonID2);
        int pub_pri = segmented2.indexOfChild(radioButton2);

        if (_title_EditText.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.please_enter_title));
        } else if (_sport_EditText.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_sport));
        } else if (calendar_edit.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_date));
        } else if (start_time_EditText.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_start_time));
        } else if (idx == -1) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_event_price));
        } else if (idx == 1 && cost_edit.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_cost));
        } else if (location_edit.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.enter_event_location));
        } else if (pub_pri == -1) {
            ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.select_event_type));
        } else {
            Bitmap bitmap = ((BitmapDrawable) image_event.getDrawable()).getBitmap();

            NewEventAsyncTask newEventAsyncTask = new NewEventAsyncTask(InviteToEventActivity.this, sport_id,
                    _title_EditText.getText().toString(), start_date, end_date, start_time_EditText.getText().toString(),
                    end_time_EditText.getText().toString(),
                    bitmap, description_edit.getText().toString(), idx, cost_edit.getText().toString(), "",
                    "", pub_pri, "", "", "", ""
                    , "", "", lat, lng, selectPlayerLists);
            newEventAsyncTask.execute();

        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        Date date = cal.getTime();
        DateFormat dateFormat = new DateFormat();
        if (view.getTag().equalsIgnoreCase("Start")) {
            startTimeValue = date;
            start_time_EditText.setText(dateFormat.format(Constant.HH_mm_ss, date));

        } else {
            Log.e("startTimeValue", "" + startTimeValue);
            Log.e("startTimeValue", "" + date);

            if (startTimeValue != null && startTimeValue.getTime() < date.getTime()) {
                end_time_EditText.setText(dateFormat.format(Constant.HH_mm_ss, date));
            } else {
                ToolUtils.showSnak(InviteToEventActivity.this, getString(R.string.time_check));

            }

        }
        Log.e("TimePickerDialog", "You picked the following time: " + hourOfDay + "h" + minute + "m" + second);
    }
}
