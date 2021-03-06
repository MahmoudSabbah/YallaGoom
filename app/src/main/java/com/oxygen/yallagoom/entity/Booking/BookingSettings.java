package com.oxygen.yallagoom.entity.Booking;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/1/2018.
 */

public class BookingSettings {
    private ArrayList<AttributeList> attribute_list;
    private ArrayList<TicketsDates> tickets_dates;
    private BookingRows rows_data;

    public ArrayList<AttributeList> getAttribute_list() {
        return attribute_list;
    }

    public void setAttribute_list(ArrayList<AttributeList> attribute_list) {
        this.attribute_list = attribute_list;
    }

    public BookingRows getRows_data() {
        return rows_data;
    }

    public void setRows_data(BookingRows rows_data) {
        this.rows_data = rows_data;
    }

    public ArrayList<TicketsDates> getTickets_dates() {
        return tickets_dates;
    }

    public void setTickets_dates(ArrayList<TicketsDates> tickets_dates) {
        this.tickets_dates = tickets_dates;
    }
}
