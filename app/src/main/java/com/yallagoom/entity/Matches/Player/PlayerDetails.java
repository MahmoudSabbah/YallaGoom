package com.yallagoom.entity.Matches.Player;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/29/2018.
 */

public class PlayerDetails {
    private ArrayList<PlayerList> player_main_data;
    private Player_incident_data incident_data;
/*
    private player_main_data_season season;
*/

    public ArrayList<PlayerList> getPlayer_main_data() {
        return player_main_data;
    }

    public void setPlayer_main_data(ArrayList<PlayerList> player_main_data) {
        this.player_main_data = player_main_data;
    }

    public Player_incident_data getIncident_data() {
        return incident_data;
    }

    public void setIncident_data(Player_incident_data incident_data) {
        this.incident_data = incident_data;
    }

  /*  public player_main_data_season getSeason() {
        return season;
    }

    public void setSeason(player_main_data_season season) {
        this.season = season;
    }*/
}
