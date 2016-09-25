package in.ghostcode.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Coffee on 9/25/16.
 */

public class SQLDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes";
    private static final int DATABASE_VERSION = 1;

    private static final String NOTES_TABLE = "notes";
    private static final String KEY_ID ="id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    public SQLDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOTES_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(db);
    }

    // CRUD Operations
    // Create Operation
    public void insertintoDB (String title, String content) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_CONTENT, content);

        database.insert(NOTES_TABLE, null, contentValues);
        database.close();
    }

    // Read Operation
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM " + NOTES_TABLE;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                note.setId(id);
                note.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return notes;
    }

    // Update
    public void updateTitle (int id, String title) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);

        // Update database
        database.update (NOTES_TABLE, contentValues, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        database.close();
    }

    public void updateContent (int id, String content) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONTENT, content);

        // Update database
        database.update (NOTES_TABLE, contentValues, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        database.close();
    }

    // Delete
    public void deleteNote (int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(NOTES_TABLE, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        database.close();
    }
}
