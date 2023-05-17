package com.r.notifylibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NotificationIntentActivity extends Activity {
    Button btnCancel;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_notification);
        context=this;
        btnCancel=findViewById(R.id.left_button);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Left Click",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
