package com.codelancer.notesreminder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codelancer.notesreminder.Database.NotesModel;
import com.codelancer.notesreminder.Helper;
import com.codelancer.notesreminder.Notes;
import com.codelancer.notesreminder.R;

public class NotesAdapter extends ListAdapter<NotesModel, NotesAdapter.ViewHolder> {



    Context context;
    public NotesAdapter(@NonNull DiffUtil.ItemCallback<NotesModel> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotesModel notesModel = getItem(position);
        holder.bind(notesModel);
        holder.itemView.setOnClickListener(v -> startEdit(v.getContext(), notesModel));
    }

    private void startEdit(@NonNull Context context, NotesModel notesModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.notesModel), notesModel);
        Intent intent = new Intent(context, Notes.class);
        intent.putExtra(context.getString(R.string.notesBundle), bundle);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, note, reminder;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            reminder = itemView.findViewById(R.id.reminderText);
            note = itemView.findViewById(R.id.noteText);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @NonNull
        public static ViewHolder create(@NonNull ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item, parent, false);
            return new ViewHolder(view);
        }

        public void bind(@NonNull NotesModel notesModel) {
            if (notesModel.getTitle().equals("")) title.setVisibility(View.GONE);
            else title.setText(Helper.parseString(notesModel.getTitle(), 12));
            note.setText(notesModel.getText());
            if(notesModel.getReminder_time()!=null){
                imageView.setVisibility(View.VISIBLE);
                reminder.setVisibility(View.VISIBLE);
                reminder.setText(Helper.getTimeString(notesModel.getReminder_time()));
            }
        }
    }

    public static class NotesDiff extends DiffUtil.ItemCallback<NotesModel>{
        public NotesDiff() {
            super();
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull NotesModel oldItem, @NonNull NotesModel newItem) {
            return super.getChangePayload(oldItem, newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull NotesModel oldItem, @NonNull NotesModel newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull NotesModel oldItem, @NonNull NotesModel newItem) {
            return oldItem.getText().equals(newItem.getText())
                    && oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getReminder_time().toString().equals(newItem.getReminder_time().toString());
        }
    }
}
