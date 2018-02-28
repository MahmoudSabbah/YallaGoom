package com.yallagoom.utils;

import com.yallagoom.controller.RealmController;

import io.realm.Realm;

/**
 * Created by Mahmoud Sabbah on 2/28/2018.
 */

public class RealmTools {

    public static Realm getRealm() {
        Realm realm = Realm.getDefaultInstance();
        RealmController realmController = new RealmController(realm);
        return realm;
    }
}
