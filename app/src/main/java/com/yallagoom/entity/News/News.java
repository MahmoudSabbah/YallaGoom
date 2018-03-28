package com.yallagoom.entity.News;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/22/2018.
 */

public class News {
    private String channel;
    private ArrayList<News_data_feeds> data_feeds;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public ArrayList<News_data_feeds> getData_feeds() {
        return data_feeds;
    }

    public void setData_feeds(ArrayList<News_data_feeds> data_feeds) {
        this.data_feeds = data_feeds;
    }
}
