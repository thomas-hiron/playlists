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
import com.thomas.playlists.adapters.PlaylistAdapter;
import com.thomas.playlists.sqlite.ContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThomasHiron on 20/10/2014.
 *
 * Gère la sauvegarde et suppression d'une playlist
 */
public class SavePlaylistListener implements View.OnClickListener
{
    private PlaylistAdapter mSongsSQL;
    private FragmentActivity mPlaylistFragment;
    private TextView mTitle;
    private Button mIcon;
    private PlaylistAdapter mSongsEchoNestAdapter = null;
    private boolean mSaved;
    private int mPlaylistId;

    /**
     * Constructeur appelé suite à un retour de résultats de l'api
     *
     * @param playlistAdapter L'adapteur qui contient les morceaux
     * @param playlistFragment Le fragment qui a généré le clic
     */
    public SavePlaylistListener(PlaylistAdapter playlistAdapter, FragmentActivity playlistFragment)
    {
        mPlaylistFragment = playlistFragment;

        /* Ajout de l'adapter, on le garde en mémoire au cas où des sons soient supprimés */
        mSongsEchoNestAdapter = playlistAdapter;

        mTitle = (TextView) mPlaylistFragment.findViewById(R.id.playlistResultsLabel);
        mIcon = (Button) mPlaylistFragment.findViewById(R.id.savePlaylist);
        mSaved = false;
    }

    /**
     * Constructeur appelé grâce à une playlist existante
     *
     * @param playlistAdapter L'adapteur qui contient les morceaux
     * @param playlistFragment Le fragment qui a généré le clic
     * @param saveButton Le bouton Enregistrer
     * @param title Le titre du fragment dans le bandeau
     */
    public SavePlaylistListener(PlaylistAdapter playlistAdapter, FragmentActivity playlistFragment, Button saveButton, View title)
    {
        mPlaylistFragment = playlistFragment;

        /* Ajout de l'adapter, on le garde en mémoire au cas où des sons soient supprimés */
        mSongsSQL = playlistAdapter;

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
        String songCondition = ContentProvider.SONGS_PLAYLISTS_ID + " = " + mPlaylistId;
        String playlistCondition = ContentProvider.PLAYLISTS_ID + " = " + mPlaylistId;

        /* Suppression des sons */
        mPlaylistFragment.getContentResolver().delete(ContentProvider.CONTENT_URI_SONGS, songCondition, null);

        /* Suppression de la playlist */
        mPlaylistFragment.getContentResolver().delete(ContentProvider.CONTENT_URI_PLAYLISTS, playlistCondition, null);

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
     * @param title Le titre de la playlist à enregistrer
     */
    public void dialogValidated(String title)
    {
        /* Le context */
        Context context = mPlaylistFragment;

        /* Première lettre en majuscule */
        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();

        /* Les valeurs à insérer */
        ContentValues playlistValues = new ContentValues();
        playlistValues.put(ContentProvider.PLAYLISTS_TITLE, title);

        /* Insertion */
        Uri uri = context.getContentResolver().insert(ContentProvider.CONTENT_URI_PLAYLISTS, playlistValues);

        /* On récupère l'id */
        List<String> pathSegments = uri.getPathSegments();
        mPlaylistId = Integer.parseInt(pathSegments.get(pathSegments.size() - 1));

        /* Insertion des sons */
        if(mSongsEchoNestAdapter == null)
        {
            /* On prend les sons SQL */
            for(int i = 0, l = mSongsSQL.getCount(); i < l; ++i)
                insertSong(mSongsSQL.getItem(i), context);
        }
        else
        {
            /* On prend les sons EchoNest */
            for(int i = 0, l = mSongsEchoNestAdapter.getCount(); i < l; ++i)
                insertSongEchoNest(mSongsEchoNestAdapter.getItem(i), context);
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
     * @param song Le son à insérer dans la BDD
     * @param context Le contexte
     */
    private void insertSong(PlaylistSong song, Context context)
    {
        /* Les valeurs à insérer */
        ContentValues songValues = new ContentValues();

        /* Ajout des valeurs */
        songValues.put(ContentProvider.SONGS_PLAYLISTS_ID, mPlaylistId);
        songValues.put(ContentProvider.SONGS_TITLE, song.getTitle());
        songValues.put(ContentProvider.SONGS_ARTIST_NAME, song.getArtistName());

        songValues.put(ContentProvider.SONGS_DURATION, song.getDuration());
        songValues.put(ContentProvider.SONGS_DANCEABILITY, song.getDanceability());
        songValues.put(ContentProvider.SONGS_TEMPO, song.getTempo());
        songValues.put(ContentProvider.SONGS_HOTTTNESSS, song.getHotttnesss());
        songValues.put(ContentProvider.SONGS_LOUDNESS, song.getLoudness());
        songValues.put(ContentProvider.SONGS_ARTIST_LOCATION, song.getArtistLocation());
        songValues.put(ContentProvider.SONGS_ARTIST_HOTTTNESSS, song.getArtistHotttnesss());

        /* Les albums et le cover */
        String sAlbums = "";

        /* Les albums */
        String[] albums = song.getArtistAlbums();

        /* Création de la chaine */
        for(String s : albums)
            sAlbums += s + "[azerty]";

        /* Ajout du cover */
        songValues.put(ContentProvider.SONGS_COVER, song.getCover());

        /* Ajout des albums */
        songValues.put(ContentProvider.SONGS_ARTIST_ALBUMS, sAlbums);

        /* Insertion */
        Uri uri = context.getContentResolver().insert(ContentProvider.CONTENT_URI_SONGS, songValues);

        /* Ajout de l'id */
        song.setId(getSongId(uri));
    }

    /**
     * Insertion des sons si EchoNest
     *
     * @param playlistSong Le son à insérer dans la BDD
     * @param context Le contexte
     */
    private void insertSongEchoNest(PlaylistSong playlistSong, Context context)
    {
        /* Les valeurs à insérer */
        ContentValues songValues = new ContentValues();

        /* Le son */
        Song song = playlistSong.getSong();

        /* Ajout des valeurs */
        songValues.put(ContentProvider.SONGS_PLAYLISTS_ID, mPlaylistId);
        songValues.put(ContentProvider.SONGS_TITLE, song.getTitle());
        songValues.put(ContentProvider.SONGS_ARTIST_NAME, song.getArtistName());

        try
        {
            songValues.put(ContentProvider.SONGS_DURATION, song.getDuration());
            songValues.put(ContentProvider.SONGS_DANCEABILITY, song.getDanceability());
            songValues.put(ContentProvider.SONGS_TEMPO, song.getTempo());
            songValues.put(ContentProvider.SONGS_HOTTTNESSS, song.getSongHotttnesss());
            songValues.put(ContentProvider.SONGS_LOUDNESS, song.getLoudness());
            songValues.put(ContentProvider.SONGS_ARTIST_LOCATION, song.getArtistLocation().getPlaceName());
            songValues.put(ContentProvider.SONGS_ARTIST_HOTTTNESSS, song.getArtistHotttnesss());
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
        String album;

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

            songValues.put(ContentProvider.SONGS_COVER, cover);
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        /* Ajout des albums */
        songValues.put(ContentProvider.SONGS_ARTIST_ALBUMS, sAlbums);

        /* Insertion */
        Uri uri = context.getContentResolver().insert(ContentProvider.CONTENT_URI_SONGS, songValues);

        /* Ajout de l'id */
        playlistSong.setId(getSongId(uri));
    }

    /**
     * Retourne l'id inséré
     *
     * @param uri L'uri générée suite à l'insertion
     * @return L'id inséré
     */
    private int getSongId(Uri uri)
    {
        List<String> pathSegments = uri.getPathSegments();
        return Integer.parseInt(pathSegments.get(pathSegments.size() - 1));
    }

    public void setPlaylistId(int id)
    {
        mPlaylistId = id;
    }
}
