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
import android.widget.TextView;

import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.adapters.PlaylistAdapter;
import com.thomas.playlists.interfaces.OnPlaylistItemClicked;
import com.thomas.playlists.sqlite.MyContentProvider;
import com.thomas.playlists.sqlite.PlaylistItem;

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
        String condition = MyContentProvider.SONGS_PLAYLISTS_ID + " = " + playlistId;

        /* On détecte la présence de playlists */
        String URL = MyContentProvider.URL_SONGS;
        Uri playlistsUri = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(playlistsUri, null, condition, null, null);

        if(c.moveToFirst())
        {
            do
            {
                /* Récupération des données */
                String title = c.getString(c.getColumnIndex(MyContentProvider.SONGS_TITLE));
                String cover = c.getString(c.getColumnIndex(MyContentProvider.SONGS_COVER));
                double duration = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_DURATION)));
                double danceability = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_DANCEABILITY)));
                double tempo = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_TEMPO)));
                double hotttnesss = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_HOTTTNESSS)));
                double loudness = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_LOUDNESS)));
                String artistName = c.getString(c.getColumnIndex(MyContentProvider.SONGS_ARTIST_NAME));
                String artistLocation = c.getString(c.getColumnIndex(MyContentProvider.SONGS_ARTIST_LOCATION));
                double artistHotttnesss = Double.parseDouble(c.getString(c.getColumnIndex(MyContentProvider.SONGS_ARTIST_HOTTTNESSS)));
                String[] artistAlbums = c.getString(c.getColumnIndex(MyContentProvider.SONGS_ARTIST_ALBUMS)).split("\\[azerty\\]");

                PlaylistSong playlistSong = new PlaylistSong(null);

                /* Pour la vue simple */
                playlistSong.setCover(cover);
                playlistSong.setArtistName(artistName);
                playlistSong.setTitle(title);

                /* Pour la vue détaillée */
                playlistSong.setDuration(duration);
                playlistSong.setDanceability(danceability);
                playlistSong.setTempo(tempo);
                playlistSong.setHotttnesss(hotttnesss);
                playlistSong.setLoudness(loudness);
                playlistSong.setArtistLocation(artistLocation);
                playlistSong.setArtistHotttnesss(artistHotttnesss);
                playlistSong.setArtistAlbums(artistAlbums);

                mAdapter.add(playlistSong);
            }
            while(c.moveToNext());
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
