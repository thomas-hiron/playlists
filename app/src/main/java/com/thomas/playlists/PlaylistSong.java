package com.thomas.playlists;

import com.echonest.api.v4.Song;

/**
 * Created by ThomasHiron on 21/10/2014.
 * <p/>
 * Classe perso de son, car impossible de créer une classe qui étend Song :/
 */
public class PlaylistSong
{
    private Song mSong;
    private String mCover;

    public PlaylistSong(Song song)
    {
        mSong = song;
    }

    public Song getSong()
    {
        return mSong;
    }

    public void setCover(String cover)
    {
        this.mCover = cover;
    }

    public String getCover()
    {
        return mCover;
    }
}
