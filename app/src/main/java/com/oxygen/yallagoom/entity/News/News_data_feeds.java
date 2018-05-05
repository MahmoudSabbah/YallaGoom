package com.oxygen.yallagoom.entity.News;

/**
 * Created by Mahmoud Sabbah on 3/22/2018.
 */

public class News_data_feeds {
/*
    private int id;
*/
    private String created_at;
    private String id_str;
    private String text;
    private boolean truncated;
    private News_entities entities;
    private News_extended_entities extended_entities;

  /*  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
*/
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public News_entities getEntities() {
        return entities;
    }

    public void setEntities(News_entities entities) {
        this.entities = entities;
    }

    public News_extended_entities getExtended_entities() {
        return extended_entities;
    }

    public void setExtended_entities(News_extended_entities extended_entities) {
        this.extended_entities = extended_entities;
    }
}
