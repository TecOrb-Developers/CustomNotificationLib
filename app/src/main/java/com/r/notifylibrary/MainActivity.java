package com.r.notifylibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.r.hrnotification.CustomRemoteNotification;
import com.r.notifylibrary.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String NOTIFICATION_CHANNEL_ID = "10001";
    public final static String default_notification_channel_id = "default";
    public final static String MESSAGE_STATUS = "message_status";
    public Context context;
    boolean gotFBCrash;
    private final String title = "Device preview";
    private final String desc = "This preview provides a general idea of how your message will appear on a mobile device. Actual message rendering will vary depending on the device. Test with a real device for actual results. ";
    private final String title1 = "Honda";
    private final String desc2 = "The most popular Honda cars in India are Amaze, New City and City Hybrid eHEV. The prices for the top 3 popular Honda cars in India are: ...\n" +
            "Most Expensive: Honda City Hybrid eHEV (Rs. ...\n" +
            "Fuel Types: Petrol, Diesel, Hybrid (Electric + P...\n" +
            "Most Affordable: Honda Amaze (Rs. 7.01 Lakh)\n" +
            "Popular: Honda Amaze, Honda New City, Hon...\n";
    private NotificationReceiver notificationReceiver;
    private ActivityMainBinding binding;
    private ExplosionField mExplosionField;
    private RemoteViews remoteViews, expandedView;
    private CustomRemoteNotification customRemoteNotification;
    private MyWorkClass myWorkClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        binding.items.setOnClickListener(this);
        binding.noti.setOnClickListener(this);
        customRemoteNotification = new CustomRemoteNotification();
        FirebaseApp.initializeApp(this);
        firebaseNotification();

    }

    private void firebaseNotification() {
        if (!gotFBCrash) {
            try {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        try {
                            if (task != null && task.isComplete()) {
                                if (task.getResult() != null) {
                                    String newToken = task.getResult();
                                    if (newToken != null)
                                        Log.d("FirebaseToken", newToken);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                gotFBCrash = true;
            }
        } else {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String deviceToken = instanceIdResult.getToken();
                    Log.d("FirebaseTOken", deviceToken);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.items:
                workManger();
                break;
            case R.id.noti:

                RemoteViews remoteViews = CustomRemoteNotification.getCustomDesignCollapsed(context, title1, desc2);
                remoteViews.setTextColor(R.id.content_title, Color.WHITE);
                RemoteViews remoteViews1 = CustomRemoteNotification.getCustomDesignExpended(context, title1, desc2);
                remoteViews1.setImageViewResource(R.id.big_icon, R.drawable.car);
                remoteViews1.setImageViewResource(R.id.notification_img, R.drawable.car);
                int image = R.drawable.ic_baseline_add_reaction_24;
                CustomRemoteNotification.ShowNotification(context, MainActivity.class, default_notification_channel_id, NOTIFICATION_CHANNEL_ID, title, desc, remoteViews, remoteViews1, image);
                break;
        }
    }
    private void workManger() {
        WorkManager mWorkManager = WorkManager.getInstance();
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        mWorkManager.cancelAllWorkByTag("work_tag");
        OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(MyWorkClass.class)
                .setConstraints(constraints)
                .setInitialDelay(3, TimeUnit.SECONDS)
                .addTag("work_tag")
                .build();
        mWorkManager.enqueue(mRequest);
    }


}