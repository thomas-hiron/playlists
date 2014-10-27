package com.thomas.playlists.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ThomasHiron on 24/10/2014.
 *
 * Exécute les requêtes
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    /**
     * Constructeur
     *
     * @param context Le contexte
     */
    public DatabaseHelper(Context context)
    {
        super(context, ContentProvider.DATABASE_NAME, null, ContentProvider.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(ContentProvider.CREATE_TABLE_PLAYLISTS);
        sqLiteDatabase.execSQL(ContentProvider.CREATE_TABLE_SONGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        /* Suppression des tables */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentProvider.TABLE_PLAYLISTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentProvider.TABLE_SONGS);

        /* Recréation */
        onCreate(sqLiteDatabase);
    }
}
