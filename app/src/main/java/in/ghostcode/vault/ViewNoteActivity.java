package in.ghostcode.vault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import in.ghostcode.sqlhelper.Note;
import in.ghostcode.sqlhelper.SQLDatabaseHelper;

public class ViewNoteActivity extends AppCompatActivity {
    private EditText titleText;
    private EditText contentText;
    private String type;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        type = intent.getStringExtra(Constants.TYPE);

        titleText = (EditText) findViewById(R.id.note_title);
        contentText = (EditText) findViewById(R.id.note_content);

        if (type.equals(Constants.EDIT_NOTE)) {
            note = (Note) intent.getSerializableExtra(Constants.NOTE);
            titleText.setText(note.getTitle());
            contentText.setText(note.getContent());
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            saveNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveNote();
        super.onBackPressed();
    }

    public void saveNote() {
        SQLDatabaseHelper dbHelper = new SQLDatabaseHelper(this);
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        if (type.equals(Constants.EDIT_NOTE)) {
            if (!title.equals(note.getTitle())) {
                dbHelper.updateTitle(note.getId(), title);
            }
            if (!content.equals(note.getContent())) {
                dbHelper.updateContent(note.getId(), content);
            }
        } else if (type.equals(Constants.NEW_NOTE)) {
            if (!title.equals("") || !content.equals(""))
            dbHelper.insertintoDB(title, content);
        }

    }
}
