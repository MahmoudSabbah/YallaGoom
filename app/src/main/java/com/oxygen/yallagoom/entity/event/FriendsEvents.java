package com.oxygen.yallagoom.entity.event;

import com.oxygen.yallagoom.entity.CategoryDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/24/2018.
 */

public class FriendsEvents implements Serializable {
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data implements Serializable{
        private int id;
        private int EventCategoryId;
        private int CreatorUserId;
        private int Cost;
        private String EventTitle;
        private String StartEventDate;
        private String EndEventDate;
        private String StartEventTime;
        private String EndEventTime;
        private String EventImage;
        private String EventDescription;
        private String IsFree;
        private String OrganizerName;
        private String OrganizerDescription;
        private String PrivateOrPublic;
        private String EventLat;
        private String EventLong;
        private String created_at;
        private String updated_at;
        private CreatorData creator_data;
        private CategoryDetails category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEventCategoryId() {
            return EventCategoryId;
        }

        public void setEventCategoryId(int eventCategoryId) {
            EventCategoryId = eventCategoryId;
        }

        public int getCreatorUserId() {
            return CreatorUserId;
        }

        public void setCreatorUserId(int creatorUserId) {
            CreatorUserId = creatorUserId;
        }

        public int getCost() {
            return Cost;
        }

        public void setCost(int cost) {
            Cost = cost;
        }

        public String getEventTitle() {
            return EventTitle;
        }

        public void setEventTitle(String eventTitle) {
            EventTitle = eventTitle;
        }

        public String getStartEventDate() {
            return StartEventDate;
        }

        public void setStartEventDate(String startEventDate) {
            StartEventDate = startEventDate;
        }

        public String getEndEventDate() {
            return EndEventDate;
        }

        public void setEndEventDate(String endEventDate) {
            EndEventDate = endEventDate;
        }

        public String getStartEventTime() {
            return StartEventTime;
        }

        public void setStartEventTime(String startEventTime) {
            StartEventTime = startEventTime;
        }

        public String getEndEventTime() {
            return EndEventTime;
        }

        public void setEndEventTime(String endEventTime) {
            EndEventTime = endEventTime;
        }

        public String getEventImage() {
            return EventImage;
        }

        public void setEventImage(String eventImage) {
            EventImage = eventImage;
        }

        public String getEventDescription() {
            return EventDescription;
        }

        public void setEventDescription(String eventDescription) {
            EventDescription = eventDescription;
        }

        public String getIsFree() {
            return IsFree;
        }

        public void setIsFree(String isFree) {
            IsFree = isFree;
        }

        public String getOrganizerName() {
            return OrganizerName;
        }

        public void setOrganizerName(String organizerName) {
            OrganizerName = organizerName;
        }

        public String getOrganizerDescription() {
            return OrganizerDescription;
        }

        public void setOrganizerDescription(String organizerDescription) {
            OrganizerDescription = organizerDescription;
        }

        public String getPrivateOrPublic() {
            return PrivateOrPublic;
        }

        public void setPrivateOrPublic(String privateOrPublic) {
            PrivateOrPublic = privateOrPublic;
        }

        public String getEventLat() {
            return EventLat;
        }

        public void setEventLat(String eventLat) {
            EventLat = eventLat;
        }

        public String getEventLong() {
            return EventLong;
        }

        public void setEventLong(String eventLong) {
            EventLong = eventLong;
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

        public CreatorData getCreator_data() {
            return creator_data;
        }

        public void setCreator_data(CreatorData creator_data) {
            this.creator_data = creator_data;
        }

        public CategoryDetails getCategory() {
            return category;
        }

        public void setCategory(CategoryDetails category) {
            this.category = category;
        }


        public class CreatorData implements Serializable{
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

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
