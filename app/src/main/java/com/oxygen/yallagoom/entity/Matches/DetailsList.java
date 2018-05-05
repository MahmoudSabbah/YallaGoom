package com.oxygen.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/17/2018.
 */

public class DetailsList {
    private int id;
    private int details_constant_id;
    private String details_value;
    private int match_id;
    private DetailsTitle details_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetails_constant_id() {
        return details_constant_id;
    }

    public void setDetails_constant_id(int details_constant_id) {
        this.details_constant_id = details_constant_id;
    }

    public String getDetails_value() {
        return details_value;
    }

    public void setDetails_value(String details_value) {
        this.details_value = details_value;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public DetailsTitle getDetails() {
        return details_title;
    }

    public void setDetails(DetailsTitle details_title) {
        this.details_title = details_title;
    }
}
