package com.yallagoom.controller;

/**
 * Created by pdx on 7/24/2017.
 */


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.yallagoom.entity.TicketClasses.Country;
import com.yallagoom.entity.TicketClasses.ReviewList;
import com.yallagoom.entity.TicketClasses.TicketInfo;
import com.yallagoom.entity.TicketClasses.TicketDetails;
import com.yallagoom.entity.gift.FavoriteGifts;
import com.yallagoom.entity.gift.Gift;
import com.yallagoom.entity.gift.MyCart;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public RealmController(Realm realm) {
        this.realm = realm;
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Categories.class
    public void clearAllTicketDetails() {

        realm.beginTransaction();
        realm.delete(TicketDetails.class);
        realm.delete(TicketInfo.class);
        realm.commitTransaction();
    }

    //find all objects in the Categories.class
    public RealmResults<TicketDetails> getTicketDetails() {

        return realm.where(TicketDetails.class).findAll();
    }

    public RealmResults<ReviewList> getReviewList() {

        return realm.where(ReviewList.class).findAll();
    }

    public RealmResults<Country> getCountry() {

        return realm.where(Country.class).findAll();
    }

    public RealmResults<TicketInfo> getTicketInfo() {

        return realm.where(TicketInfo.class).findAll();
    }

    public boolean checkTicketsExists(int id) {
        TicketInfo ticketInfo = realm.where(TicketInfo.class).equalTo("id", id).findFirst();

        if (ticketInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public void removeTickets(int id) {
        RealmResults<TicketInfo> ticketInfos = realm.where(TicketInfo.class).equalTo("id", id).findAll();
        ticketInfos.deleteAllFromRealm();
    }

    public boolean checkGift(int id) {
        RealmResults<Gift> gifts = realm.where(Gift.class).equalTo("id", id).findAll();
        if (gifts.size() > 0) {
            return true;
        } else
            return false;
    }

    public void removeGift(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Gift> gifts = realm.where(Gift.class).equalTo("id", id).findAll();
                gifts.deleteAllFromRealm();
            }
        });

    }

    public boolean checkMyCart(int id) {
        RealmResults<MyCart> myCarts = realm.where(MyCart.class).equalTo("id", id).findAll();
        if (myCarts.size() > 0) {
            return true;
        } else
            return false;
    }

    public void removeMyCart(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MyCart> myCarts = realm.where(MyCart.class).equalTo("id", id).findAll();
                myCarts.deleteAllFromRealm();
            }
        });

    }

    public RealmResults<MyCart> getMyCart() {

        return realm.where(MyCart.class).findAll();
    }

    public boolean checkFavoriteGifts(int id) {
        RealmResults<FavoriteGifts> favoriteGifts = realm.where(FavoriteGifts.class).equalTo("id", id).findAll();
        if (favoriteGifts.size() > 0) {
            return true;
        } else
            return false;
    }

    public void removeFavoriteGifts(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FavoriteGifts> favoriteGifts = realm.where(FavoriteGifts.class).equalTo("id", id).findAll();
                favoriteGifts.deleteAllFromRealm();
            }
        });

    }

    public FavoriteGifts[] getFavoriteGifts() {
        ArrayList<FavoriteGifts> list = new ArrayList(realm.where(FavoriteGifts.class).findAll());
        FavoriteGifts[] array = list.toArray(new FavoriteGifts[list.size()]);
        return array;
    }
    public MyCart[] getMyCarts() {
        ArrayList<MyCart> list = new ArrayList(realm.where(MyCart.class).findAll());
        MyCart[] array = list.toArray(new MyCart[list.size()]);
        return array;
    }
  /*  public void removeTickets(int id) {

        RealmResults<TicketDetails> ticketDetails = realm.where(TicketDetails.class).findAll();
        Log.e("removeTicketsDetails",""+ticketDetails.size());

        for (int i=0;i<ticketDetails.size();i++){//distinct
            if (ticketDetails.get(i).getTicket_info().getId()==id){
                Log.e("removeTickets","removeTickets"+i);
                ticketDetails.get(i).getTicket_info().deleteFromRealm();
                ticketDetails.get(i).getReview_list().deleteFromRealm();
                ticketDetails.deleteFromRealm(i);
            }
        }

    }*/

}