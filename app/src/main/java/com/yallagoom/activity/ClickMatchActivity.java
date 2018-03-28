package com.yallagoom.activity;

import android.os.Handler;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewClickMatchSegmentedDetails;
import com.yallagoom.adapter.RecycleViewClickMatchSegmentedResult;
import com.yallagoom.adapter.RecycleViewClickMatchSegmentedStats;
import com.yallagoom.entity.Matches.MatchDetails;
import com.yallagoom.entity.Matches.ResultsList;
import com.yallagoom.entity.Matches.StatsList;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.segmented.SegmentedGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClickMatchActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private SegmentedGroup segmented_match;
    private RadioButton details;
    private View header_view;
    private RecyclerView details_list;
    private RecyclerView result_list;
    private TabLayout tabs_result;
    private TabLayout tabs_stats;
    private RecyclerView stats_list;
    private View participants_layout;
    private MatchDetails matchDetails;
    private TextView match_date;
    private TextView time;
    private TextView team_first;
    private TextView team_second;
    private RecycleViewClickMatchSegmentedDetails recycleViewClickMatchSegmentedDetails;
    private TabItem result_tab_1;
    private RecycleViewClickMatchSegmentedStats recycleViewClickMatchSegmentedStats;
    private RecycleViewClickMatchSegmentedResult recycleViewClickMatchSegmentedResult;
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView time_counter_down;
    private LinearLayout action_layout;
    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_match);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(ClickMatchActivity.this);
        ToolUtils.setLightStatusBar(parent, ClickMatchActivity.this);
        matchDetails = new Gson().fromJson(getIntent().getExtras().getString("matchDetails"), MatchDetails.class);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(R.string.schedule);
        header_view = (View) findViewById(R.id.header_view);
        header_view.setVisibility(View.VISIBLE);
        name_header = (TextView) findViewById(R.id.name_header);
        name_header.setText(R.string.match_details);
        time = (TextView) findViewById(R.id.time);
        match_date = (TextView) findViewById(R.id.match_date);
        match_date.setText(ToolUtils.convertDateFromFormatToFormat(matchDetails.getStart_date(), Constant.yyyy_MM_dd__HH_mm, Constant.EEEE_dd_MMM_yyyy));
        time = (TextView) findViewById(R.id.time);
        time.setText(ToolUtils.convertDateFromFormatToFormat(matchDetails.getStart_date(), Constant.yyyy_MM_dd__HH_mm, Constant.hh_mm_aa));
        time_counter_down = (TextView) findViewById(R.id.time_counter_down);
        team_first = (TextView) findViewById(R.id.team_first);
        team_second = (TextView) findViewById(R.id.team_second);
        team_first.setText(matchDetails.getParticipants_list().get(1).getParticipants_data().getShort_name());
        team_second.setText(matchDetails.getParticipants_list().get(0).getParticipants_data().getShort_name());

        segmented_match = (SegmentedGroup) findViewById(R.id.segmented_match);
        details = (RadioButton) findViewById(R.id.details);
        details.setChecked(true);
        action_layout = (LinearLayout) findViewById(R.id.action_layout);
        participants_layout = (View) findViewById(R.id.participants_layout);
        details_list = (RecyclerView) findViewById(R.id.details_list);
        result_list = (RecyclerView) findViewById(R.id.result_list);
        stats_list = (RecyclerView) findViewById(R.id.stats_list);
        result_list.setFocusable(false);
        details_list.setFocusable(false);
        stats_list.setFocusable(false);
        recycleViewClickMatchSegmentedDetails = new RecycleViewClickMatchSegmentedDetails(matchDetails.getDetails());
        details_list.setAdapter(recycleViewClickMatchSegmentedDetails);

      /*  result_list.setAdapter(recycleViewClickMatchSegmented);
        stats_list.setAdapter(recycleViewClickMatchSegmented);*/
        tabs_result = (TabLayout) findViewById(R.id.tabs_result);
        tabs_stats = (TabLayout) findViewById(R.id.tabs_stats);
        for (int i = 0; i < tabs_result.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.text_tap_font_medium, null);
            tabs_result.getTabAt(i).setCustomView(tv);
            tabs_result.getTabAt(i).setText(matchDetails.getParticipants_list().get(-1 * (i - 1)).getParticipants_data().getShort_name());
        }
        for (int i = 0; i < tabs_stats.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.text_tap_font_medium, null);
            tabs_stats.getTabAt(i).setCustomView(tv);
            tabs_stats.getTabAt(i).setText(matchDetails.getParticipants_list().get(-1 * (i - 1)).getParticipants_data().getShort_name());

        }
        segmented_match.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = segmented_match.getCheckedRadioButtonId();
                View radioButton = segmented_match.findViewById(radioButtonID);
                int idx = segmented_match.indexOfChild(radioButton);
                switch (idx) {
                    case 0:
                        details_list.setVisibility(View.VISIBLE);
                        participants_layout.setVisibility(View.GONE);
                        action_layout.setVisibility(View.GONE);
                        break;
                    case 1:
                        details_list.setVisibility(View.GONE);
                        action_layout.setVisibility(View.GONE);
                        participants_layout.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        action_layout.setVisibility(View.VISIBLE);
                        details_list.setVisibility(View.GONE);
                        participants_layout.setVisibility(View.GONE);
                        break;
                }
            }
        });
        tabs_result.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listResult(1);
                        break;
                    case 1:
                        listResult(0);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabs_stats.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listStats(1);
                        break;
                    case 1:
                        listStats(0);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        listResult(1);
        listStats(1);
        countDownStart();
        incidentsList();
    }

    public void Back(View view) {
        finish();
    }

    private void listResult(int flag) {
        ArrayList<ResultsList> resultsLists = new ArrayList<>();
        for (int i = 0; i < matchDetails.getResults_list().size(); i++) {
            if (matchDetails.getResults_list().get(i).getParticipants_id() == matchDetails.getParticipants_list().get(flag).getParticipant_id()) {
                resultsLists.add(matchDetails.getResults_list().get(i));
            }
        }
        recycleViewClickMatchSegmentedResult = new RecycleViewClickMatchSegmentedResult(resultsLists);
        result_list.setAdapter(recycleViewClickMatchSegmentedResult);

    }

    private void listStats(int flag) {
        ArrayList<StatsList> statsLists = new ArrayList<>();
        for (int i = 0; i < matchDetails.getResults_list().size(); i++) {
            if (matchDetails.getResults_list().get(i).getParticipants_id() == matchDetails.getParticipants_list().get(flag).getParticipant_id()) {
                statsLists.add(matchDetails.getStats_list().get(i));
            }
        }
        recycleViewClickMatchSegmentedStats = new RecycleViewClickMatchSegmentedStats(statsLists);
        stats_list.setAdapter(recycleViewClickMatchSegmentedStats);

    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.yyyy_MM_dd__HH_mm);
                    Date event_date = dateFormat.parse(matchDetails.getStart_date());
                    Date current_date = new Date();
                    if (!current_date.after(event_date) && (ToolUtils.converDateToString(event_date, Constant.yyyy_MM_dd).equalsIgnoreCase(
                            ToolUtils.converDateToString(current_date, Constant.yyyy_MM_dd)
                    ))) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        // Log.e("date", "" + Hours + ":" + Minutes + ":" + Seconds);
                        time_counter_down.setText(Hours + ":" + Minutes + ":" + Seconds);
                    } else {
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    private void incidentsList() {
        int ckeck_value = -1;
        for (int i = 0; i < matchDetails.getIncidents().size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View inflatedLayout = inflater.inflate(R.layout.list_item_incidents, null, false);
            RelativeLayout mes_left = (RelativeLayout) inflatedLayout.findViewById(R.id.mes_left);
            RelativeLayout view2_lay = (RelativeLayout) inflatedLayout.findViewById(R.id.view2_lay);
            RelativeLayout view1_lay = (RelativeLayout) inflatedLayout.findViewById(R.id.view1_lay);
            RelativeLayout small_circle = (RelativeLayout) inflatedLayout.findViewById(R.id.small_circle);
            RelativeLayout text_half = (RelativeLayout) inflatedLayout.findViewById(R.id.text_half);
            TextView text_message_left = (TextView) inflatedLayout.findViewById(R.id.text_message_left);
            TextView text_message_right = (TextView) inflatedLayout.findViewById(R.id.text_message_right);
            RelativeLayout mes_right = (RelativeLayout) inflatedLayout.findViewById(R.id.mes_right);
            ImageView icon = (ImageView) inflatedLayout.findViewById(R.id.icon);
            TextView time_left = (TextView) inflatedLayout.findViewById(R.id.time_left);
            TextView time_right = (TextView) inflatedLayout.findViewById(R.id.time_right);
            if (i == 0 || (ckeck_value != -1 && ckeck_value == i)) {
                Log.e(" index : "+i,"VISIBLE");
                small_circle.setVisibility(View.VISIBLE);
            } else {
                Log.e(" index : "+i,"GONE");
                small_circle.setVisibility(View.GONE);

            }
            if ((i + 1) < matchDetails.getIncidents().size() - 1 && (matchDetails.getIncidents().get(i + 1).getEvent_status_name().equalsIgnoreCase("2nd half")) && check) {
                check = false;
                ckeck_value = i + 1;
                view2_lay.setVisibility(View.VISIBLE);
                text_half.setVisibility(View.VISIBLE);
                check = false;
            } else {
                view2_lay.setVisibility(View.GONE);
            }
            if (i == matchDetails.getIncidents().size() - 1) {
                view2_lay.setVisibility(View.VISIBLE);
            }
            if (i % 2 == 0) {
                mes_left.setVisibility(View.VISIBLE);
                if (matchDetails.getIncidents().get(i).getSubparticipant_name().equalsIgnoreCase("")) {
                    text_message_left.setText(matchDetails.getIncidents().get(i).getParticipant_name());

                } else {
                    text_message_left.setText(matchDetails.getIncidents().get(i).getSubparticipant_name());

                }
                time_left.setText(matchDetails.getIncidents().get(i).getEvent_time().split(":")[0]+"`");
                mes_right.setVisibility(View.GONE);
            } else {
                mes_left.setVisibility(View.GONE);
                mes_right.setVisibility(View.VISIBLE);
                if (matchDetails.getIncidents().get(i).getSubparticipant_name().equalsIgnoreCase("")) {
                    text_message_right.setText(matchDetails.getIncidents().get(i).getParticipant_name());

                } else {
                    text_message_right.setText(matchDetails.getIncidents().get(i).getSubparticipant_name());

                }
                time_right.setText(matchDetails.getIncidents().get(i).getEvent_time().split(":")[0]+"`");

            }
            switch (matchDetails.getIncidents().get(i).getIncident_name()) {
                case "1st half started":
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Possible goal":
                    icon.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Goal":
                    icon.setImageResource(R.drawable.ic_goal);
                    break;
                case "Yellow card":
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._20sdp));
                    layoutParams.gravity=Gravity.CENTER;
                  //  layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    icon.setLayoutParams(layoutParams);
                    icon.setImageResource(R.drawable.ic_yallow_card);
                    break;
                case "Injury time":
                    icon.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "2nd half started":
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Substitution out":
                    icon.setImageResource(R.drawable.ic_change_payer);
                    break;
                case "Substitution in":
                    icon.setImageResource(R.drawable.ic_change_payer);
                    break;
                case "Possible card":
                    icon.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Halftime":
                    icon.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Finished regular time":
                    icon.setImageResource(R.drawable.ic_end_game);
                    break;
                case "Red card":
                    icon.setImageResource(R.drawable.ic_red_card);
                    break;
                case "Penalty":
                    icon.setImageResource(R.drawable.ic_penalty);
                    break;
                case "Possible penalty":
                    icon.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_start_game);
                    break;
                case "Missed penalty":
                    icon.setImageResource(R.drawable.ic_missed_penalty);
                    break;
            }
            action_layout.addView(inflatedLayout);
        }
    }
}
