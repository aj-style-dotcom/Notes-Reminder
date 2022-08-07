package com.codelancer.notesreminder.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.R;
import com.codelancer.notesreminder.Reminder.Notifier;
import com.codelancer.notesreminder.Service.ReminderService;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("testing", "reminding user");
        Bundle bundle = intent.getBundleExtra(context.getString(R.string.notesBundle));
        NotesModel notesModel = (NotesModel) bundle.getSerializable(context.getString(R.string.notesModel));

        Bundle bundle1 = new Bundle();
        Intent intent1 = new Intent(context, ReminderService.class);
        bundle1.putSerializable(context.getString(R.string.notesModel), notesModel);
        intent1.putExtra(context.getString(R.string.notesBundle), bundle1);

        context.startService(intent1);
    }
}