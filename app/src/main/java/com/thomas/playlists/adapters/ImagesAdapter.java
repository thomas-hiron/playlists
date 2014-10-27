package com.thomas.playlists.adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thomas.playlists.R;

/**
 * Created by ThomasHiron on 27/10/2014.
 *
 * Adapter pour les photos d'un artiste
 */
public class ImagesAdapter extends ArrayAdapter<String>
{
    private LayoutInflater mInflater;

    public ImagesAdapter(Context context)
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
            view = mInflater.inflate(R.layout.artist_image_item, parent, false);
        /* On garde la vue transmise */
        else
            view = convertView;

        /* Récupération de l'image */
        ImageView image = (ImageView) view.findViewById(R.id.artistImage);

        /* Calcul de la taille de l'écran */
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        /* Calcul final, avec margin */
        int width = size.x / 3 - 30;

        /* Picasso */
        Picasso.with(view.getContext())
                .load(getItem(position))
                .resize(width, width)
                .centerCrop()
                .into(image);

        /* Retour de la vue */
        return view;
    }
}
