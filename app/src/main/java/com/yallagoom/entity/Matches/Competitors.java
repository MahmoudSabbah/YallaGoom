package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/12/2018.
 */

public class Competitors {
    private int competitors_id;
    private String competitors_name;
    private String competitors_country;
    private String competitors_country_code;
    private String competitors_abbreviation;

    public int getCompetitors_id() {
        return competitors_id;
    }

    public void setCompetitors_id(int competitors_id) {
        this.competitors_id = competitors_id;
    }

    public String getCompetitors_name() {
        return competitors_name;
    }

    public void setCompetitors_name(String competitors_name) {
        this.competitors_name = competitors_name;
    }

    public String getCompetitors_country() {
        return competitors_country;
    }

    public void setCompetitors_country(String competitors_country) {
        this.competitors_country = competitors_country;
    }

    public String getCompetitors_country_code() {
        return competitors_country_code;
    }

    public void setCompetitors_country_code(String competitors_country_code) {
        this.competitors_country_code = competitors_country_code;
    }

    public String getCompetitors_abbreviation() {
        return competitors_abbreviation;
    }

    public void setCompetitors_abbreviation(String competitors_abbreviation) {
        this.competitors_abbreviation = competitors_abbreviation;
    }
}
