package com.codelancer.notesreminder.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notes_Table")
public class NotesModel implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "reminder_time")
    private String reminder_time;

    public NotesModel(String title, String text, String reminder_time) {
        this.title = title;
        this.text = text;
        this.reminder_time = reminder_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    public void setReminder_time(String reminder_time) {
        this.reminder_time = reminder_time;
    }
}
