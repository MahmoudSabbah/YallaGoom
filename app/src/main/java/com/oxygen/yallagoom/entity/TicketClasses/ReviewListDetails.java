package com.oxygen.yallagoom.entity.TicketClasses;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Mahmoud Sabbah on 2/28/2018.
 */

public class ReviewListDetails extends RealmObject implements Serializable {
    private int id;
    private int user_id;
    private int ticket_id;
    private double rate;
    private String txt;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
