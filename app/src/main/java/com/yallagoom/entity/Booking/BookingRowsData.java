package com.yallagoom.entity.Booking;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/1/2018.
 */

public class BookingRowsData {
    private int id;
    private int ticket_id;
    private String title;
    private double price;
    private ArrayList<OtherAttributesValues> other_attributes_values;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<OtherAttributesValues> getOther_attributes_values() {
        return other_attributes_values;
    }

    public void setOther_attributes_values(ArrayList<OtherAttributesValues> other_attributes_values) {
        this.other_attributes_values = other_attributes_values;
    }
}
