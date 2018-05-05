package com.oxygen.yallagoom.entity.News;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/22/2018.
 */

public class News_entities {
    private ArrayList<News_entities_hashtags> hashtags;
    private ArrayList<News_entities_media> media;
    private ArrayList<News_entities_urls> urls;

    public ArrayList<News_entities_hashtags> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<News_entities_hashtags> hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<News_entities_media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<News_entities_media> media) {
        this.media = media;
    }

    public ArrayList<News_entities_urls> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<News_entities_urls> urls) {
        this.urls = urls;
    }
}
