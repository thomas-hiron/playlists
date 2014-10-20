package com.thomas.playlists.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thomas.playlists.R;
import com.thomas.playlists.interfaces.OnPlaylistAdded;
import com.thomas.playlists.listeners.AddPlaylistListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnPlaylistAdded} interface
 * to handle interaction events.
 */
public class AddPlaylistFragment extends Fragment
{
    private OnPlaylistAdded mListener;

    public AddPlaylistFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_playlist, container, false);

        /* Ajout du listener au clic sur le bouton */
        Button validate = (Button) view.findViewById(R.id.validAddPlaylist);
        validate.setOnClickListener(new AddPlaylistListener(mListener, view));

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnPlaylistAdded) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPlaylistAdded");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
}
