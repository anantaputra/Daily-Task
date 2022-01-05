package com.example.dailytask.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dailytask.show.ShowAlarm;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, ShowAlarm.class);
        alarmIntent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmIntent);
    }
}
