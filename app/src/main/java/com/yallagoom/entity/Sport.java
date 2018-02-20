package com.yallagoom.entity;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class Sport {
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        private int sport_id;
        private String name_en;
        private String name_ar;
        private String code;
        private String icon;
        private int vis=0;



        public int getVis() {
            return vis;
        }

        public void setVis(int vis) {
            this.vis = vis;
        }

        public int getSport_id() {
            return sport_id;
        }

        public void setSport_id(int sport_id) {
            this.sport_id = sport_id;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

}
