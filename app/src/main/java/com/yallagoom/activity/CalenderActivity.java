package com.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yallagoom.R;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.compactcalendarview.CompactCalendarView;

import java.util.Date;
import java.util.List;

public class CalenderActivity extends AppCompatActivity {

    private TextView specific_date;
    private LinearLayout parent;
    private CompactCalendarView compactCalendarView;
    private String TAG = "CalenderActivity";
    private TextView previous;
    private TextView next;
    private TextView between;
    private TextView after_date;
    private TextView date_value;
    private TextView date_header;
    private DateFormat dateFormat;
    private TextView ok;
    private TextView start_date;
    private TextView end_date;
    private LinearLayout start_end_lay;
    private Date selectDate;
    private Date start_dateValue;
    private Date end_dateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        dateFormat = new DateFormat();
        selectDate = new Date();
        start_dateValue = selectDate;
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(CalenderActivity.this);
        ToolUtils.setLightStatusBar(parent, CalenderActivity.this);


        start_end_lay = (LinearLayout) findViewById(R.id.start_end_lay);
        start_date = (TextView) findViewById(R.id.start_date);
        start_date.setSelected(true);
        end_date = (TextView) findViewById(R.id.end_date);
        ok = (TextView) findViewById(R.id.ok);
        date_header = (TextView) findViewById(R.id.date_header);
        previous = (TextView) findViewById(R.id.previous);
        next = (TextView) findViewById(R.id.next);
        between = (TextView) findViewById(R.id.between);
        after_date = (TextView) findViewById(R.id.after_date);
        specific_date = (TextView) findViewById(R.id.specific_date);
        date_value = (TextView) findViewById(R.id.date_value);
        specific_date.setSelected(true);
        date_value.setText(dateFormat.format(Constant.dd_MMM_yyyy, selectDate) + "");
        date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, selectDate) + "");
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectDate = dateClicked;

                dateFormat.format(Constant.yyyy_MM_dd, dateClicked);
                date_value.setText(dateFormat.format(Constant.dd_MMM_yyyy, dateClicked) + "");
                //   date_header.setText(dateFormat.format("yyyy-MM-dd", dateClicked) + "");
                if (start_date.isSelected() && between.isSelected()) {
                    start_dateValue = dateClicked;
                    date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, dateClicked) + " - ");
                } else if (end_date.isSelected() && between.isSelected()) {
                    if (start_dateValue.getTime() > dateClicked.getTime()) {
                        ToolUtils.showSnak(CalenderActivity.this, getString(R.string.check_date_greater));
                    } else {
                        end_dateValue = dateClicked;
                        date_header.setText( dateFormat.format(Constant.yyyy_MM_dd, start_dateValue)+" - " + dateFormat.format(Constant.yyyy_MM_dd, dateClicked) + "");

                    }
                } else {
                    date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, dateClicked) + "");
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //      Log.e(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                date_value.setText(dateFormat.format(Constant.dd_MMM_yyyy, firstDayOfNewMonth) + "");
                //  date_header.setText(dateFormat.format("yyyy-MM-dd", firstDayOfNewMonth) + "");

            }
        });

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
        specific_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specific_date.setSelected(true);
                after_date.setSelected(false);
                between.setSelected(false);
                start_end_lay.setVisibility(View.GONE);
                date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, selectDate) + "");

            }
        });
        between.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                between.setSelected(true);
                after_date.setSelected(false);
                start_date.setSelected(true);
                end_date.setSelected(false);
                specific_date.setSelected(false);
                start_end_lay.setVisibility(View.VISIBLE);
                end_dateValue = null;

                date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, selectDate) + " - ");


            }
        });
        after_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                after_date.setSelected(true);
                specific_date.setSelected(false);
                between.setSelected(false);
                start_end_lay.setVisibility(View.GONE);
                date_header.setText(dateFormat.format(Constant.yyyy_MM_dd, selectDate) + "");

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (between.isSelected()) {
                    if (end_dateValue != null) {
                        intent.putExtra("date", date_header.getText().toString());
                        intent.putExtra("start_date", dateFormat.format(Constant.yyyy_MM_dd, start_dateValue) + "");
                        intent.putExtra("end_date", dateFormat.format(Constant.yyyy_MM_dd, end_dateValue) + "");
                        intent.putExtra("check", "between");

                        setResult(102, intent);
                        finish();
                    } else {
                        ToolUtils.showSnak(CalenderActivity.this, getString(R.string.check_end_date));

                    }

                } else if (specific_date.isSelected()){
                    intent.putExtra("date", date_header.getText().toString());
                    intent.putExtra("selectDate", dateFormat.format(Constant.yyyy_MM_dd, selectDate) + "");
                    intent.putExtra("check", "specific_date");
                    setResult(102, intent);
                    finish();
                }else if(after_date.isSelected()){
                    intent.putExtra("date", date_header.getText().toString());
                    intent.putExtra("selectDate", dateFormat.format(Constant.yyyy_MM_dd, selectDate) + "");
                    intent.putExtra("check", "after");
                    setResult(102, intent);
                    finish();
                }

            }
        });
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date.setSelected(true);
                end_date.setSelected(false);
            }
        });
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end_date.setSelected(true);
                start_date.setSelected(false);
            }
        });


    }

    public void Cancel(View view) {
        finish();
    }
}
