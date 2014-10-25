package com.thomas.playlists.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.thomas.playlists.DialogPlaylistTitle;
import com.thomas.playlists.sqlite.MainDatabaseHelper;
import com.thomas.playlists.sqlite.MyContentProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThomasHiron on 20/10/2014.
 */
public class SavePlaylistListener implements View.OnClickListener
{
    private List<Song> mSongs;
    private FragmentManager mPlaylistFragmentManager;
    private final String DIALOG_TAG = "TITLE";


    public SavePlaylistListener(FragmentManager playlistFragment, List<Song> songs)
    {
        mPlaylistFragmentManager = playlistFragment;
        mSongs = songs;
    }

    @Override
    public void onClick(View view)
    {
        /* Ajout d'un dialogue pour renseigner le titre */
        DialogPlaylistTitle dialogPlaylistTitle = new DialogPlaylistTitle(view.getContext(), this);
    }

    /**
     * Titre renseigné, on enregistre la playlist et les morceaux
     *
     * @param title
     * @param context
     */
    public void dialogValidated(String title, Context context)
    {
        /* Enregistrement du titre et récupération de l'id inséré dans un premier temps */
        MainDatabaseHelper playlists_db = new MainDatabaseHelper(context);

        /* Les valeurs à insérer */
        ContentValues playlistValues = new ContentValues();
        playlistValues.put(MyContentProvider.PLAYLISTS_TITLE, title);

        /* Insertion */
        Uri uri = context.getContentResolver().insert(MyContentProvider.CONTENT_URI_PLAYLISTS, playlistValues);

        /* On récupère l'id */
        List<String> pathSegments = uri.getPathSegments();
        int playlistId = Integer.parseInt(pathSegments.get(pathSegments.size() - 1));

        /* Insertion des sons */
        for(Song song : mSongs)
        {
            /* Les valeurs à insérer */
            ContentValues songValues = new ContentValues();

            /* Ajout des valeurs */
            songValues.put(MyContentProvider.SONGS_PLAYLISTS_ID, playlistId);
            songValues.put(MyContentProvider.SONGS_TITLE, "tests de ouf avec un texte plus long que le titre du morceau !!!");

            try
            {
                /* La durée */
                /* Formatage de la durée reçue en secondes vers mm:ss */
                Date date = new Date((long) (song.getDuration() * 1000));
                String formattedDate = new SimpleDateFormat("mm:ss").format(date);
                songValues.put(MyContentProvider.SONGS_DURATION, formattedDate);

                songValues.put(MyContentProvider.SONGS_DANCEABILITY, (int) (song.getDanceability() * 10));
                songValues.put(MyContentProvider.SONGS_TEMPO, (int) (song.getTempo()));
                songValues.put(MyContentProvider.SONGS_HOTTTNESSS, (int) (song.getSongHotttnesss() * 100));
                songValues.put(MyContentProvider.SONGS_LOUDNESS, (int) (song.getLoudness()));
                songValues.put(MyContentProvider.SONGS_ARTIST_LOCATION, song.getArtistLocation().getPlaceName());
                songValues.put(MyContentProvider.SONGS_ARTIST_HOTTTNESSS, (int) (song.getArtistHotttnesss()));
            }
            catch(EchoNestException e)
            {
                e.printStackTrace();
            }

            /* Les albums et le cover */
            String sAlbums = "";
            String cover = "";

            /* Compteur */
            int cpt = 0;

            /* Les albums */
            ArrayList<String> albums = new ArrayList<String>();
            String album = "";

            try
            {

                /*
                 * Lorsque le compteur aura dépassé la taille du tableau, une exception sera levée
                 */
                while(true)
                {
                    album = song.getString("tracks[" + cpt + "].album_name");

                    if(album != null && albums.indexOf(album) == -1)
                        albums.add(album);

                    ++cpt;
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }

            /* Création de la chaine */
            for(String s : albums)
                sAlbums += s + "[azerty]";

            /* L'image de l'artiste */
            try
            {
                cpt = 0;

                while((cover = song.getString("tracks[" + cpt + "].release_image")) == null)
                    ++cpt;

                songValues.put(MyContentProvider.SONGS_COVER, cover);
            }
            catch(IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }

            /* Ajout des albums */
            songValues.put(MyContentProvider.SONGS_ARTIST_ALBUMS, sAlbums);

            /* Insertion */
            context.getContentResolver().insert(MyContentProvider.CONTENT_URI_SONGS, songValues);
        }
    }
}
