package com.oxygen.yallagoom.entity.TicketClasses;

import java.io.Serializable;

import io.realm.RealmObject;

public class LikesCount  extends RealmObject implements Serializable {
    private int ticket_id;
    private int likes_count;

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }
}
