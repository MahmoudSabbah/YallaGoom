package com.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/17/2018.
 */

public class ParticipantsList {
    private int id;
    private int match_id;
    private int counter;
    private int participant_id;
    private  ParticipantsData participants_data;

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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(int participant_id) {
        this.participant_id = participant_id;
    }

    public ParticipantsData getParticipants_data() {
        return participants_data;
    }

    public void setParticipants_data(ParticipantsData participants_data) {
        this.participants_data = participants_data;
    }
}
