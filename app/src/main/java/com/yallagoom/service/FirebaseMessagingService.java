package com.yallagoom.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.RemoteMessage;
import com.yallagoom.R;
import com.yallagoom.activity.HomeActivity;
import com.yallagoom.activity.NotificationActivity;

import java.util.Map;

/**
 * Created by Mahmoud Sabbah on 4/3/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("message", "" + remoteMessage.getData());
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
        //    checkData(remoteMessage.getData(),remoteMessage);

    }
/*
    private void checkData(Map<String, String> data, RemoteMessage remoteMessage) {
        switch (data.get("type")) {
            case "friend_request":
                showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                break;
            case "you_got_new_friend":
                showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                break;
        }

    }*/

    private void showNotification(String title, String body, Map<String, String> data) {
        Intent intent = new Intent(this, NotificationActivity.class);
        switch (data.get("type")) {
            case "friend_request":
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("ActionNotification","friend_request");
                intent.putExtra("active",true);
                break;
            case "you_got_new_friend":
                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("ActionNotification","you_got_new_friend");
                intent.putExtra("active",true);

                break;

        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setImageViewResource(R.id.image, R.drawable.logo);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, body);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setAutoCancel(true)
             /*   .setContentTitle(title)
                .setContentText(body)*/
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setContent(contentView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }

    private void showNotification2(String message) {

        Intent i = new Intent(this, NotificationActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setAutoCancel(true)
                .setContentTitle(message)
                .setContentText(message)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }


}
