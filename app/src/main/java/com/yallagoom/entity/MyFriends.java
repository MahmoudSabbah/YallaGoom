package com.yallagoom.entity;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/19/2018.
 */

public class MyFriends {
    private ArrayList<FriendsList> data;

    public ArrayList<FriendsList> getData() {
        return data;
    }

    public void setData(ArrayList<FriendsList> data) {
        this.data = data;
    }

    public class FriendsList {
        private int id;
        private int user_id;
        private int group_id;
        private int target_user_id;
        private String status;
        private User user;

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

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getTarget_user_id() {
            return target_user_id;
        }

        public void setTarget_user_id(int target_user_id) {
            this.target_user_id = target_user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public class User {
            private int id;
            private int country_id;
            private String email;
            private String mobile;
            private String facebook;
            private String twitter;
            private String device_id;
            private String first_name;
            private String last_name;
            private String birth_date;
            private String gender;
            private CountryData get_country_data;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCountry_id() {
                return country_id;
            }

            public void setCountry_id(int country_id) {
                this.country_id = country_id;
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

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
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

            public CountryData getGet_country_data() {
                return get_country_data;
            }

            public void setGet_country_data(CountryData get_country_data) {
                this.get_country_data = get_country_data;
            }

            public class CountryData {
                private int id;
                private String name_en;
                private String name_ar;
                private String code;
                private String phone_code;
                private String flag;

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

        }
    }
}
