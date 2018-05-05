package com.oxygen.yallagoom.entity.Matches.Player;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/29/2018.
 */

public class Player_incident_data {
    private int no_of_matches;
    private ArrayList<Player_incident_data_count> incident_count;

    public int getNo_of_matches() {
        return no_of_matches;
    }

    public void setNo_of_matches(int no_of_matches) {
        this.no_of_matches = no_of_matches;
    }

    public ArrayList<Player_incident_data_count> getIncident_count() {
        return incident_count;
    }

    public void setIncident_count(ArrayList<Player_incident_data_count> incident_count) {
        this.incident_count = incident_count;
    }
}
