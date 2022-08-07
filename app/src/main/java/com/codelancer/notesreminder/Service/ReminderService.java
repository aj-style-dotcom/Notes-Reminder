package com.codelancer.notesreminder.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.Database.NotesRepo;
import com.codelancer.notesreminder.R;
import com.codelancer.notesreminder.Reminder.Notifier;

public class ReminderService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle bundle = intent.getBundleExtra(getString(R.string.notesBundle));
        NotesModel notesModel = (NotesModel)bundle.getSerializable(getString(R.string.notesModel));

        new Notifier().notification(this.getApplicationContext(), notesModel);

        NotesRepo notesRepo = new NotesRepo(getApplication());
        notesModel.setReminder_time(null);
        notesRepo.update(notesModel);
        Log.d("testing", "reminder updated");
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
