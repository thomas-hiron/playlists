package com.thomas.playlists.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.thomas.playlists.DialogPlaylistTitle;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.sqlite.MyContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThomasHiron on 20/10/2014.
 */
public class SavePlaylistListener implements View.OnClickListener
{
    private ArrayList<PlaylistSong> mSongsSQL;
    private FragmentActivity mPlaylistFragment;
    private TextView mTitle;
    private Button mIcon;
    private List<Song> mSongsEchoNest = null;
    private boolean mSaved;
    private int mPlaylistId;

    /**
     * Constructeur appelé suite à un retour de résultats de l'api
     *
     * @param songs
     * @param playlistFragment
     */
    public SavePlaylistListener(List<Song> songs, FragmentActivity playlistFragment)
    {
        mPlaylistFragment = playlistFragment;
        mSongsEchoNest = songs;

        mTitle = (TextView) mPlaylistFragment.findViewById(R.id.playlistResultsLabel);
        mIcon = (Button) mPlaylistFragment.findViewById(R.id.savePlaylist);
        mSaved = false;
    }

    /**
     * Constructeur appelé grâce à une playlist existante
     *
     * @param playlistSongs
     * @param playlistFragment
     * @param saveButton
     * @param title
     */
    public SavePlaylistListener(ArrayList<PlaylistSong> playlistSongs, FragmentActivity playlistFragment, Button saveButton, View title)
    {
        mPlaylistFragment = playlistFragment;
        mSongsSQL = playlistSongs;

        mIcon = saveButton;
        mTitle = (TextView) title;

        mSaved = true;
    }

    @Override
    public void onClick(View view)
    {
        /* Suppression */
        if(mSaved)
            deletePlaylist();
        /* Ajout d'un dialogue pour renseigner le titre */
        else
            new DialogPlaylistTitle(view.getContext(), this);
    }

    private void deletePlaylist()
    {
        /* Les conditions */
        String songCondition = MyContentProvider.SONGS_PLAYLISTS_ID + " = " + mPlaylistId;
        String playlistCondition = MyContentProvider.PLAYLISTS_ID + " = " + mPlaylistId;

        /* Suppression des sons */
        mPlaylistFragment.getContentResolver().delete(MyContentProvider.CONTENT_URI_SONGS, songCondition, null);

        /* Suppression de la playlist */
        mPlaylistFragment.getContentResolver().delete(MyContentProvider.CONTENT_URI_PLAYLISTS, playlistCondition, null);

        /* On change le titre */
        mTitle.setText(R.string.results);

            /* On change l'icone */
        mIcon.setBackgroundResource(R.drawable.favorite);

        /* Etat non sauvegardé */
        mSaved = false;

        /* Toast */
        Toast.makeText(mPlaylistFragment, mPlaylistFragment.getString(R.string.playlistRemoved), Toast.LENGTH_LONG).show();
    }

    /**
     * Titre renseigné, on enregistre la playlist et les morceaux
     *
     * @param title
     */
    public void dialogValidated(String title)
    {
        /* Le context */
        Context context = mPlaylistFragment;

        /* Première lettre en majuscule */
        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();

        /* Les valeurs à insérer */
        ContentValues playlistValues = new ContentValues();
        playlistValues.put(MyContentProvider.PLAYLISTS_TITLE, title);

        /* Insertion */
        Uri uri = context.getContentResolver().insert(MyContentProvider.CONTENT_URI_PLAYLISTS, playlistValues);

        /* On récupère l'id */
        List<String> pathSegments = uri.getPathSegments();
        mPlaylistId = Integer.parseInt(pathSegments.get(pathSegments.size() - 1));

        /* Insertion des sons */
        if(mSongsEchoNest == null)
        {
            /* On prend les sons SQL */
            for(PlaylistSong song : mSongsSQL)
                insertSong(song, context);
        }
        else
        {
            /* On prend les sons EchoNest */
            for(Song song : mSongsEchoNest)
                insertSong(song, context);
        }

        /* On change le titre */
        mTitle.setText(title);

        /* On change l'icone */
        mIcon.setBackgroundResource(R.drawable.trash);

        /* Etat sauvegardé */
        mSaved = true;

        /* Toast */
        Toast.makeText(mPlaylistFragment, context.getString(R.string.playlistSaved), Toast.LENGTH_LONG).show();
    }

    /**
     * Insertion des sons si suppression/ajout
     *
     * @param song
     * @param context
     */
    private void insertSong(PlaylistSong song, Context context)
    {
        /* Les valeurs à insérer */
        ContentValues songValues = new ContentValues();

        /* Ajout des valeurs */
        songValues.put(MyContentProvider.SONGS_PLAYLISTS_ID, mPlaylistId);
        songValues.put(MyContentProvider.SONGS_TITLE, song.getTitle());
        songValues.put(MyContentProvider.SONGS_ARTIST_NAME, song.getArtistName());

        songValues.put(MyContentProvider.SONGS_DURATION, song.getDuration());
        songValues.put(MyContentProvider.SONGS_DANCEABILITY, song.getDanceability());
        songValues.put(MyContentProvider.SONGS_TEMPO, song.getTempo());
        songValues.put(MyContentProvider.SONGS_HOTTTNESSS, song.getHotttnesss());
        songValues.put(MyContentProvider.SONGS_LOUDNESS, song.getLoudness());
        songValues.put(MyContentProvider.SONGS_ARTIST_LOCATION, song.getArtistLocation());
        songValues.put(MyContentProvider.SONGS_ARTIST_HOTTTNESSS, song.getArtistHotttnesss());

        /* Les albums et le cover */
        String sAlbums = "";

        /* Les albums */
        String[] albums = song.getArtistAlbums();
        String album = "";

        /* Création de la chaine */
        for(String s : albums)
            sAlbums += s + "[azerty]";

        /* Ajout du cover */
        songValues.put(MyContentProvider.SONGS_COVER, song.getCover());

        /* Ajout des albums */
        songValues.put(MyContentProvider.SONGS_ARTIST_ALBUMS, sAlbums);

        /* Insertion */
        context.getContentResolver().insert(MyContentProvider.CONTENT_URI_SONGS, songValues);
    }

    /**
     * Insertion des sons si EchoNest
     *
     * @param song
     * @param context
     */
    private void insertSong(Song song, Context context)
    {
        /* Les valeurs à insérer */
        ContentValues songValues = new ContentValues();

        /* Ajout des valeurs */
        songValues.put(MyContentProvider.SONGS_PLAYLISTS_ID, mPlaylistId);
        songValues.put(MyContentProvider.SONGS_TITLE, song.getTitle());
        songValues.put(MyContentProvider.SONGS_ARTIST_NAME, song.getArtistName());

        try
        {
            songValues.put(MyContentProvider.SONGS_DURATION, song.getDuration());
            songValues.put(MyContentProvider.SONGS_DANCEABILITY, song.getDanceability());
            songValues.put(MyContentProvider.SONGS_TEMPO, song.getTempo());
            songValues.put(MyContentProvider.SONGS_HOTTTNESSS, song.getSongHotttnesss());
            songValues.put(MyContentProvider.SONGS_LOUDNESS, song.getLoudness());
            songValues.put(MyContentProvider.SONGS_ARTIST_LOCATION, song.getArtistLocation().getPlaceName());
            songValues.put(MyContentProvider.SONGS_ARTIST_HOTTTNESSS, song.getArtistHotttnesss());
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

    public void setPlaylistId(int id)
    {
        mPlaylistId = id;
    }
}
