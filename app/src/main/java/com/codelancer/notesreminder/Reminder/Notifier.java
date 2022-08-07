package com.codelancer.notesreminder.Reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.R;

public class Notifier {
    public void notification(Context context, NotesModel notesModel){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.channel_id));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(notesModel.getTitle());
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setVibrate(new long[]{0, 100, 1000});
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.build().flags = Notification.PRIORITY_HIGH;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.channel_id), context.getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(context.getString(R.string.channel_id));
        }

        notificationManager.notify((int)System.currentTimeMillis(), builder.build());
    }
}
