package com.thomas.playlists.contentProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ThomasHiron on 24/10/2014.
 */
public class MainDatabaseHelper extends SQLiteOpenHelper
{
    /* La table playlists */
    private final String PLAYLISTS_TABLE = "CREATE TABLE IF NOT EXISTS playlists " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT)";
    /**
     * Constructeur
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MainDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(PLAYLISTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {

    }

    public void addPlaylist() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", "Yeah !"); // get title

        // 3. insert
        db.insert("playlists", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public List<String> getAllPlaylists() {
        List<String> playlists = new LinkedList<String>();

        // 1. build the query
        String query = "SELECT title FROM playlists";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        String title = null;
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(0);

                // Add book to books
                playlists.add(title);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", playlists.toString());

        // return books
        return playlists;
    }
}
