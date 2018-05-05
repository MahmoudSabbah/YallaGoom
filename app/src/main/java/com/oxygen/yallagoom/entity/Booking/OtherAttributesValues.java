package com.oxygen.yallagoom.entity.Booking;

/**
 * Created by Mahmoud Sabbah on 3/1/2018.
 */

public class OtherAttributesValues {
    private int id;
    private int attribute_id;
    private int ticket_degree_id;
    private String value;
    private AttributeTitle attribute_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(int attribute_id) {
        this.attribute_id = attribute_id;
    }

    public int getTicket_degree_id() {
        return ticket_degree_id;
    }

    public void setTicket_degree_id(int ticket_degree_id) {
        this.ticket_degree_id = ticket_degree_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributeTitle getAttribute_title() {
        return attribute_title;
    }

    public void setAttribute_title(AttributeTitle attribute_title) {
        this.attribute_title = attribute_title;
    }
}
