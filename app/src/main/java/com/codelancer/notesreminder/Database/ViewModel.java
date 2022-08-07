package com.codelancer.notesreminder.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private final NotesRepo notesRepo;
    private final LiveData<List<NotesModel>> listLiveData;

    public ViewModel(@NonNull Application application) {
        super(application);
        notesRepo = new NotesRepo(application);
        listLiveData=notesRepo.getAllNotes();
    }

    public LiveData<List<NotesModel>> getAllNotes(){
        return listLiveData;
    }

    public void insert(NotesModel notesModel) { notesRepo.insert(notesModel); }

    public void update(NotesModel notesModel) { notesRepo.update(notesModel); }

    public void delete(NotesModel notesModel) { notesRepo.delete(notesModel); }


}
