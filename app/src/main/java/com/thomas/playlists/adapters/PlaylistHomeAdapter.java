package com.thomas.playlists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thomas.playlists.R;
import com.thomas.playlists.sqlite.PlaylistItem;

/**
 * Created by ThomasHiron on 25/10/2014.
 *
 * Adapter pour la liste des playlists de la home
 */
public class PlaylistHomeAdapter extends ArrayAdapter<PlaylistItem>
{
    private LayoutInflater mInflater;

    public PlaylistHomeAdapter(Context context)
    {
        super(context, android.R.layout.simple_list_item_1);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;

        /* Création de la vue */
        if(convertView == null)
            view = mInflater.inflate(R.layout.playlist_item, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Ajout du titre de la playlist */
        ((TextView) view.findViewById(R.id.playlistTitle)).setText(getItem(position).getTitle());

        /* Retour de la vue */
        return view;
    }
}
