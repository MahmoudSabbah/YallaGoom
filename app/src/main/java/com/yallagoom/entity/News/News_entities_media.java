package com.yallagoom.entity.News;

/**
 * Created by Mahmoud Sabbah on 3/22/2018.
 */

public class News_entities_media {
/*
    private int id;
*/
    private String id_str;
    private String media_url;
    private String media_url_https;
    private String url;
    private String expanded_url;
    private String type;
    private News_extended_entities_video_info video_info;
    private int[] indices;


   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_url_https() {
        return media_url_https;
    }

    public void setMedia_url_https(String media_url_https) {
        this.media_url_https = media_url_https;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public void setExpanded_url(String expanded_url) {
        this.expanded_url = expanded_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public News_extended_entities_video_info getVideo_info() {
        return video_info;
    }

    public void setVideo_info(News_extended_entities_video_info video_info) {
        this.video_info = video_info;
    }
}
