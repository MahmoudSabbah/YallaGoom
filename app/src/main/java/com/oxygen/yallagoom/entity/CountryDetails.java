package com.oxygen.yallagoom.entity;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Mahmoud Sabbah on 4/1/2018.
 */

public class CountryDetails extends RealmObject implements Serializable{
    private int id;
    private String name_en;
    private String name_ar;
    private String code;
    private String phone_code;
    private String flag;
    private String code_3;
    private String img_url;
    private int vis=0;

    public String getCode_3() {
        return code_3;
    }

    public void setCode_3(String code_3) {
        this.code_3 = code_3;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
