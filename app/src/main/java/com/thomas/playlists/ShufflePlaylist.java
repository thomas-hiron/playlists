package com.thomas.playlists;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.thomas.playlists.sqlite.ContentProvider;

/**
 * Objet qui permet d'obtenir un artiste aléatoirement pour effectuer une requête
 */
public class ShufflePlaylist
{
    private String[] artists = {"Jimi Hendrix", "Led Zeppelin", "The Rolling Stones", "Iron Maiden", "Pink Floyd"};

    public String getRandomArtistName(Context context)
    {
        /* Récupération d'un artiste au hasard */
        /* On détecte la présence de playlists */
        String URL = ContentProvider.URL_SONGS;
        Uri playlistsUri = Uri.parse(URL);
        Cursor c = context.getContentResolver().query(playlistsUri, null, null, null, "RANDOM()");

        /* Le nom de l'artiste */
        String artistName;

        /* S'il y a des insertions */
        if(c.moveToFirst())
            artistName = c.getString(c.getColumnIndex(ContentProvider.SONGS_ARTIST_NAME));
        /* Sinon on en prend un aléatoire dans le tableau */
        else
        {
            int index = (int) (Math.random() * artists.length);
            artistName = artists[index];
        }

        return artistName;
    }
}
