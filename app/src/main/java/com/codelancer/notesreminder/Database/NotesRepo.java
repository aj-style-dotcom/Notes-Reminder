package com.codelancer.notesreminder.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepo {
    private final NotesDao notesDao;
    private final LiveData<List<NotesModel>> listLiveData;

    public NotesRepo(Application application){
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();
        listLiveData = notesDao.getNotes();
    }

    LiveData<List<NotesModel>> getAllNotes(){
        return listLiveData;
    }

    void insert(NotesModel notesModel){
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.insert(notesModel));
    }

    public void update(NotesModel notesModel){
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.update(notesModel));
    }

    void delete(NotesModel notesModel){
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDao.delete(notesModel));
    }

}
