package com.thomas.playlists.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thomas.playlists.R;
import com.thomas.playlists.interfaces.OnHomeButtonClicked;
import com.thomas.playlists.listeners.HomeButtonsListener;
import com.thomas.playlists.sqlite.MyContentProvider;
import com.thomas.playlists.sqlite.PlaylistItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnHomeButtonClicked} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment
{
    private final String NO_PLAYLIST_SAVED = "Aucune playlist enregistrée.\n" +
            "Appuyez sur le bouton '+' pour créer une nouvelle playlist.";

    private OnHomeButtonClicked mListener = null;

    private HomeButtonsListener mHomeButtonsListener = null;

    public HomeFragment()
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /* On détecte la présence de playlists */
        String URL = MyContentProvider.URL_PLAYLISTS;
        Uri playlistsUri = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(playlistsUri, null, null, null, null);

        /* La liste des playlists */
        ArrayList<PlaylistItem> playlists = new ArrayList<PlaylistItem>();

        if(c.moveToFirst())
        {
            do
            {
                /* Création de l'objet */
                PlaylistItem playlistItem = new PlaylistItem();
                playlistItem.setId(Integer.parseInt(c.getString(c.getColumnIndex(MyContentProvider.PLAYLISTS_ID))));
                playlistItem.setTitle(c.getString(c.getColumnIndex(MyContentProvider.PLAYLISTS_TITLE)));

                /* Ajout à la liste */
                playlists.add(playlistItem);
            }
            while(c.moveToNext());
        }

        /* Si aucune, on affiche un message */
        if(playlists.size() == 0)
            this.addNoPlaylist(view);

        /* Récupération des boutons */
        Button addPlaylist = (Button) view.findViewById(R.id.addPlaylist);
        Button shufflePlaylist = (Button) view.findViewById(R.id.shufflePlaylist);

        /* Création du listener des boutons */
        mHomeButtonsListener = new HomeButtonsListener(mListener);

        /* Ajout des listener */
        addPlaylist.setOnClickListener(mHomeButtonsListener);
        shufflePlaylist.setOnClickListener(mHomeButtonsListener);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnHomeButtonClicked) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHomeButtonClicked");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * Aucune playlist enregistrée, on informe l'utilisateur
     *
     * @param view La vue
     */
    private void addNoPlaylist(View view)
    {
        /* Création du TextView et ajout des paramètres */
        TextView tv = new TextView(getActivity());
        tv.setText(NO_PLAYLIST_SAVED);
        tv.setTypeface(null, Typeface.ITALIC);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setPadding(0, 50, 0, 0);

        /* Le container */
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.homeContainer);

        /* Ajout du textView */
        frameLayout.addView(tv);
    }
}
