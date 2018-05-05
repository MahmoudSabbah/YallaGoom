package com.oxygen.yallagoom.entity.Booking;

/**
 * Created by Mahmoud Sabbah on 3/13/2018.
 */

public class TicketsDates {
    private int id;
    private int ticket_id;
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
