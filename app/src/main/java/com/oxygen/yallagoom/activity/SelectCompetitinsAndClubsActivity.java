package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewCompetitionsSettingsSelect;
import com.oxygen.yallagoom.entity.Matches.ClubCompetitionList;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

public class SelectCompetitinsAndClubsActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView left_text;
    private View header_view;
    private TextView name_header;
    private SegmentedGroup segmented_check;
    private RadioButton competitions;
    private IndexFastScrollRecyclerView comp_club_list;
    private ClubCompetitionList clubCompetitionList;
    private RecycleViewCompetitionsSettingsSelect recycleViewCompetitionsSettingsSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_competitins_and_clubs);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SelectCompetitinsAndClubsActivity.this);
        ToolUtils.setLightStatusBar(parent, SelectCompetitinsAndClubsActivity.this);
        clubCompetitionList = new Gson().fromJson(getIntent().getExtras().getString("ResultClubCompitetion"), ClubCompetitionList.class);

        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setVisibility(View.GONE);
        // left_text.setText(R.string.schedule);
        header_view = (View) findViewById(R.id.header_view);
        header_view.setVisibility(View.VISIBLE);
        name_header = (TextView) findViewById(R.id.name_header);
        name_header.setText(getIntent().getExtras().getString("title"));
        segmented_check = (SegmentedGroup) findViewById(R.id.segmented_check);
        if (getIntent().getExtras().getString("type").equalsIgnoreCase("international")){
            segmented_check.setVisibility(View.GONE);
        }
        competitions = (RadioButton) findViewById(R.id.competitions);
        competitions.setChecked(true);
        comp_club_list = (IndexFastScrollRecyclerView) findViewById(R.id.comp_club_list);
        comp_club_list.setIndexBarTransparentValue((float) 0.2);
      //  comp_club_list.setIndexBarTextColor("#6b6a6a");

        segmented_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = segmented_check.getCheckedRadioButtonId();
                View radioButton = segmented_check.findViewById(radioButtonID);
                int idRadio = segmented_check.indexOfChild(radioButton);
                switch (idRadio) {
                    case 0:
                        setData(0);
                        break;
                    case 1:
                        setData(1);
                        break;
                }
            }
        });
        setData(0);
    }

    public void Back(View view) {
        finish();
    }

    private void setData(int type) {
      /*  ArrayList<String> dataStore = ToolUtils.getArrayOfCompAndClub(getApplicationContext());
        dataStore.clear();
        ToolUtils.setCompAndClub(getApplicationContext(),  dataStore);*/
        Log.e("dataStore", "" +ToolUtils.getArrayOfCompAndClub(getApplicationContext()).size());
        recycleViewCompetitionsSettingsSelect = new RecycleViewCompetitionsSettingsSelect(this, clubCompetitionList.getClubs(), clubCompetitionList.getCompitetions(), type, ToolUtils.getArrayOfCompAndClub(getApplicationContext()));
        comp_club_list.setAdapter(recycleViewCompetitionsSettingsSelect);
    }
}
