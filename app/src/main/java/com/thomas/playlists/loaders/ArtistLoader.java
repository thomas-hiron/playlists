package com.thomas.playlists.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestException;
import com.thomas.playlists.api.EchoNestWrapper;
import com.thomas.playlists.exceptions.EchoNestWrapperException;

import java.util.List;

/**
 * Created by ThomasHiron on 22/10/2014.
 */
public class ArtistLoader extends AsyncTaskLoader<List<Artist>>
{
    private String mArtistName;
    private List<Artist> mArtist;

    public ArtistLoader(Context context, String artistName)
    {
        super(context);

        mArtistName = artistName;
    }

    @Override
    public List<Artist> loadInBackground()
    {
        try
        {
            /* Initialisation des paramètres */
            ArtistParams params = new ArtistParams();
            params.includeBiographies();
            params.includeImages();
            params.includeYearsActive();

            /* Le nom de l'artiste */
            params.setName(mArtistName);

            /* Requête à l'API */
            mArtist = EchoNestWrapper.getInstance().searchArtists(params);
        }
        catch(EchoNestWrapperException e)
        {
            mArtist = null;
            e.printStackTrace();
        }
        catch(EchoNestException e)
        {
            mArtist = null;
            e.printStackTrace();
        }

        return mArtist;
    }

    @Override
    protected void onStartLoading()
    {
        if(mArtist != null)
            deliverResult(mArtist);
        else
            forceLoad();
    }
}
