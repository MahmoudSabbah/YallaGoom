package com.oxygen.yallagoom.entity.TicketClasses;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Mahmoud Sabbah on 2/28/2018.
 */
public class ReviewList extends RealmObject implements Serializable {
   private   RealmList<ReviewListData> data;

    public RealmList<ReviewListData> getData() {
        return data;
    }

    public void setData(RealmList<ReviewListData> data) {
        this.data = data;
    }

}
