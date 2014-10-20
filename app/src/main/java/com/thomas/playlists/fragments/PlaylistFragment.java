package com.thomas.playlists.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

import com.echonest.api.v4.Playlist;
import com.thomas.playlists.PlayListLoader;
import com.thomas.playlists.PlaylistAdapter;
import com.thomas.playlists.PlaylistSearch;
import com.thomas.playlists.R;
import com.thomas.playlists.listeners.SavePlaylistListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaylistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PlaylistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Playlist>
{
    private OnFragmentInteractionListener mListener;
    private int PLAYLIST_LOADER_ID = 1;
    private PlaylistSearch playlistSearch = null;
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
        AbsListView mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;
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

    public void search(PlaylistSearch pPlaylistSearch)
    {
        playlistSearch = pPlaylistSearch;
        getLoaderManager().initLoader(PLAYLIST_LOADER_ID, null, this);
    }

    @Override
    public Loader<Playlist> onCreateLoader(int i, Bundle bundle)
    {
        /* Déclaration et initialisation du loader */
        PlayListLoader loader = new PlayListLoader(getActivity());

        /* Ajout du playlistSearch */
        loader.setPlaylistSearch(playlistSearch);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Playlist> objectLoader, Playlist playlist)
    {
        if(playlist != null)
        {
            /* Ajout des morceaux */
            mAdapter.addAll(playlist.getSongs());

            /* Ajout du listener pour l'enregistrement de la playlist */
            Button saveButton = (Button) getActivity().findViewById(R.id.savePlaylist);
            saveButton.setOnClickListener(new SavePlaylistListener());
        }
        /* Sinon affichage des erreurs */
        else
            Toast.makeText(getActivity(), "Une erreur s'est produite avec l'API", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<Playlist> objectLoader)
    {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
    }

}
