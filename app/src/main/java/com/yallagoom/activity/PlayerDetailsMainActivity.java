package com.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewPlayerDetailsAction;
import com.yallagoom.entity.Matches.Player.PlayerDetails;
import com.yallagoom.entity.Matches.Player.PlayerList;
import com.yallagoom.entity.Matches.Player.Player_incident_data;
import com.yallagoom.entity.Matches.Player.Player_incident_data_count;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerDetailsMainActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private TextView position_value;
    private TextView age_value;
    private TextView country_value;
    private TextView weight_value;
    private TextView height_value;
    private RecyclerView action_list;
    private TextView yellow_card_icon_value;
    private TextView red_card_icon_value;
    private TextView matches_cap_value;
    private TextView goal_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details_main);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(PlayerDetailsMainActivity.this);
        ToolUtils.setLightStatusBar(parent, PlayerDetailsMainActivity.this);
        PlayerDetails playerInfo = new Gson().fromJson(getIntent().getExtras().getString("Player_details"), PlayerDetails.class);

        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.GONE);
        name_header.setText(playerInfo.getPlayer_main_data().get(0).getName());
        position_value = (TextView) findViewById(R.id.position_value);
        position_value.setText(playerInfo.getPlayer_main_data().get(0).getPosition_name());
        age_value = (TextView) findViewById(R.id.age_value);
        age_value.setText(ToolUtils.getAge(playerInfo.getPlayer_main_data().get(0).getBirthdate()) + "");
        country_value = (TextView) findViewById(R.id.country_value);
        country_value.setText(playerInfo.getPlayer_main_data().get(0).getArea_name());
        weight_value = (TextView) findViewById(R.id.weight_value);
        if (!playerInfo.getPlayer_main_data().get(0).getWeight().equalsIgnoreCase("")) {
            weight_value.setText(playerInfo.getPlayer_main_data().get(0).getWeight() + " " + getString(R.string.kg));
        }
        height_value = (TextView) findViewById(R.id.height_value);
        if (!playerInfo.getPlayer_main_data().get(0).getHeight().equalsIgnoreCase("")) {
            height_value.setText(playerInfo.getPlayer_main_data().get(0).getHeight() + " " + getString(R.string.cm));
        }
        matches_cap_value = (TextView) findViewById(R.id.matches_cap_value);
        matches_cap_value.setText(playerInfo.getIncident_data().getNo_of_matches() + "");
        goal_value = (TextView) findViewById(R.id.goal_value);
        yellow_card_icon_value = (TextView) findViewById(R.id.yellow_card_icon_value);
        red_card_icon_value = (TextView) findViewById(R.id.red_card_icon_value);
        action_list = (RecyclerView) findViewById(R.id.action_list);
        action_list.setNestedScrollingEnabled(false);
        action_list.setFocusable(false);

        if (playerInfo.getIncident_data().getIncident_count()!=null) {
            for (Player_incident_data_count d : playerInfo.getIncident_data().getIncident_count()) {
                if (d.getIncident_name().equalsIgnoreCase("Yellow card")) {
                    yellow_card_icon_value.setText(d.getCount() + "");
                } else if (d.getIncident_name().equalsIgnoreCase("Red card")) {
                    red_card_icon_value.setText(d.getCount() + "");
                } else if (d.getIncident_name().equalsIgnoreCase("Goal")) {
                    goal_value.setText(d.getCount() + "");
                }
            }
            RecycleViewPlayerDetailsAction recycleViewPlayerDetailsAction = new RecycleViewPlayerDetailsAction(PlayerDetailsMainActivity.this, playerInfo.getIncident_data().getIncident_count());
            action_list.setAdapter(recycleViewPlayerDetailsAction);
          /*  List<Player_incident_data_count> dataPointsCalledJohn =
                    playerInfo.getIncident_data().getIncident_count().stream()
                            .filter(p -> p.getIncident_name().equals(("john")))
                            .collect(Collectors.toList());*/

        }


     /*   for (int i=0;i<playerInfo.getIncident_data().getIncident_count().size();i++){
            if (playerInfo.getIncident_data().getIncident_count().get(i).getIncident_name().equalsIgnoreCase("Yellow card")){
                yellow_card_icon_value.setText(playerInfo.getIncident_data().getIncident_count().get(i).getCount()+"");
            }else if (playerInfo.getIncident_data().getIncident_count().get(i).getIncident_name().equalsIgnoreCase("Red card")){
                red_card_icon_value.setText(playerInfo.getIncident_data().getIncident_count().get(i).getCount()+"");
            }else if (playerInfo.getIncident_data().getIncident_count().get(i).getIncident_name().equalsIgnoreCase("Goal")){
                goal_value.setText(playerInfo.getIncident_data().getIncident_count().get(i).getCount()+"");
            }
        }*/


//Fill up myList with your Data Points


    }
  /*  Predicate<Player_incident_data_count> nameEqualsTo(final String name) {
        return new Predicate<Player_incident_data_count>() {

            @Override
            public boolean test(Player_incident_data_count player_incident_data_count) {
                return false;
            }

            public boolean apply(Player_incident_data_count dataPoint) {
                return dataPoint.getIncident_name().equals(name);
            }
        };
    }*/

    public void Back(View view) {
        finish();
    }
}
  /*  List<Player_incident_data_count> dataPointsCalledJohn =
            myList.stream()
                    .filter(p -> p.getIncident_name().equals(("john")))
                    .collect(Collectors.toList());*/
