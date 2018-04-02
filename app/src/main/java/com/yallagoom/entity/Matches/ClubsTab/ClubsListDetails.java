package com.yallagoom.entity.Matches.ClubsTab;

import com.yallagoom.entity.Matches.MatchesList;
import com.yallagoom.entity.Matches.Player.Player_incident_data_count;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/30/2018.
 */

public class ClubsListDetails {
    private ArrayList<Player_incident_data_count> all_stats_values;
    private ArrayList<MatchesList> all_matches_in_last_season;

    public ArrayList<Player_incident_data_count> getAll_stats_values() {
        return all_stats_values;
    }

    public void setAll_stats_values(ArrayList<Player_incident_data_count> all_stats_values) {
        this.all_stats_values = all_stats_values;
    }

    public ArrayList<MatchesList> getAll_matches_in_last_season() {
        return all_matches_in_last_season;
    }

    public void setAll_matches_in_last_season(ArrayList<MatchesList> all_matches_in_last_season) {
        this.all_matches_in_last_season = all_matches_in_last_season;
    }
}
