package com.thomas.playlists.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;
import com.thomas.playlists.PlaylistSearch;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.adapters.PlaylistAdapter;
import com.thomas.playlists.interfaces.OnPlaylistItemClicked;
import com.thomas.playlists.listeners.SavePlaylistListener;
import com.thomas.playlists.loaders.PlayListLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnPlaylistItemClicked} interface
 * to handle interaction events.
 */
public class PlaylistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Playlist>
{
    private static int PLAYLIST_LOADER_ID = 1;

    private OnPlaylistItemClicked mListener;
    private PlaylistSearch mPlaylistSearch = null;
    private PlaylistAdapter mAdapter = null;

    public PlaylistFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Instanciation de l'adapter */
        mAdapter = new PlaylistAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_results, container, false);

        // Set the adapter
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
                    + " must implement OnPlaylistItemClicked");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public void search(PlaylistSearch pPlaylistSearch)
    {
        mPlaylistSearch = pPlaylistSearch;
        getLoaderManager().initLoader(PLAYLIST_LOADER_ID, null, this);
    }

    @Override
    public Loader<Playlist> onCreateLoader(int i, Bundle bundle)
    {
        /* Déclaration et initialisation du loader */
        PlayListLoader loader = new PlayListLoader(getActivity());

        /* Ajout du playlistSearch */
        loader.setPlaylistSearch(mPlaylistSearch);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Playlist> objectLoader, Playlist playlist)
    {
        if(playlist != null)
        {
            /* Ajout des morceaux */
            for(Song song : playlist.getSongs())
                mAdapter.add(new PlaylistSong(song));

            /* Ajout du listener pour l'enregistrement de la playlist */
            Button saveButton = (Button) getActivity().findViewById(R.id.savePlaylist);
            saveButton.setOnClickListener(new SavePlaylistListener(playlist.getSongs(), getActivity()));
        }
        /* Sinon affichage des erreurs */
        else
            Toast.makeText(getActivity(), "Une erreur s'est produite avec l'API", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<Playlist> objectLoader)
    {

    }
}
