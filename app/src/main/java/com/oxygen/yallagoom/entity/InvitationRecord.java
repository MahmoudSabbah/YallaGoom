package com.oxygen.yallagoom.entity;

import java.io.Serializable;

/**
 * Created by Mahmoud Sabbah on 4/7/2018.
 */

public class InvitationRecord implements  Serializable{
    private int id ;
    private int invitee_id ;
    private int inviter_id ;
    private int event_id ;
    private int is_temp ;
    private String invitation_status ;
    private String notes ;
    private User user_invitee_data;

    public User getUser_invitee_data() {
        return user_invitee_data;
    }

    public void setUser_invitee_data(User user_invitee_data) {
        this.user_invitee_data = user_invitee_data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvitee_id() {
        return invitee_id;
    }

    public void setInvitee_id(int invitee_id) {
        this.invitee_id = invitee_id;
    }

    public int getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(int inviter_id) {
        this.inviter_id = inviter_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getIs_temp() {
        return is_temp;
    }

    public void setIs_temp(int is_temp) {
        this.is_temp = is_temp;
    }

    public String getInvitation_status() {
        return invitation_status;
    }

    public void setInvitation_status(String invitation_status) {
        this.invitation_status = invitation_status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
