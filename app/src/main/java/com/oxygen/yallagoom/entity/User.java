package com.oxygen.yallagoom.entity;

import java.io.Serializable;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class User implements Serializable {
    private int id;
    private String email;
    private String mobile;
    private String created_at;
    private String updated_at;
    private String first_name;
    private String last_name;
    private int country_id;
    private String birth_date;
    private String gender;
    private String token;
    private String name;
    private String img_url;
    private String status;
    private String firebase_auth_user_id;
    private String facebook;
    private String twitter;
    private String device_id;
    private CountryDetails get_country_data;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getFirebase_auth_user_id() {
        return firebase_auth_user_id;
    }

    public void setFirebase_auth_user_id(String firebase_auth_user_id) {
        this.firebase_auth_user_id = firebase_auth_user_id;
    }

    public CountryDetails getGet_country_data() {
        return get_country_data;
    }

    public void setGet_country_data(CountryDetails get_country_data) {
        this.get_country_data = get_country_data;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
