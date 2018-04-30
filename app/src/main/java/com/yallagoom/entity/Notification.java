package com.yallagoom.entity;

/**
 * Created by Mahmoud Sabbah on 4/7/2018.
 */

public class Notification {
    private String body;
    private String created_at;
    private int notify_user_id;
    private User db_sender;
    private User db_reciever;

    public int getNotify_user_id() {
        return notify_user_id;
    }

    public void setNotify_user_id(int notify_user_id) {
        this.notify_user_id = notify_user_id;
    }

    public User getDb_sender() {
        return db_sender;
    }

    public void setDb_sender(User db_sender) {
        this.db_sender = db_sender;
    }

    public User getDb_reciever() {
        return db_reciever;
    }

    public void setDb_reciever(User db_reciever) {
        this.db_reciever = db_reciever;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
