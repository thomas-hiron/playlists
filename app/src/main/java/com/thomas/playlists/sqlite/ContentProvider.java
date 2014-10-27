package com.thomas.playlists.sqlite;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by ThomasHiron on 24/10/2014.
 *
 * Interagit avec le DatabaseHelper et construit les requêtes
 */
public class ContentProvider extends android.content.ContentProvider
{
    private static final String PROVIDER_NAME = "com.thomas.playlists.provider.Playlists";

    public static final String URL_PLAYLISTS = "content://" + PROVIDER_NAME + "/playlists";
    public static final String URL_SONGS = "content://" + PROVIDER_NAME + "/songs";

    public static final Uri CONTENT_URI_PLAYLISTS = Uri.parse(URL_PLAYLISTS);
    public static final Uri CONTENT_URI_SONGS = Uri.parse(URL_SONGS);

    /* Les champs de playlists */
    public static final String PLAYLISTS_ID = "id";
    public static final String PLAYLISTS_TITLE = "title";

    /* Les champs de songs */
    public static final String SONGS_ID = "id";
    public static final String SONGS_PLAYLISTS_ID = "playlists_id";
    public static final String SONGS_TITLE = "title";
    public static final String SONGS_DURATION = "duration";
    public static final String SONGS_COVER = "cover";
    public static final String SONGS_DANCEABILITY = "danceability";
    public static final String SONGS_TEMPO = "tempo";
    public static final String SONGS_HOTTTNESSS = "hotttnesss";
    public static final String SONGS_LOUDNESS = "loudness";
    public static final String SONGS_ARTIST_NAME = "artist_name";
    public static final String SONGS_ARTIST_LOCATION = "artist_location";
    public static final String SONGS_ARTIST_HOTTTNESSS = "artist_hotttnesss";
    public static final String SONGS_ARTIST_ALBUMS = "artist_albums";

    /* Les valeurs utilisées dans le content uri */
    private static final int PLAYLISTS = 1;
    private static final int SONGS = 2;

    /* L'helper sqlite */
    DatabaseHelper mDBHelper;

    private static final UriMatcher mUriMatcher;
    static
    {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_NAME, "playlists", PLAYLISTS);
        mUriMatcher.addURI(PROVIDER_NAME, "songs", SONGS);
    }

    /* LA BDD */
    private SQLiteDatabase mDatabase;
    protected static final String DATABASE_NAME = "playlists";
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_PLAYLISTS = "playlists";
    protected static final String TABLE_SONGS = "songs";

    /* Les requêtes des tables */
    protected static final String CREATE_TABLE_PLAYLISTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYLISTS + " " +
            "(" + PLAYLISTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PLAYLISTS_TITLE + " TEXT)";

    protected static final String CREATE_TABLE_SONGS = "CREATE TABLE IF NOT EXISTS " + TABLE_SONGS + " " +
            "(" +
            SONGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SONGS_PLAYLISTS_ID + " INTEGER," +
            SONGS_TITLE + " TEXT," +
            SONGS_DURATION + " TEXT," +
            SONGS_COVER + " TEXT," +
            SONGS_DANCEABILITY + " INTEGER," +
            SONGS_TEMPO + " INTEGER," +
            SONGS_HOTTTNESSS + " INTEGER," +
            SONGS_LOUDNESS + " INTEGER," +
            SONGS_ARTIST_NAME + " TEXT," +
            SONGS_ARTIST_LOCATION + " TEXT," +
            SONGS_ARTIST_HOTTTNESSS + " INTEGER," +
            SONGS_ARTIST_ALBUMS + " TEXT" +
            ")";

    @Override
    public boolean onCreate()
    {
        mDBHelper = new DatabaseHelper(getContext());

        mDatabase = mDBHelper.getWritableDatabase();

        return mDatabase != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        /* Le constructeur de requête */
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        /* Si on récupère les playlists ou les sons */
        switch(mUriMatcher.match(uri))
        {
            /* Les playlists */
            case PLAYLISTS:

                queryBuilder.setTables(TABLE_PLAYLISTS);
                sortOrder = PLAYLISTS_TITLE;

                break;

            /* Les sons */
            case SONGS:

                queryBuilder.setTables(TABLE_SONGS);

                break;

            /* Erreur */
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /* Exécution */
        Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);

        /* register to watch a content URI for changes */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        /* Retour des résultats */
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        /* Récupération de l'id */
        int id = mUriMatcher.match(uri);

        /* La table et le content uri*/
        String table = null;
        Uri contentUri = null;

        if(id == PLAYLISTS)
        {
            table = TABLE_PLAYLISTS;
            contentUri = CONTENT_URI_PLAYLISTS;
        }
        else if(id == SONGS)
        {
            table = TABLE_SONGS;
            contentUri = CONTENT_URI_SONGS;
        }

        /* Insertion et récupération de l'id inséré */
        long row = mDatabase.insert(table, "", contentValues);

        /* Ligne bien insérée */
        if(row > 0)
        {
            Uri newUri = ContentUris.withAppendedId(contentUri, row);
            getContext().getContentResolver().notifyChange(newUri, null);

            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int count;

        switch(mUriMatcher.match(uri))
        {
            case PLAYLISTS:

                count = mDatabase.delete(TABLE_PLAYLISTS, selection, selectionArgs);

                break;

            case SONGS:

                count = mDatabase.delete(TABLE_SONGS, selection, selectionArgs);

                break;

            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }
}
