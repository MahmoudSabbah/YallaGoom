package com.oxygen.yallagoom.entity.TicketClasses;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Mahmoud Sabbah on 2/26/2018.
 */

public class TicketDetails extends RealmObject implements Serializable {
    private TicketInfo ticket_info;
    private ReviewList review_list;

    public TicketInfo getTicket_info() {
        return ticket_info;
    }

    public void setTicket_info(TicketInfo ticket_info) {
        this.ticket_info = ticket_info;
    }

    public ReviewList getReview_list() {
        return review_list;
    }

    public void setReview_list(ReviewList review_list) {
        this.review_list = review_list;
    }




}
