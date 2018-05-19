package com.oxygen.yallagoom.entity;

import com.oxygen.yallagoom.entity.TicketClasses.CategoryTicketsListCount;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryDetails implements Serializable {
    private int id;
    private String CategoryName;
    private String CategoryDescription;
    private String created_at;
    private String updated_at;
    private int vis=0;
    private ArrayList<CategoryTicketsListCount> tickets_list_count;

    public ArrayList<CategoryTicketsListCount> getTickets_list_count() {
        return tickets_list_count;
    }

    public void setTickets_list_count(ArrayList<CategoryTicketsListCount> tickets_list_count) {
        this.tickets_list_count = tickets_list_count;
    }
    public int getVis() {
        return vis;
    }

    public void setVis(int vis) {
        this.vis = vis;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
