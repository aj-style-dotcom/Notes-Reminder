package com.codelancer.notesreminder.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.Database.ViewModel;
import com.codelancer.notesreminder.Receiver.ReminderReceiver;

import java.util.Calendar;

public class Reminder {
    public void setReminder(Context context, NotesModel notesModel){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("notesModel", notesModel);
        intent.putExtra("notesBundle", bundle);

        int requestCode = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_MUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(notesModel.getReminder_time().split("-")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(notesModel.getReminder_time().split("-")[1]));
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            Toast.makeText(context, "Postponing Reminder for Tomorrow", Toast.LENGTH_SHORT).show();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("testing", "reminder is set");

    }
    public void deleteReminder(@NonNull Context context){
        int requestCode = (int) System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.cancel(pendingIntent);
        Log.d("testing", "reminder is deleted");
    }
}