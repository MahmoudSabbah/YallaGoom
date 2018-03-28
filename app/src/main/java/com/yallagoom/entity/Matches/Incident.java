package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/18/2018.
 */

public class Incident {
    private int id;
    private int match_id;
    private String id_internal;
    private String incident_id;
    private String incident_name;
    private String participant_id;
    private String participant_name;
    private String participant_slug;
    private String subparticipant_id;
    private String subparticipant_name;
    private String subparticipant_slug;
    private String event_status_id;
    private String event_status_name;
    private String event_time;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getId_internal() {
        return id_internal;
    }

    public void setId_internal(String id_internal) {
        this.id_internal = id_internal;
    }

    public String getIncident_id() {
        return incident_id;
    }

    public void setIncident_id(String incident_id) {
        this.incident_id = incident_id;
    }

    public String getIncident_name() {
        return incident_name;
    }

    public void setIncident_name(String incident_name) {
        this.incident_name = incident_name;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public String getParticipant_name() {
        return participant_name;
    }

    public void setParticipant_name(String participant_name) {
        this.participant_name = participant_name;
    }

    public String getParticipant_slug() {
        return participant_slug;
    }

    public void setParticipant_slug(String participant_slug) {
        this.participant_slug = participant_slug;
    }

    public String getSubparticipant_id() {
        return subparticipant_id;
    }

    public void setSubparticipant_id(String subparticipant_id) {
        this.subparticipant_id = subparticipant_id;
    }

    public String getSubparticipant_name() {
        return subparticipant_name;
    }

    public void setSubparticipant_name(String subparticipant_name) {
        this.subparticipant_name = subparticipant_name;
    }

    public String getSubparticipant_slug() {
        return subparticipant_slug;
    }

    public void setSubparticipant_slug(String subparticipant_slug) {
        this.subparticipant_slug = subparticipant_slug;
    }

    public String getEvent_status_id() {
        return event_status_id;
    }

    public void setEvent_status_id(String event_status_id) {
        this.event_status_id = event_status_id;
    }

    public String getEvent_status_name() {
        return event_status_name;
    }

    public void setEvent_status_name(String event_status_name) {
        this.event_status_name = event_status_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
