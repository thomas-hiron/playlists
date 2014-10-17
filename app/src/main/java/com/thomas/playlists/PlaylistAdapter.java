//package com.thomas.playlists;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.echonest.api.v4.Song;
//import com.squareup.picasso.Picasso;
//
///**
// * Created by ThomasHiron on 14/10/2014.
// *
// * @author ThomasHiron
// */
//public class PlaylistAdapter extends ArrayAdapter<Song>
//{
//    /* L'inflater */
//    private LayoutInflater mInflater = null;
//
//    public PlaylistAdapter(Context context)
//    {
//        super(context, android.R.layout.simple_list_item_2);
//
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View view = null;
//
//        /* Création de la vue */
//        if(convertView == null)
//            view = mInflater.inflate(R.layout.fragment_song, parent, false);
//        /* On garde la vue transmise */
//        else
//            view = convertView;
//
//        /* Récupération du son */
//        Song song = getItem(position);
//
//        /* L'image de l'artiste */
//        String cover = "";
//
//        try
//        {
//            cover = song.getString("tracks[0].release_image");
//        }
//        catch(IndexOutOfBoundsException e)
//        {
//            e.printStackTrace();
//        }
//
//        ((TextView) view.findViewById(R.id.song_artist_name)).setText(song.getArtistName());
//        ((TextView) view.findViewById(R.id.song_title)).setText(song.getTitle());
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.cover);
//
//        if(!cover.equals(""))
//            Picasso.with(getContext()).load(cover).into(imageView);
//
//        return view;
//    }
//}
