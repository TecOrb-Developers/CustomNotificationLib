package com.r.notifylibrary;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.r.hrnotification.CustomRemoteNotification;
import com.r.hrnotification.MainNotification;

public class NotificationIntentService extends IntentService {

    CustomRemoteNotification customRemoteNotification=new CustomRemoteNotification();
    public NotificationIntentService() {
        super("notificationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getAction()) {
            case "left":
                Handler leftHandler = new Handler(Looper.getMainLooper());
                leftHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getBaseContext(), "You clicked the left button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case "right":
                Handler rightHandler = new Handler(Looper.getMainLooper());
                rightHandler.post(new Runnable() {
                    @Override
                    public void run() {

                          Toast.makeText(getBaseContext(), "You clicked the right button", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

}
