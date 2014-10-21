package com.thomas.playlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.squareup.picasso.Picasso;

/**
 * Created by ThomasHiron on 14/10/2014.
 *
 * @author ThomasHiron
 */
public class PlaylistAdapter extends ArrayAdapter<PlaylistSong>
{
    /* L'inflater */
    private LayoutInflater mInflater = null;

    public PlaylistAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_2);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;

        /* Création de la vue */
        if(convertView == null)
            view = mInflater.inflate(R.layout.fragment_item_song, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Récupération du son */
        PlaylistSong playlistSong = getItem(position);
        Song song = playlistSong.getSong();

        /* L'image de l'artiste */
        String cover = "";

        try
        {
            /* Compteur */
            int cpt = 0;

            /*
             * Tant qu'on n'a pas trouvé d'image
             * Lorsque le compteur aura dépassé la taille du tableau, une exception sera levée
             */
            while((cover = song.getString("tracks[" + cpt + "].release_image")) == null)
                ++cpt;
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        /* Modification des textes des textView */
        ((TextView) view.findViewById(R.id.song_artist_name)).setText(song.getArtistName());
        ((TextView) view.findViewById(R.id.song_title)).setText(song.getTitle());

        /* Suppression du chargement */
        ((View) parent.getParent()).findViewById(R.id.loading_results).setVisibility(View.GONE);

        /* L'image */
        ImageView imageView = (ImageView) view.findViewById(R.id.cover);

        /* Ajout de l'image */
        if(cover != null && !cover.equals(""))
            Picasso.with(getContext()).load(cover).into(imageView);

        /* Ajout de la cover */
        playlistSong.setCover(cover);

        return view;
    }
}
