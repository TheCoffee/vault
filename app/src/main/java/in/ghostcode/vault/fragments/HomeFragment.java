package in.ghostcode.vault.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ghostcode.sqlhelper.Note;
import in.ghostcode.sqlhelper.SQLDatabaseHelper;
import in.ghostcode.vault.R;
import in.ghostcode.vault.RecyclerViewNotesAdapter;

/**
 * Created by ghost on 9/18/16.
 */
public class HomeFragment extends Fragment {
    ArrayList<Note> notes = new ArrayList<>();
    private RecyclerViewNotesAdapter notesAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

//        Note note = new Note(1, "This is a test", "This is a test content");
//        notes.add(note);
        SQLDatabaseHelper dbHelper = new SQLDatabaseHelper(getContext());
        notes = dbHelper.getAllNotes();

        for (Note n: notes) {
            Log.d("Note Title: ", n.getTitle());
        }

        dbHelper.close();

        recyclerView = (RecyclerView) view.findViewById(R.id.notes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notesAdapter = new RecyclerViewNotesAdapter(notes, getContext());
        recyclerView.setAdapter(notesAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SQLDatabaseHelper dbHelper = new SQLDatabaseHelper(getContext());
        notes = dbHelper.getAllNotes();

        notesAdapter = (RecyclerViewNotesAdapter) recyclerView.getAdapter();
        notesAdapter.setNotes(notes);
        notesAdapter.notifyDataSetChanged();

        dbHelper.close();
    }
}
