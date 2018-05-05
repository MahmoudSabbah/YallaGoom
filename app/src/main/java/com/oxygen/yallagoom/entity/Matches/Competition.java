package com.oxygen.yallagoom.entity.Matches;

/**
 * Created by Mahmoud Sabbah on 3/17/2018.
 */

public class Competition {
    private int id;
    private String name;
    private String short_name;
    private String mini_name;
    private String area_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getMini_name() {
        return mini_name;
    }

    public void setMini_name(String mini_name) {
        this.mini_name = mini_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
