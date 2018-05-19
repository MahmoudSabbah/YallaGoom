package com.oxygen.yallagoom.entity.event;

import com.oxygen.yallagoom.entity.User;

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
                private User member_data;

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

                public User getMember_data() {
                    return member_data;
                }

                public void setMember_data(User member_data) {
                    this.member_data = member_data;
                }

            }
        }

    }
}
