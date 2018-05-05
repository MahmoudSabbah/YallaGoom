package com.oxygen.yallagoom.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/11/2018.
 */

public class Player implements Serializable {
    private ArrayList<Player.PlayerList> data;

    public ArrayList<PlayerList> getData() {
        return data;
    }

    public void setData(ArrayList<PlayerList> data) {
        this.data = data;
    }

    public class PlayerList implements Serializable {

        private int id;
        private String email;
        private String mobile;
        private String facebook;
        private String twitter;
        private int device_id;
        private int country_id;
        private String created_at;
        private String updated_at;
        private String first_name;
        private String last_name;
        private String birth_date;
        private String gender;
        private String img_url;
        private int checkSelectGroup = 0;
        private boolean invited = false;
        private ArrayList<PlayerSport> get_user_sports_list;
        private UserCountry get_country_data;

        public UserCountry getGet_country_data() {
            return get_country_data;
        }

        public void setGet_country_data(UserCountry get_country_data) {
            this.get_country_data = get_country_data;
        }

        public boolean isInvited() {
            return invited;
        }

        public void setInvited(boolean invited) {
            this.invited = invited;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getCheckSelectGroup() {
            return checkSelectGroup;
        }

        public void setCheckSelectGroup(int checkSelectGroup) {
            this.checkSelectGroup = checkSelectGroup;
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

        public int getDevice_id() {
            return device_id;
        }

        public void setDevice_id(int device_id) {
            this.device_id = device_id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
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

        public ArrayList<PlayerSport> getGet_user_sports_list() {
            return get_user_sports_list;
        }

        public void setGet_user_sports_list(ArrayList<PlayerSport> get_user_sports_list) {
            this.get_user_sports_list = get_user_sports_list;
        }

        public class SportList implements Serializable {
            private int id;
            private int user_id;
            private int sport_id;
            private int rate;
            private String notes;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getSport_id() {
                return sport_id;
            }

            public void setSport_id(int sport_id) {
                this.sport_id = sport_id;
            }

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }
        }
    }
}
