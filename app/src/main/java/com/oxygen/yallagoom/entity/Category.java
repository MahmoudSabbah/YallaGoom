package com.oxygen.yallagoom.entity;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/10/2018.
 */

public class Category {
    private ArrayList<CategoryList> data;


    public ArrayList<CategoryList> getData() {
        return data;
    }

    public void setData(ArrayList<CategoryList> data) {
        this.data = data;
    }

    public class CategoryList {
        private int id;
        private String CategoryName;
        private String CategoryDescription;
        private int vis=0;

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
    }


}
