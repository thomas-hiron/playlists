package com.thomas.playlists;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.thomas.playlists.api.EchoNestWrapper;
import com.thomas.playlists.exceptions.EchoNestWrapperException;

/**
 * Created by ThomasHiron on 14/10/2014.
 */
public class PlayListLoader extends AsyncTaskLoader<Playlist>
{
    private Playlist mPlaylist = null;
    private PlaylistSearch playlistSearch = null;

    public PlayListLoader(Context context)
    {
        super(context);
    }

    @Override
    public Playlist loadInBackground()
    {
        try
        {
            /* Récupération des params */
            PlaylistParams params = playlistSearch.getParams();

            /* Requête à l'API */
            mPlaylist = EchoNestWrapper.getInstance().createStaticPlaylist(params);
        }
        catch(EchoNestWrapperException e)
        {
            mPlaylist = null;
            e.printStackTrace();
        }
        catch(EchoNestException e)
        {
            mPlaylist = null;
            e.printStackTrace();
        }

        return mPlaylist;
    }

    @Override
    protected void onStartLoading()
    {
        if(mPlaylist != null)
            deliverResult(mPlaylist);
        else
            forceLoad();
    }

    public void setPlaylistSearch(PlaylistSearch pPlaylistSearch)
    {
        playlistSearch = pPlaylistSearch;
    }
}
