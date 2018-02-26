package com.yallagoom.entity;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/22/2018.
 */

public class Group {
    private MyGroup my_own_group;
    private MyGroup others_group;

    public MyGroup getOthers_group() {
        return others_group;
    }

    public void setOthers_group(MyGroup others_group) {
        this.others_group = others_group;
    }

    public MyGroup getMy_own_group() {
        return my_own_group;
    }

    public void setMy_own_group(MyGroup my_own_group) {
        this.my_own_group = my_own_group;
    }

    public class MyGroup {
        private ArrayList<Data> data;

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {
            private int id;
            private int creator_id;
            private String title;
            private String img;
            private String updated_at;
            private String created_at;
            private ArrayList<memberslist> members_list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCreator_id() {
                return creator_id;
            }

            public void setCreator_id(int creator_id) {
                this.creator_id = creator_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public ArrayList<memberslist> getMembers_list() {
                return members_list;
            }

            public void setMembers_list(ArrayList<memberslist> members_list) {
                this.members_list = members_list;
            }

            public class memberslist {
                private int id;
                private int group_id;
                private int member_id;
                private String created_at;
                private String updated_at;
                private memberData member_data;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(int group_id) {
                    this.group_id = group_id;
                }

                public int getMember_id() {
                    return member_id;
                }

                public void setMember_id(int member_id) {
                    this.member_id = member_id;
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

                public memberData getMember_data() {
                    return member_data;
                }

                public void setMember_data(memberData member_data) {
                    this.member_data = member_data;
                }

                public class memberData {
                    private int id;
                    private int country_id;
                    private String email;
                    private String mobile;
                    private String facebook;
                    private String twitter;
                    private String device_id;
                    private String created_at;
                    private String updated_at;
                    private String first_name;
                    private String last_name;
                    private String birth_date;
                    private String gender;
                    private String img_url;

                    public String getImg_url() {
                        return img_url;
                    }

                    public void setImg_url(String img_url) {
                        this.img_url = img_url;
                    }

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
                }
            }
        }

    }
}
