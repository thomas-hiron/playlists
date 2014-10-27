package com.thomas.playlists.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.adapters.PlaylistAdapter;
import com.thomas.playlists.interfaces.OnPlaylistItemClicked;
import com.thomas.playlists.listeners.SavePlaylistListener;
import com.thomas.playlists.sqlite.ContentProvider;
import com.thomas.playlists.sqlite.PlaylistItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnPlaylistItemClicked} interface
 * to handle interaction events.
 */
public class ExistingPlaylistFragment extends Fragment
{

    private PlaylistItem mPlaylistItem;
    private OnPlaylistItemClicked mListener;
    private PlaylistAdapter mAdapter;

    public ExistingPlaylistFragment(PlaylistItem playlistItem)
    {
        mPlaylistItem = playlistItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_list, container, false);

        /* Initialisation de l'adapter */
        mAdapter = new PlaylistAdapter(view.getContext());

        /* Récupération des sons */
        int playlistId = mPlaylistItem.getId();
        String condition = ContentProvider.SONGS_PLAYLISTS_ID + " = " + playlistId;

        /* On détecte la présence de playlists */
        String URL = ContentProvider.URL_SONGS;
        Uri playlistsUri = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(playlistsUri, null, condition, null, null);

        /* Tableau des sons */
        ArrayList<PlaylistSong> playlistSongs = new ArrayList<PlaylistSong>();

        if(c.moveToFirst())
        {
            do
            {
                /* Récupération des données */
                int songId = c.getInt(c.getColumnIndex(ContentProvider.SONGS_ID));
                String title = c.getString(c.getColumnIndex(ContentProvider.SONGS_TITLE));
                String cover = c.getString(c.getColumnIndex(ContentProvider.SONGS_COVER));
                double duration = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_DURATION));
                double danceability = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_DANCEABILITY));
                double tempo = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_TEMPO));
                double hotttnesss = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_HOTTTNESSS));
                double loudness = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_LOUDNESS));
                String artistName = c.getString(c.getColumnIndex(ContentProvider.SONGS_ARTIST_NAME));
                String artistLocation = c.getString(c.getColumnIndex(ContentProvider.SONGS_ARTIST_LOCATION));
                double artistHotttnesss = c.getDouble(c.getColumnIndex(ContentProvider.SONGS_ARTIST_HOTTTNESSS));
                String[] artistAlbums = c.getString(c.getColumnIndex(ContentProvider.SONGS_ARTIST_ALBUMS)).split("\\[azerty\\]");

                PlaylistSong playlistSong = new PlaylistSong(null);

                /* Pour la vue simple */
                playlistSong.setCover(cover);
                playlistSong.setArtistName(artistName);
                playlistSong.setTitle(title);
                playlistSong.setId(songId);

                /* Pour la vue détaillée */
                playlistSong.setDuration(duration);
                playlistSong.setDanceability(danceability);
                playlistSong.setTempo(tempo);
                playlistSong.setHotttnesss(hotttnesss);
                playlistSong.setLoudness(loudness);
                playlistSong.setArtistLocation(artistLocation);
                playlistSong.setArtistHotttnesss(artistHotttnesss);
                playlistSong.setArtistAlbums(artistAlbums);

                /* Ajout au tableau */
                playlistSongs.add(playlistSong);
            }
            while(c.moveToNext());

            /* Ajout à l'adapter */
            mAdapter.addAll(playlistSongs);
        }

        /* Set the adapter */
        AbsListView mListView = (AbsListView) view.findViewById(R.id.listPlaylistResults);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                mListener.onPlaylistItemClicked(mAdapter.getItem(i));
            }
        });

        /* Changement du titre ('Résultats') par celui de la playlist */
        ((TextView) view.findViewById(R.id.playlistResultsLabel)).setText(mPlaylistItem.getTitle());

        /* Ajout du listener sur le bouton */
        Button saveButton = (Button) view.findViewById(R.id.savePlaylist);
        SavePlaylistListener savePlaylistListener = new SavePlaylistListener(
                mAdapter, getActivity(), saveButton, view.findViewById(R.id.playlistResultsLabel)
        );
        saveButton.setOnClickListener(savePlaylistListener);

        /* Changement de l'icone favorite par trash */
        saveButton.setBackgroundResource(R.drawable.trash);

        /* Ajout de l'id de la playlist */
        savePlaylistListener.setPlaylistId(playlistId);

        /* Suppression du chargement */
        view.findViewById(R.id.loadingResults).setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnPlaylistItemClicked) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
}
