package com.oxygen.yallagoom.entity.TicketClasses;

import java.io.Serializable;

import io.realm.RealmObject;

public class LikesContribution extends RealmObject implements Serializable {
    private int id;
    private int ticket_id;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
