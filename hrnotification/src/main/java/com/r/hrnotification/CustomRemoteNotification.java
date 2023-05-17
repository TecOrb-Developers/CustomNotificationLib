package com.r.hrnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CustomRemoteNotification {

   private static RemoteViews expandedView;
  private static  RemoteViews collapsedView;


    public static RemoteViews getCustomDesignCollapsed(Context context, String title, String message) {
         collapsedView = new RemoteViews(context.getPackageName(), R.layout.collepsed_notification);
        setImageResource(collapsedView, R.id.big_icon, R.drawable.mobileicon);
        setTitle(collapsedView, R.id.content_title, title);
        setMessage(collapsedView, R.id.content_text, message);
        setTextColor(collapsedView, R.id.content_text, Color.RED);
        setTime(collapsedView, R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        return collapsedView;
    }


    public static void buttonAction(Context context){
        Intent leftIntent = new Intent(context, NotificationIntentServices.class);
        leftIntent.setAction("left");
        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(context, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Intent rightIntent = new Intent(context, NotificationIntentServices.class);
        rightIntent.setAction("right");
        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(context, 0, rightIntent, PendingIntent.FLAG_CANCEL_CURRENT));

    }


    public static RemoteViews getCustomDesignExpended(Context context, String title, String message) {
        expandedView = new RemoteViews(context.getPackageName(),R.layout.expanded_notification);
         buttonAction(context);
        setTime(expandedView, R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        setImageResource(expandedView, R.id.big_icon, R.drawable.mobileicon);
        setMessage(expandedView, R.id.notification_message, message);
        setTitle(expandedView, R.id.content_title, title);
        return expandedView;
    }

    public static void ShowNotification(Context context, Class activityClass, String default_notification_channel_id, String NOTIFICATION_CHANNEL_ID, String title, String description, RemoteViews remoteViews, RemoteViews expandRemote, int image) {
        Intent snoozeIntent = new Intent(context, activityClass);
        snoozeIntent.putExtra("EXTRA_NOTIFICATION_ID", 0);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, default_notification_channel_id);
        mBuilder.setTicker("Notification Listener Service Example");
        if (image == 0) {
            mBuilder.setSmallIcon(R.drawable.mobileicon);
        } else {
            mBuilder.setSmallIcon(image);
        }
        if (remoteViews == null && expandRemote == null) {
            mBuilder.setCustomContentView(CustomRemoteNotification.getCustomDesignCollapsed(context, title, description));
            mBuilder.setCustomBigContentView(CustomRemoteNotification.getCustomDesignExpended(context, title, description));
        } else {
            mBuilder.setCustomContentView(remoteViews);
            mBuilder.setCustomBigContentView(expandRemote);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder.setContentIntent(PendingIntent.getService(context, 0, new Intent(context, activityClass), 0));
        } else {
            mBuilder.setContentIntent(PendingIntent.getService(context, 0, new Intent(context, activityClass), PendingIntent.FLAG_MUTABLE));
        }

        // mBuilder.setContentIntent(PendingIntent.getService(context, 0, new Intent(context, activityClass),0));

        mBuilder.setColorized(true);
        mBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        mBuilder.setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public static void setTitle(RemoteViews remoteViews, int id, String title) {
        remoteViews.setTextViewText(id, title);
    }

    public static void setMessage(RemoteViews remoteViews, int id, String message) {
        remoteViews.setTextViewText(id, message);
    }

    public static void setTime(RemoteViews remoteViews, int id, String time) {
        remoteViews.setTextViewText(id, time);
    }

    public static void setImageResource(RemoteViews remoteViews, int id, int image) {
        remoteViews.setImageViewResource(id, image);
    }

    public static void setImageViewInBitmap(RemoteViews remoteViews, int id, Bitmap bitmap) {
        remoteViews.setImageViewBitmap(id, bitmap);
    }

    public static void setImageViewUri(RemoteViews remoteViews, int id, Uri uri) {
        remoteViews.setImageViewUri(id, uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setImageViewIcon(RemoteViews remoteViews, int id, Icon icon) {
        remoteViews.setImageViewIcon(id, icon);
    }

    public static void setTextColor(RemoteViews remoteViews, int id, int color) {
        remoteViews.setTextColor(id, color);
    }

    public static void removeAllView(RemoteViews remoteViews, int id) {
        remoteViews.removeAllViews(id);
    }

    public static void setBackGroundColor(RemoteViews remoteViews, int id, String method, int color) {
        remoteViews.setInt(id, method, color);
    }

    public RemoteViews getCustomDesign(Context context, String title, String message) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), com.r.hrnotification.R.layout.notification);
        remoteViews.setTextViewText(com.r.hrnotification.R.id.title, title);
        remoteViews.setTextViewText(com.r.hrnotification.R.id.subtitle, message);
        remoteViews.setImageViewResource(com.r.hrnotification.R.id.icon, com.r.hrnotification.R.drawable.rounded_background);
        return remoteViews;
    }


}
