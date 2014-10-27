package com.thomas.playlists.listeners;

import android.view.View;
import android.widget.Button;

import com.thomas.playlists.R;
import com.thomas.playlists.interfaces.OnHomeButtonClicked;

/**
 * Created by ThomasHiron on 16/10/2014.
 *
 * Au clic sur Ajouter playlist ou Playlist aléatoire
 */
public class HomeButtonsListener implements View.OnClickListener
{
    public static final String ACTION_ADD_PLAYLIST = "ADD_PLAYLIST";
    public static final String ACTION_SHUFFLE_PLAYLIST = "SHUFFLE_PLAYLIST";

    /* On garde une référence vers l'activité principale */
    private OnHomeButtonClicked mListener = null;

    public HomeButtonsListener(OnHomeButtonClicked homeButtonClickedListener)
    {
        mListener = homeButtonClickedListener;
    }

    @Override
    public void onClick(View view)
    {
        /* Le bouton cliqué */
        Button b = (Button) view;

        /* L'action à effectuer */
        String action = (b.getId() == R.id.addPlaylist) ? ACTION_ADD_PLAYLIST : ACTION_SHUFFLE_PLAYLIST;

        /* Renvoie vers l'activité principale */
        mListener.onHomeButtonClicked(action);
    }
}
