package com.thomas.playlists.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ThomasHiron on 24/10/2014.
 */
public class MainDatabaseHelper extends SQLiteOpenHelper
{
    /**
     * Constructeur
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MainDatabaseHelper(Context context)
    {
        super(context, MyContentProvider.DATABASE_NAME, null, MyContentProvider.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(MyContentProvider.CREATE_TABLE_PLAYLISTS);
        sqLiteDatabase.execSQL(MyContentProvider.CREATE_TABLE_SONGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        /* Suppression des tables */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContentProvider.TABLE_PLAYLISTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContentProvider.TABLE_SONGS);

        /* Recréation */
        onCreate(sqLiteDatabase);
    }
}
