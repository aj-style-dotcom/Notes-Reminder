package com.codelancer.notesreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codelancer.notesreminder.Adapter.NotesAdapter;
import com.codelancer.notesreminder.Database.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private ViewModel viewModel;
    private NotesAdapter notesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.app_name);

        initialize();

        recyclerView.setAdapter(notesAdapter);
        viewModel.getAllNotes().observe(this, notesModels -> notesAdapter.submitList(notesModels));

        addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Notes.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(this, About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.listRecycleView);
        addButton = findViewById(R.id.addButton);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        notesAdapter = new NotesAdapter(new NotesAdapter.NotesDiff(), this);
    }
}
