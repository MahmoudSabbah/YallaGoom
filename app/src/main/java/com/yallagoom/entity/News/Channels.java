package com.yallagoom.entity.News;

/**
 * Created by Mahmoud Sabbah on 3/26/2018.
 */

public class Channels {
    private int id;
    private int area_id;
    private String ChannelName;
    private String url;
    private String cat_filter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCat_filter() {
        return cat_filter;
    }

    public void setCat_filter(String cat_filter) {
        this.cat_filter = cat_filter;
    }
}
