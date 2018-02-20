package com.yallagoom.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yallagoom.R;
import com.yallagoom.api.NewEventAsyncTask;
import com.yallagoom.api.UpdateEventAsyncTask;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.segmented.SegmentedGroup;

import java.util.Calendar;
import java.util.Date;

public class HomeCreateNewEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private TextView title;
    private EditText add_description;
    private CompactCalendarView compactcalendar_view_start;
    private TextView date_value;
    private DateFormat dateFormat;
    private TextView date_value__end;
    private CompactCalendarView compactcalendar_end;
    private RelativeLayout location_name;
    private TextView name_location;
    private double lat = -1;
    private double lng = -1;
    private TextView previous;
    private TextView next;
    private TextView previous_end;
    private TextView next_end;
    private TextView start_time;
    private TextView end_time;
    private RelativeLayout cat_layout;
    private TextView cat_name;
    private int categoryId = -1;
    private SegmentedGroup segmented_cost;
    private RadioButton free;
    private RadioButton paid;
    private EditText cost_edit;
    private SegmentedGroup public_private_seg;
    private RadioButton private_;
    private RadioButton public_;
    private String startTimeValue = "";
    private String endTimeValue = "";
    private Date startDate;
    private Date endDate;
    private RoundedImageView event_image;
    private boolean checkLocation = true;
    private EditText organizer_description;
    private EditText organizer_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_create_new_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(HomeCreateNewEventActivity.this);
        ToolUtils.setLightStatusBar(parent, HomeCreateNewEventActivity.this);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(R.string.create_new_event);
        right_text.setText(getString(R.string.save));
        left_text.setText(getString(R.string.cancel));


        event_image = (RoundedImageView) findViewById(R.id.event_image);
        title = (TextView) findViewById(R.id.title);
        add_description = (EditText) findViewById(R.id.add_description);
        organizer_description = (EditText) findViewById(R.id.organizer_description);
        organizer_name = (EditText) findViewById(R.id.organizer_name);
        compactcalendar_view_start = (CompactCalendarView) findViewById(R.id.compactcalendar_view_start);
        compactcalendar_end = (CompactCalendarView) findViewById(R.id.compactcalendar_end);
        location_name = (RelativeLayout) findViewById(R.id.location_name);
        name_location = (TextView) findViewById(R.id.name_location);

        date_value = (TextView) findViewById(R.id.date_value);
        date_value__end = (TextView) findViewById(R.id.date_value__end);
        previous = (TextView) findViewById(R.id.previous);
        next = (TextView) findViewById(R.id.next);
        previous_end = (TextView) findViewById(R.id.previous_end);
        next_end = (TextView) findViewById(R.id.next_end);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        cat_layout = (RelativeLayout) findViewById(R.id.cat_layout);
        cat_name = (TextView) findViewById(R.id.cat_name);
        segmented_cost = (SegmentedGroup) findViewById(R.id.segmented_cost);
        public_private_seg = (SegmentedGroup) findViewById(R.id.public_private_seg);
        free = (RadioButton) findViewById(R.id.free);
        paid = (RadioButton) findViewById(R.id.paid);
        private_ = (RadioButton) findViewById(R.id.private_);
        public_ = (RadioButton) findViewById(R.id.public_);

        cost_edit = (EditText) findViewById(R.id.cost_edit);
        startDate = new Date();
        endDate = new Date();
        date_value.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, startDate) + "");
        date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, endDate) + "");

        compactcalendar_view_start.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                startDate = dateClicked;
                date_value.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, dateClicked) + "");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
        compactcalendar_end.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                endDate = dateClicked;
                date_value__end.setText(dateFormat.format(Constant.EEEE_dd_MMM_yyyy, dateClicked) + "");

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        location_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCreateNewEventActivity.this, ChooseLocationActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("check", checkLocation);
                startActivityForResult(intent, 202);

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_view_start.showPreviousMonth();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_view_start.showNextMonth();
            }
        });
        previous_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_end.showPreviousMonth();
            }
        });
        next_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactcalendar_end.showNextMonth();
            }
        });


        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        HomeCreateNewEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "Start");
            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog
                        = TimePickerDialog.newInstance(
                        HomeCreateNewEventActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                timePickerDialog.show(getFragmentManager(), "End");
            }
        });
        end_time.setText("   -   ");
        start_time.setText("   -   ");
        cat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCreateNewEventActivity.this, SearchCategoryActivity.class);
                startActivityForResult(intent, 203);
            }
        });

        segmented_cost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = segmented_cost.getCheckedRadioButtonId();
                View radioButton = segmented_cost.findViewById(radioButtonID);
                int idx = segmented_cost.indexOfChild(radioButton);
                if (idx == 0) {
                    cost_edit.setVisibility(View.GONE);
                } else {
                    cost_edit.setVisibility(View.VISIBLE);
                }
            }
        });


        right_text.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                newEvent();
            }
        });


    }

    public void Back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 202:
                if (resultCode == 102) {
                    lat = data.getExtras().getDouble("lat");
                    lng = data.getExtras().getDouble("lng");
                    checkLocation = false;
                    if (data.getExtras().getString("address").equalsIgnoreCase("")) {
                        name_location.setText(lat + " , " + lng);

                    } else {
                        name_location.setText(data.getExtras().getString("address"));
                    }

                }
                break;
            case 203:
                if (resultCode == 102) {
                    categoryId = data.getExtras().getInt("cat_id");
                    cat_name.setText(data.getExtras().getString("cat_name"));

                }
                break;
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
            startTimeValue = dateFormat.format(Constant.HH_mm_ss, date) + "";
            start_time.setText(dateFormat.format(Constant.hh_mm_aa, date));
        } else {

            String[] st_time = startTimeValue.split(":");
            String[] en_time = (dateFormat.format(Constant.HH_mm_ss, date) + "").split(":");
            double finishStartTime = Double.parseDouble(st_time[0]) + (Double.parseDouble(st_time[1]) / 60);
            double finishEndTime = Double.parseDouble(en_time[0]) + (Double.parseDouble(en_time[1]) / 60);
            if (startTimeValue != null && finishStartTime <= finishEndTime) {
                end_time.setText(dateFormat.format(Constant.hh_mm_aa, date));
                endTimeValue = dateFormat.format(Constant.HH_mm_ss, date) + "";
            } else {
                ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.time_check));

            }

        }
    }

    private void newEvent() {
        int radioButtonID = segmented_cost.getCheckedRadioButtonId();
        View radioButton = segmented_cost.findViewById(radioButtonID);
        int idx = segmented_cost.indexOfChild(radioButton);

        int radioButtonID2 = public_private_seg.getCheckedRadioButtonId();
        View radioButton2 = public_private_seg.findViewById(radioButtonID2);
        int pub_pri = public_private_seg.indexOfChild(radioButton2);
        if (pub_pri == 0) {
            pub_pri = 1;
        } else if (pub_pri == 1) {
            pub_pri = 0;
        }
        if (title.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.please_enter_title));
        } else if (lat == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.enter_event_location));
        } else if (startTimeValue.toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_start_time));
        } else if (categoryId == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_cat));
        } else if (idx == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_event_price));
        } else if (idx == 1 && cost_edit.getText().toString().equalsIgnoreCase("")) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_cost));
        } else if (pub_pri == -1) {
            ToolUtils.showSnak(HomeCreateNewEventActivity.this, getString(R.string.select_event_type));
        } else {
            // Bitmap bitmap = ((BitmapDrawable) event_image.getDrawable()).getBitmap();

            NewEventAsyncTask newEventAsyncTask = new NewEventAsyncTask(HomeCreateNewEventActivity.this, categoryId,
                    title.getText().toString(), dateFormat.format(Constant.yyyy_MM_dd, startDate) + ""
                    , dateFormat.format(Constant.yyyy_MM_dd, endDate) + "", startTimeValue,
                    endTimeValue,
                    null, add_description.getText().toString(), idx, cost_edit.getText().toString(), organizer_name.getText().toString(),
                    organizer_description.getText().toString(), pub_pri, "", "", "", ""
                    , "", "", lat, lng, null);
            newEventAsyncTask.execute();

        }
    }

}
