package com.thomas.playlists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thomas.playlists.R;

/**
 * Created by ThomasHiron on 21/10/2014.
 *
 * Adapter pour la liste d'albums d'un artiste
 */
public class AlbumsAdapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    public AlbumsAdapter(Context context)
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
            view = mInflater.inflate(R.layout.album_item, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Ajout du nom de l'album */
        ((TextView) view.findViewById(R.id.albumTitle)).setText(getItem(position));

        /* Retour de la vue */
        return view;
    }
}
