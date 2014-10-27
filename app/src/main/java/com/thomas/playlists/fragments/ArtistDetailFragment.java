package com.thomas.playlists.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.Biography;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Image;
import com.echonest.api.v4.Song;
import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.R;
import com.thomas.playlists.adapters.AlbumsAdapter;
import com.thomas.playlists.adapters.ImagesAdapter;
import com.thomas.playlists.loaders.ArtistLoader;
import com.thomas.playlists.viewsExtended.AlbumsListView;
import com.thomas.playlists.viewsExtended.ImagesGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Artist>>
{
    private PlaylistSong mSong;
    private View mView;
    private static int ARTIST_LOADER_ID = 2;
    private static int NB_PICTURES = 9;

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

        mView = view;

        getLoaderManager().initLoader(ARTIST_LOADER_ID, null, this);

        /* Le son de l'API */
        Song song = mSong.getSong();

        /* Les variables */
        String artistName = song == null ? mSong.getArtistName() : song.getArtistName();
        double artistHotttnesss = 0;
        String artistLocation = "";
        String[] artistAlbums = mSong.getArtistAlbums();

        try
        {
            artistHotttnesss = song == null ? mSong.getArtistHotttnesss() : song.getArtistHotttnesss();
            artistLocation = song == null ? mSong.getArtistLocation() : song.getArtistLocation().getPlaceName();
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }

        /* Changement du titre */
        ((TextView) mView.findViewById(R.id.artistDetailTitle)).setText(artistName);

        /* Location */
        ((TextView) mView.findViewById(R.id.artistDetailLocation)).setText("Location : " + artistLocation);

        /* Hotttnesss */
        ((TextView) mView.findViewById(R.id.artistDetailHotttnesss)).setText("Hotttnesss : " + (int) (artistHotttnesss * 100) + "%");


        /* Les albums */
        ArrayList<String> albums = new ArrayList<String>();

        /* Si playlist enregistrée */
        if(artistAlbums != null)
        {
            for(String album : artistAlbums)
                albums.add(album);
        }
        else
        {
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
        }

        /* La listView */
        AlbumsListView listAlbums = (AlbumsListView) mView.findViewById(R.id.artistDetailAlbums);
        listAlbums.setExpanded(true);

        /* On prévient le scroll car déjà dans scrollView */
        listAlbums.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                /* Pour ne pas scroller dans la vue */
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

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
            mView.findViewById(R.id.albumsLabel).setVisibility(View.GONE);
            listAlbums.setVisibility(View.GONE);
        }

        return mView;
    }


    @Override
    public Loader<List<Artist>> onCreateLoader(int i, Bundle bundle)
    {
        /* Le son */
        Song song = mSong.getSong();

        /* Déclaration et initialisation du loader */
        ArtistLoader loader = new ArtistLoader(getActivity(), song == null ? mSong.getArtistName() : song.getArtistName());

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> artistLoader, List<Artist> artists)
    {
        /* Le context */
        Context context = mView.getContext();

        /* On cache le loading */
        mView.findViewById(R.id.loadingArtistDetails).setVisibility(View.GONE);

        /* On récupère le premier artiste */
        Artist artist = artists.get(0);


        /* La biographie (wikipedia) */
        try
        {
            List<Biography> biographies = artist.getBiographies();

            String site = "", bio = "";
            for(Biography biography : biographies)
            {
                site = biography.getSite();
                if(site.equals("wikipedia"))
                {
                    bio = biography.getText();
                    break;
                }
            }

            /* Le TV de la bio */
            TextView tvBio = (TextView) mView.findViewById(R.id.artistDetailBiography);

            /* Affichage de la biographie */
            if(!bio.equals(""))
            {
                int max = 1000;

                /* On coupe la chaine */
                if(bio.length() > max)
                    bio = bio.substring(0, max) + "...";

                /* Changement du texte */
                tvBio.setText(bio);

                /* Affichage du titre */
                mView.findViewById(R.id.biographyLabel).setVisibility(View.VISIBLE);
            }
            /* Sinon on cache la vue */
            else
                tvBio.setVisibility(View.GONE);
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Les images */
        try
        {
            /* Les images */
            List<Image> images = artist.getImages();

            /* La vue conteneur et l'adapter */
            ImagesGridView imagesContainer = (ImagesGridView) mView.findViewById(R.id.artistDetailImages);
            imagesContainer.setExpanded(true);
            ImagesAdapter imagesAdapter = new ImagesAdapter(context);

            /* Ajout des urls à la liste */
            for(int i = 0, l = Math.min(NB_PICTURES, images.size()); i < l; ++i)
                imagesAdapter.add(images.get(i).getURL());

            imagesContainer.setAdapter(imagesAdapter);

            /* Si aucune image, on cache les vues */
            if(images.size() == 0)
                imagesContainer.setVisibility(View.GONE);
            /* Sinon on affiche le titre */
            else
                mView.findViewById(R.id.imagesLabel).setVisibility(View.VISIBLE);
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }


        /* Les genres */
        try
        {
            /* Le textView */
            TextView tvYearsActive = (TextView) mView.findViewById(R.id.artistDetailActivity);

            /* Les années d'activité */
            Long[] yearsActive = artist.getYearsActive().getRange();

            /* La chaine */
            String yearsString = "Activité : " + yearsActive[0];

            /* Si l'activité est terminée :( */
            if(yearsActive.length > 1)
                yearsString += " - " + yearsActive[1];

            /* Ajout du texte */
            tvYearsActive.setText(yearsString);
        }
        catch(EchoNestException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> artistCatalogLoader)
    {

    }
}
