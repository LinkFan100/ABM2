package com.example.abm2.Alert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.abm2.R;


public class StartEndAlert extends BroadcastReceiver {
    static int notifyId;

    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmType = intent.getStringExtra("alarmType"); // Get extra data

        // Send different types of alarms based on alarmType
        if ("start".equals(alarmType)) {
                makeNotification(context, "Course Starting", "Your Course starts Today");
        }
        if ("end".equals(alarmType)) {
                    makeNotification(context, "Course Ending", "Last Day to Complete Course!");
            }
        if ("startAssess".equals(alarmType)) {
            makeNotification(context, "Assessment Starting", "Your Assessment starts Today");
        }
        if ("endAssess".equals(alarmType)) {
            makeNotification(context, "Course Ending", "Last Day to Complete Assessment!");
        }

    }
    private void makeNotification (Context context, String title, String message){
        String channelID = "Channel_First";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);

        builder.setSmallIcon(R.drawable.baseline_class_24)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, StartEndAlert.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID, "First", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(notifyId++, builder.build());
    }
}
