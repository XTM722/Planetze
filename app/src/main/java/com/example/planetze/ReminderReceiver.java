package com.example.planetze;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "habit_reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String habitTitle = intent.getStringExtra("habit_title");
            if (habitTitle == null || habitTitle.isEmpty()) {
                habitTitle = "Your Habit"; // Fallback title
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "Habit Reminders",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel.setDescription("Reminder notifications for habits");
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                channel.setSound(soundUri, null); // Use the default sound
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info) // Default icon
                    .setContentTitle("Habit Reminder")
                    .setContentText("It's time to log progress for: " + habitTitle)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(soundUri) // Default notification sound
                    .setAutoCancel(true);

            if (notificationManager != null) {
                notificationManager.notify(habitTitle.hashCode(), builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
