package com.yallagoom.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/6/2018.
 */

public class Event implements Serializable {
    private ArrayList<DataEvent> data;

    public ArrayList<DataEvent> getData() {
        return data;
    }

    public void setData(ArrayList<DataEvent> data) {
        this.data = data;
    }

    public class DataEvent implements Serializable {
        private int id;
        private int EventCategoryId;
        private int CreatorUserId;
        private String EventTitle;
        private String StartEventDate;
        private String EndEventDate;
        private String StartEventTime;
        private String EndEventTime;
        private String EventImage;
        private String EventDescription;
        private String IsFree;
        private String Cost;
        private String OrganizerName;
        private String OrganizerDescription;
        private String PrivateOrPublic;
        private String EventLat;
        private String EventLong;
        private String created_at;
        private String updated_at;
        private Category category;

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

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

        public String getCost() {
            return Cost;
        }

        public void setCost(String cost) {
            Cost = cost;
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
    }

    public class Category implements Serializable {
        private int id;
        private String CategoryName;
        private String CategoryDescription;
        private String created_at;
        private String updated_at;

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

}
