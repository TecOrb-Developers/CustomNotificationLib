package com.r.notifylibrary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.r.hrnotification.CustomRemoteNotification;

public class MyWorkClass extends Worker {

    private static final String WORK_RESULT = "work_result";
    public  final  static String NOTIFICATION_CHANNEL_ID = "10001";
    private final static   String default_notification_channel_id = "default";
    private String title="Device preview";
    private String desc="This preview provides a general idea of how your message will appear on a mobile device. Actual message rendering will vary depending on the device. Test with a real device for actual results. ";
    Notification callback;

    public MyWorkClass(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);
        CustomRemoteNotification customRemoteNotification=new CustomRemoteNotification();
        customRemoteNotification.ShowNotification(getApplicationContext(),MainActivity.class,default_notification_channel_id,NOTIFICATION_CHANNEL_ID,title,desc,null,null,0);
        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        return Result.success(outputData);
    }


    public interface Notification {
        void sendNotification(String task,String desc);
    }



}