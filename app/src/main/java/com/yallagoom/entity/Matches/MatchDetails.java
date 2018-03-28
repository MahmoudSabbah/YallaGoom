package com.yallagoom.entity.Matches;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/18/2018.
 */

public class MatchDetails {
    private int id;
    private int competitions_id;
    private int seasons_id;
    private int stages_id;
    private String start_date;
    private String status_name;
    private String status_type;
    private ArrayList<ParticipantsList> participants_list;
    private ArrayList<ResultsList> results_list;
    private ArrayList<DetailsList> details;
    private ArrayList<StatsList> stats_list;
    private ArrayList<Incident> incidents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetitions_id() {
        return competitions_id;
    }

    public void setCompetitions_id(int competitions_id) {
        this.competitions_id = competitions_id;
    }

    public int getSeasons_id() {
        return seasons_id;
    }

    public void setSeasons_id(int seasons_id) {
        this.seasons_id = seasons_id;
    }

    public int getStages_id() {
        return stages_id;
    }

    public void setStages_id(int stages_id) {
        this.stages_id = stages_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public ArrayList<ParticipantsList> getParticipants_list() {
        return participants_list;
    }

    public void setParticipants_list(ArrayList<ParticipantsList> participants_list) {
        this.participants_list = participants_list;
    }

    public ArrayList<ResultsList> getResults_list() {
        return results_list;
    }

    public void setResults_list(ArrayList<ResultsList> results_list) {
        this.results_list = results_list;
    }

    public ArrayList<DetailsList> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsList> details) {
        this.details = details;
    }

    public ArrayList<StatsList> getStats_list() {
        return stats_list;
    }

    public void setStats_list(ArrayList<StatsList> stats_list) {
        this.stats_list = stats_list;
    }

    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents = incidents;
    }
}
