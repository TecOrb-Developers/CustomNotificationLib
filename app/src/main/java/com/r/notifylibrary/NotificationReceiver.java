package com.r.notifylibrary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.r.hrnotification.CustomRemoteNotification;

import static android.content.ContentValues.TAG;

public class NotificationReceiver extends FirebaseMessagingService {

    String channel_id = "notification_channel";
    String name = "web_app";


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("vivek9798", remoteMessage.getNotification().getBody());

        if(remoteMessage.getData()!=null){
            String title=remoteMessage.getNotification().getTitle();
            String desc=remoteMessage.getNotification().getBody();
            CustomRemoteNotification customRemoteNotification=new CustomRemoteNotification();
            customRemoteNotification.ShowNotification(getApplicationContext(),MainActivity.class,MainActivity.default_notification_channel_id,MainActivity.NOTIFICATION_CHANNEL_ID,title,desc,null,null,0);

        }

       /*
        if (remoteMessage.getNotification() != null) {
            String title=remoteMessage.getNotification().getTitle();
            String desc= remoteMessage.getNotification().getBody();
            CustomRemoteNotification customRemoteNotification=new CustomRemoteNotification();
            customRemoteNotification.ShowNotification(getApplicationContext(),MainActivity.class,MainActivity.default_notification_channel_id,MainActivity.NOTIFICATION_CHANNEL_ID,title,desc,null,null,0);
        }*/
    }
}
