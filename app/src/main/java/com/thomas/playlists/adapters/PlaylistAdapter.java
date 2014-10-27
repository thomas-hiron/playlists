package com.thomas.playlists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.Song;
import com.squareup.picasso.Picasso;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;

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
        ViewHolder viewHolder;

        /* Création de la vue */
        if(convertView == null)
        {
            view = mInflater.inflate(R.layout.fragment_item_song, parent, false);

            /* Création d'un viewHolder pour garder les vues */
            viewHolder = new ViewHolder();
            viewHolder.artist = ((TextView) view.findViewById(R.id.songArtistName));
            viewHolder.title = ((TextView) view.findViewById(R.id.songTitle));
            viewHolder.cover = (ImageView) view.findViewById(R.id.cover);

            /* Ajout de l'objet à la vue */
            view.setTag(viewHolder);
        }
        /* On garde la vue transmise */
        else
        {
            view = convertView;

            /* Récupération du holder */
            viewHolder = (ViewHolder) view.getTag();
        }

        /* Récupération du son */
        PlaylistSong playlistSong = getItem(position);
        Song song = playlistSong.getSong();

        /* Initialisation des variables */
        String artistName = song == null ? playlistSong.getArtistName() : song.getArtistName();
        String title = song == null ? playlistSong.getTitle() : song.getTitle();

        /* L'image de l'artiste */
        String cover = playlistSong.getCover();

        /*
         * Si résultat venant de l'api, on n'a pas le cover
         * (la première fois, ensuite il est présent lors du rafraichissement de la liste)
         */
        if(song != null && cover == null)
        {
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
        }

        /* Changement des textes */
        viewHolder.artist.setText(artistName);
        viewHolder.title.setText(title);

        /* Ajout de l'image */
        if(cover != null && !cover.equals(""))
            Picasso.with(getContext()).load(cover).into(viewHolder.cover);
        /* Sinon suppression de l'image */
        else
            viewHolder.cover.setImageBitmap(null);

        /* Ajout de la cover */
        playlistSong.setCover(cover);

        return view;
    }

    static class ViewHolder
    {
        TextView artist;
        TextView title;
        ImageView cover;
    }
}
