package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/12/2018.
 */

public class Season {
    private int season_id;
    private String season_name;
    private String season_start_date;
    private String season_end_date;
    private String season_year;

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public String getSeason_start_date() {
        return season_start_date;
    }

    public void setSeason_start_date(String season_start_date) {
        this.season_start_date = season_start_date;
    }

    public String getSeason_end_date() {
        return season_end_date;
    }

    public void setSeason_end_date(String season_end_date) {
        this.season_end_date = season_end_date;
    }

    public String getSeason_year() {
        return season_year;
    }

    public void setSeason_year(String season_year) {
        this.season_year = season_year;
    }
}
