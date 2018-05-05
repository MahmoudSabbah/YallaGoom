package com.oxygen.yallagoom.entity;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */

public class CategorySportsSearch {
    public String name;
    public int sport_id;
    public int vis;
    public int id;
    public CategorySportsSearch(String name , int vis, int id) {
        super();
        this.name = name;
        this.vis = vis;
        this.id = id;
    }

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

    public int getVis() {
        return vis;
    }

    public void setVis(int vis) {
        this.vis = vis;
    }
}
