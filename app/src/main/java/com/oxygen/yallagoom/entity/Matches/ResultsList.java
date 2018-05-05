package com.oxygen.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/17/2018.
 */

public class ResultsList {
    private int id;
    private int result_constant_id;
    private String result_value;
    private int participants_id;
    private int match_id;
    private ResultTitle result_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult_constant_id() {
        return result_constant_id;
    }

    public void setResult_constant_id(int result_constant_id) {
        this.result_constant_id = result_constant_id;
    }

    public String getResult_value() {
        return result_value;
    }

    public void setResult_value(String result_value) {
        this.result_value = result_value;
    }

    public int getParticipants_id() {
        return participants_id;
    }

    public void setParticipants_id(int participants_id) {
        this.participants_id = participants_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public ResultTitle getResult_title() {
        return result_title;
    }

    public void setResult_title(ResultTitle result_title) {
        this.result_title = result_title;
    }
}
