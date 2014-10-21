package com.thomas.playlists.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.adapters.AlbumsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment
{
    private PlaylistSong mSong;

    public ArtistDetailFragment(PlaylistSong song)
    {
        mSong = song;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_detail, container, false);

        /* Le son de l'API */
        Song song = mSong.getSong();

        /* Changement du titre */
        ((TextView) view.findViewById(R.id.artistDetailTitle)).setText(song.getArtistName());


        /* Location */
        try
        {
            ((TextView) view.findViewById(R.id.artistDetailLocation)).setText(
                    "Location : " + song.getArtistLocation().getPlaceName()
            );
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Hotttnesss */
        try
        {
            ((TextView) view.findViewById(R.id.artistDetailHotttnesss)).setText("Hotttnesss : " + (int) (song.getArtistHotttnesss() * 100) + "%");
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Les albums */
        ArrayList<String> albums = new ArrayList<String>();
        String album = "";

        try
        {
            /* Compteur */
            int cpt = 0;

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

        /* La listView */
        ListView listAlbums = (ListView) view.findViewById(R.id.artistDetailAlbums);

        /* S'il y a des albums */
        if(albums.size() > 0)
        {
            /* L'adapter */
            AlbumsAdapter albumAdapter = new AlbumsAdapter(getActivity());
            albumAdapter.addAll(albums);

            /* Ajout de l'adapter */
            listAlbums.setAdapter(albumAdapter);
        }
        /* Sinon on cache la liste et le label */
        else
        {
            view.findViewById(R.id.albumsLabel).setVisibility(View.GONE);
            listAlbums.setVisibility(View.GONE);
        }

        return view;
    }


}
