package com.oxygen.yallagoom.entity;

import java.io.Serializable;

public class PlayerSport implements Serializable{
    private SportObject get_sports_data;

    public SportObject getGet_sports_data() {
        return get_sports_data;
    }

    public void setGet_sports_data(SportObject get_sports_data) {
        this.get_sports_data = get_sports_data;
    }
}
