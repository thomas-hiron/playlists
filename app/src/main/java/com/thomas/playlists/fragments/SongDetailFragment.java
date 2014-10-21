package com.thomas.playlists.fragments;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.squareup.picasso.Picasso;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.interfaces.OnArtistClicked;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnArtistClicked} interface
 * to handle interaction events.
 */
public class SongDetailFragment extends Fragment
{

    private OnArtistClicked mListener;
    private PlaylistSong mSong = null;

    public SongDetailFragment(PlaylistSong song)
    {
        mSong = song;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_detail, container, false);

        /* Le son d'EchoNest */
        Song song = mSong.getSong();

        /* Ajout du titre du bandeau */
        ((TextView) view.findViewById(R.id.songDetailTitle)).setText(song.getTitle());


        /* L'image et la source */
        ImageView imageView = (ImageView) view.findViewById(R.id.songDetailCover);
        String cover = mSong.getCover();

        /* Ajout ou suppression de l'image */
        if(cover != null && !cover.equals(""))
            Picasso.with(view.getContext()).load(cover).into(imageView);
        else
            imageView.setVisibility(View.GONE);


        /* Ajout de l'artiste */
        TextView artist = (TextView) view.findViewById(R.id.songDetailArtist);
        artist.setText(song.getArtistName());

        /* Ajout de l'italic pour supposer le clic */
        artist.setPaintFlags(artist.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        /* Le listener au clic sur l'artist */
        artist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onArtistClicked(mSong);
            }
        });


        /* La durée */
        try
        {
            /* Formatage de la durée reçue en secondes vers mm:ss */
            Date date = new Date((long) (song.getDuration() * 1000));
            String formattedDate = new SimpleDateFormat("mm:ss").format(date);

            /* Mise à jour du champ texte */
            ((TextView) view.findViewById(R.id.songDetailDuration)).setText("Durée : " + formattedDate);
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Danceability */
        try
        {
            ((TextView) view.findViewById(R.id.songDetailDanceability)).setText("Danceabilité : " + (int) (song.getDanceability() * 10) + "/10");
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Tempo */
        try
        {
            ((TextView) view.findViewById(R.id.songDetailTempo)).setText("Tempo : " + (int) song.getTempo());
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Hotttnesss */
        try
        {
            ((TextView) view.findViewById(R.id.songDetailHotttnesss)).setText("Hotttnesss : " + (int) (song.getSongHotttnesss() * 100) + "%");
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Loudness */
        try
        {
            ((TextView) view.findViewById(R.id.songDetailLoudness)).setText("Loudness : " + song.getLoudness() + "dB");
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }

        /* Retour de la vue */
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnArtistClicked) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArtistClicked");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
}
