package com.oxygen.yallagoom.app;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;


import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.firebase.FirebaseApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Mahmoud Sabbah on 2/5/2017.
 */

public class MainApplication extends Application {
    public static final List<Locale> SUPPORTED_LOCALES =
            Arrays.asList(
                    new Locale("en", "US"),
                    new Locale("ar")

            );
    public static boolean isConnected;
    public static boolean networkAvailable;
    public static boolean detectorInitialized;
    public static boolean verification_check;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = ToolUtils.getSharedPreferences(this, Constant.loginCheck);
        verification_check = sharedPreferences.getBoolean(Constant.verification_check, false);

        FirebaseApp.initializeApp(this);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(false)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(options)
               /* .memoryCacheSize(41943040)
                .threadPoolSize(10)*/
                .build();

        ImageLoader.getInstance().init(config);

        ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connectivity>() {
                    @Override
                    public void call(Connectivity connectivity) {
                        if (connectivity.getState() == NetworkInfo.State.CONNECTED) {
                            networkAvailable = true;
                            if (isConnected) {

                            } else if (detectorInitialized) {

                            }
                        } else {
                            //  Log.i("test", "3");
                            networkAvailable = false;

                        }
                    }
                });
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isConnectedToInternet) {
                        detectorInitialized = true;
                        if (isConnectedToInternet) {
                            //     onceInternetConnect();
                            isConnected = true;
                            //Log.e(TAG, "YesConn");
                        } else {
                            isConnected = false;
                            //    Log.e(TAG, "NoConn");
                            if (networkAvailable) {

                            } else {

                            }
                        }
                    }
                });
        Realm.init(this);
        RealmConfiguration realmConfiguration= new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    //    LocaleChanger.onConfigurationChanged();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}