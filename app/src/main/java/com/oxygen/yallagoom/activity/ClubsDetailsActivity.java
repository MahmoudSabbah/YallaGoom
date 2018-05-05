package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.Score.ClubsTab.RecycleViewClubsDetailsMatches;
import com.oxygen.yallagoom.adapter.Score.ClubsTab.RecycleViewClubsDetailsOverview;
import com.oxygen.yallagoom.adapter.Score.ClubsTab.RecycleViewClubsOverviewAction;
import com.oxygen.yallagoom.entity.Matches.ClubsTab.ClubsListDetails;
import com.oxygen.yallagoom.entity.Matches.MatchesList;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.Date;

public class ClubsDetailsActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private RecyclerView overview_list;
    private ClubsListDetails club_details;
    private SegmentedGroup segmented;
    private LinearLayout overview_layout;
    private RelativeLayout matches_layout;
    private RadioButton overview;
    private RecyclerView matches_list;
    private RecyclerView action_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_details);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(ClubsDetailsActivity.this);
        ToolUtils.setLightStatusBar(parent, ClubsDetailsActivity.this);
        club_details = new Gson().fromJson(getIntent().getExtras().getString("Club_details"), ClubsListDetails.class);

        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.GONE);
        name_header.setText(getIntent().getExtras().getString("Club_name"));
        overview_layout = (LinearLayout) findViewById(R.id.overview_layout);
        matches_layout = (RelativeLayout) findViewById(R.id.matches_layout);
        overview_list = (RecyclerView) findViewById(R.id.overview_list);
        overview_list.setNestedScrollingEnabled(false);
        action_list = (RecyclerView) findViewById(R.id.action_list);
        action_list.setNestedScrollingEnabled(false);
        matches_list = (RecyclerView) findViewById(R.id.matches_list);
        overview = (RadioButton) findViewById(R.id.overview);
        overview.setChecked(true);
        segmented = (SegmentedGroup) findViewById(R.id.segmented_);
        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = segmented.getCheckedRadioButtonId();
                View radioButton = segmented.findViewById(radioButtonID);
                int idx = segmented.indexOfChild(radioButton);
                switch (idx) {
                    case 0:
                        overview_layout.setVisibility(View.VISIBLE);
                        matches_layout.setVisibility(View.GONE);
                        break;
                    case 1:
                        matches_layout.setVisibility(View.VISIBLE);
                        overview_layout.setVisibility(View.GONE);

                        break;

                }
            }
        });

        RecycleViewClubsDetailsMatches recycleViewClubsDetailsMatches = new RecycleViewClubsDetailsMatches(this
                , club_details.getAll_matches_in_last_season());
        matches_list.setAdapter(recycleViewClubsDetailsMatches);
        ArrayList<MatchesList> matchesLists = new ArrayList<>();
        for (int i = club_details.getAll_matches_in_last_season().size()-1; i >-1 ; i--) {
            if (ToolUtils.converStringToDate(club_details.getAll_matches_in_last_season().get(i).getStart_date(), Constant.yyyy_MM_dd__HH_mm).before(
                    new Date()
            )) {
                if (matchesLists.size() > 0) {
                    matchesLists.set(0, club_details.getAll_matches_in_last_season().get(i));
                } else {
                    matchesLists.add(club_details.getAll_matches_in_last_season().get(i));

                }
            } else {
                if (matchesLists.size() > 1) {
                    matchesLists.set(1, club_details.getAll_matches_in_last_season().get(i));
                } else {
                    matchesLists.add(club_details.getAll_matches_in_last_season().get(i));

                }
            }

        }
        RecycleViewClubsDetailsOverview recycleViewClubsDetailsOverview = new RecycleViewClubsDetailsOverview(this
                , matchesLists);
        overview_list.setAdapter(recycleViewClubsDetailsOverview);
        RecycleViewClubsOverviewAction recycleViewClubsOverviewAction = new RecycleViewClubsOverviewAction(this,
                club_details.getAll_stats_values());
        action_list.setAdapter(recycleViewClubsOverviewAction);

    }

    public void Back(View view) {
        finish();
    }

}
