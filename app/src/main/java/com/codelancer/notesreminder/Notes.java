package com.codelancer.notesreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.Database.ViewModel;

import com.codelancer.notesreminder.Reminder.Reminder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;


public class Notes extends AppCompatActivity {
    private EditText titleInput, noteInput;
    TimePickerDialog timeBuilder;
    private NotesModel notesModel;
    private ViewModel viewModel;
    private ActionBar actionBar;
    EditText filename;
    private MaterialAlertDialogBuilder builder;
    private String reminderTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        initialize();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notemenu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.reminder:
                setReminder();
                return true;
            case R.id.save:
                export();
                return true;
            case R.id.delete:
                deleteNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, this.getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
                Helper.exportFile(this, filename.getText().toString(), titleInput.getText().toString()+"\n"+noteInput.getText().toString());
            }else{
                Toast.makeText(this, this.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        save();
    }

    //initialize all variable
    private void initialize() {
        titleInput = findViewById(R.id.titleInput);
        noteInput = findViewById(R.id.noteInput);

        Calendar calendar = Calendar.getInstance();

        timeBuilder = new TimePickerDialog(this, (timePicker, i, i1) -> reminderTime = i+"-"+i1, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        builder = new MaterialAlertDialogBuilder(this);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        actionBar = getSupportActionBar();
        assert actionBar != null;

        setValues();
    }

    // set values
    private void setValues(){
        if(getIntent().hasExtra(this.getString(R.string.notesBundle))){
            actionBar.setTitle(R.string.edit_note);
            Bundle bundle = getIntent().getBundleExtra(this.getString(R.string.notesBundle));
            notesModel = (NotesModel) bundle.getSerializable(this.getString(R.string.notesModel));
            titleInput.setText(notesModel.getTitle());
            noteInput.setText(notesModel.getText());
            this.reminderTime = notesModel.getReminder_time();
        }else{
            actionBar.setTitle(R.string.create_note);
            notesModel = null;
        }
    }

    // save notes
    private void save(){
        if( !(titleInput.getText().toString().equals("") && noteInput.getText().toString().equals("") )) {

            NotesModel notesModel1 = new NotesModel(titleInput.getText().toString().trim(), noteInput.getText().toString().trim(), reminderTime);
            Reminder reminder = new Reminder();
            if(notesModel==null){
                viewModel.insert(notesModel1);
                if(notesModel1.getReminder_time() != null){
                    reminder.setReminder(this, notesModel1);
                }
            }else{
                notesModel1.setId(notesModel.getId());
                reminder.deleteReminder(this);

                if (notesModel1.getReminder_time()!=null)
                    reminder.setReminder(this, notesModel1);
                viewModel.update(notesModel1);
            }
        }
    }

    // delete notes
    private void deleteNote(){
        if(notesModel!=null){
            viewModel.delete(notesModel);
            new Reminder().deleteReminder(this);
            finish();
        }
    }

    // action on reminder icon
    private void setReminder(){
        String splitter = "-";
        if (reminderTime == null){
            timeBuilder.show();
        }else {
            builder.setMessage(reminderTime);
            builder.setPositiveButton(R.string.dialog_positive, (dialog, which) -> {
            });
            builder.setNegativeButton(R.string.dialog_negative, (dialog, which) -> {
                int hr = Integer.parseInt(reminderTime.split(splitter)[0]);
                int min = Integer.parseInt(reminderTime.split(splitter)[1]);
                timeBuilder = new TimePickerDialog(this, (timePicker, i, i1) -> reminderTime = i+splitter+i1, hr, min, false);
                timeBuilder.show();
            });
            builder.setNeutralButton(R.string.dialog_neutral, (dialog, which) -> this.reminderTime =null);
            builder.show();
        }
    }

    // Export Notes
    private void export(){
        filename= new EditText(this);
        filename.setHint(R.string.file_name);
        builder.setView(filename);

        builder.setPositiveButton(R.string.export_button, (dialog, which) -> Helper.exportFile(this, filename.getText().toString(), titleInput.getText().toString()+"\n\n"+noteInput.getText().toString()));
        builder.setNegativeButton(R.string.cancle_button, (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}