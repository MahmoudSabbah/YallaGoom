package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/12/2018.
 */

public class Tournament {
    private int tournament_id;
    private String tournament_name;
    private String tournament_category;
    private String tournament_category_name;
    private String tournament_category_country_code;

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public String getTournament_category() {
        return tournament_category;
    }

    public void setTournament_category(String tournament_category) {
        this.tournament_category = tournament_category;
    }

    public String getTournament_category_name() {
        return tournament_category_name;
    }

    public void setTournament_category_name(String tournament_category_name) {
        this.tournament_category_name = tournament_category_name;
    }

    public String getTournament_category_country_code() {
        return tournament_category_country_code;
    }

    public void setTournament_category_country_code(String tournament_category_country_code) {
        this.tournament_category_country_code = tournament_category_country_code;
    }
}
