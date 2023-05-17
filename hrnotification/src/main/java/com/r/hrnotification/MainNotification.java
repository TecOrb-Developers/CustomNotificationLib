package com.r.hrnotification;

import android.content.Context;
import android.graphics.Typeface;

public class MainNotification {

    private Typeface tf,tf1;
    private boolean bold,cancelable;
    private String title, subtitle, okLabel, koLabel;
    private Context context;
    private NotificationCallback positiveListener;
    private NotificationCallback negativeListener;
    private NotificationDialog dialog;

    public MainNotification(Context context) {
        this.context = context;
    }

    public MainNotification setTitle(String title) {
        this.title = title;
        return this;
    }

    public MainNotification setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public MainNotification setBoldPositiveLabel(boolean bold) {
        this.bold = bold;
        return this;
    }

    public MainNotification setFont(Typeface font) {
        this.tf=font;
        return this;
    }
    public MainNotification setTitleFont(Typeface font){
        this.tf1=font;
        return this;
    }

    public MainNotification setCancelable(boolean cancelable){
        this.cancelable=cancelable;
        return this;
    }

    public MainNotification setNegativeListener(String koLabel,NotificationCallback listener) {
        this.negativeListener=listener;
        this.koLabel=koLabel;
        return this;
    }

    public MainNotification setPositiveListener(String okLabel,NotificationCallback listener) {
        this.positiveListener = listener;
        this.okLabel=okLabel;
        return this;
    }

    public void dismissDialog(){
        try {
            if (dialog!=null) dialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public NotificationDialog build(){
        dialog = new NotificationDialog(context,title,subtitle, bold, tf,cancelable,tf1);
        dialog.setNegative(koLabel,negativeListener);
        dialog.setPositive(okLabel,positiveListener);
        return dialog;
    }

}
