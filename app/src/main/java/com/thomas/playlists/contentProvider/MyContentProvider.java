package com.thomas.playlists.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by ThomasHiron on 24/10/2014.
 */
public class MyContentProvider extends ContentProvider
{
    private final String DB_NAME = "playlits_db";

    private MainDatabaseHelper mOpenHelper;

    private SQLiteDatabase db;

    @Override
    public boolean onCreate()
    {
        mOpenHelper = new MainDatabaseHelper(getContext(), DB_NAME, null, 1);

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2)
    {
        return null;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        db = mOpenHelper.getWritableDatabase();
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }
}
