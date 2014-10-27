package com.thomas.playlists.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.thomas.playlists.PlaylistSearch;
import com.thomas.playlists.api.EchoNestWrapper;
import com.thomas.playlists.exceptions.EchoNestWrapperException;

/**
 * Created by ThomasHiron on 14/10/2014.
 *
 * Chargement d'une playlist
 */
public class PlayListLoader extends AsyncTaskLoader<Playlist>
{
    private Playlist mPlaylist = null;
    private PlaylistSearch mPlaylistSearch = null;

    public PlayListLoader(Context context, PlaylistSearch playlistSearch)
    {
        super(context);
        mPlaylistSearch = playlistSearch;
    }

    @Override
    public Playlist loadInBackground()
    {
        try
        {
            /* Récupération des params */
            PlaylistParams params = mPlaylistSearch.getParams();

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
        mPlaylistSearch = pPlaylistSearch;
    }
}
