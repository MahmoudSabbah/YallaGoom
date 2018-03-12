package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/11/2018.
 */

public class MatchesList {
    private int match_id;
    private String scheduled;
    private String start_time_tbd;
    private String status;
    private String season_name;
    private String tournament_name;
    private int season_id;
    private int tournament_id;
    private int competitors_1_id;
    private int competitors_2_id;
    private String competitors_1_name;
    private String competitors_2_name;
    private String competitors_1_country;
    private String competitors_2_country;
    private String competitors_1_qualifier;
    private String competitors_2_qualifier;
    private MatchResult match_result;
    private Competitors competitors_1;
    private Competitors competitors_2;
    private Season season;
    private Tournament tournament;
    private Venue venue;

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Competitors getCompetitors_2() {
        return competitors_2;
    }

    public void setCompetitors_2(Competitors competitors_2) {
        this.competitors_2 = competitors_2;
    }

    public Competitors getCompetitors_1() {
        return competitors_1;
    }

    public void setCompetitors_1(Competitors competitors_1) {
        this.competitors_1 = competitors_1;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getStart_time_tbd() {
        return start_time_tbd;
    }

    public void setStart_time_tbd(String start_time_tbd) {
        this.start_time_tbd = start_time_tbd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getCompetitors_1_id() {
        return competitors_1_id;
    }

    public void setCompetitors_1_id(int competitors_1_id) {
        this.competitors_1_id = competitors_1_id;
    }

    public int getCompetitors_2_id() {
        return competitors_2_id;
    }

    public void setCompetitors_2_id(int competitors_2_id) {
        this.competitors_2_id = competitors_2_id;
    }

    public String getCompetitors_1_name() {
        return competitors_1_name;
    }

    public void setCompetitors_1_name(String competitors_1_name) {
        this.competitors_1_name = competitors_1_name;
    }

    public String getCompetitors_2_name() {
        return competitors_2_name;
    }

    public void setCompetitors_2_name(String competitors_2_name) {
        this.competitors_2_name = competitors_2_name;
    }

    public String getCompetitors_1_country() {
        return competitors_1_country;
    }

    public void setCompetitors_1_country(String competitors_1_country) {
        this.competitors_1_country = competitors_1_country;
    }

    public String getCompetitors_2_country() {
        return competitors_2_country;
    }

    public void setCompetitors_2_country(String competitors_2_country) {
        this.competitors_2_country = competitors_2_country;
    }

    public String getCompetitors_1_qualifier() {
        return competitors_1_qualifier;
    }

    public void setCompetitors_1_qualifier(String competitors_1_qualifier) {
        this.competitors_1_qualifier = competitors_1_qualifier;
    }

    public String getCompetitors_2_qualifier() {
        return competitors_2_qualifier;
    }

    public void setCompetitors_2_qualifier(String competitors_2_qualifier) {
        this.competitors_2_qualifier = competitors_2_qualifier;
    }

    public MatchResult getMatch_result() {
        return match_result;
    }

    public void setMatch_result(MatchResult match_result) {
        this.match_result = match_result;
    }
}
