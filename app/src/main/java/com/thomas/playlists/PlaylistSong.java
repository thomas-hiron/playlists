package com.thomas.playlists;

import com.echonest.api.v4.Song;

/**
 * Created by ThomasHiron on 21/10/2014.
 *
 * Classe perso de son, car impossible de créer une classe qui étend Song (aucun setters) :/
 */
public class PlaylistSong
{
    private int mId = 0;
    private Song mSong = null;
    private String mCover = null;
    private String mArtistName = null;
    private String mTitle = null;

    private double mLoudness = 0;
    private double mDuration = 0;
    private double mDanceability = 0;
    private double mTempo = 0;
    private double mHotttnesss = 0;

    private String artistLocation;
    private double artistHotttnesss;
    private String[] artistAlbums = null;

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

    public String getArtistName()
    {
        return mArtistName;
    }

    public void setArtistName(String artistName)
    {
        this.mArtistName = artistName;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        this.mTitle = title;
    }

    public double getHotttnesss()
    {
        return mHotttnesss;
    }

    public void setHotttnesss(double hotttnesss)
    {
        this.mHotttnesss = hotttnesss;
    }

    public double getLoudness()
    {
        return mLoudness;
    }

    public void setLoudness(double loudness)
    {
        this.mLoudness = loudness;
    }

    public double getDuration()
    {
        return mDuration;
    }

    public void setDuration(double duration)
    {
        this.mDuration = duration;
    }

    public double getDanceability()
    {
        return mDanceability;
    }

    public void setDanceability(double danceability)
    {
        this.mDanceability = danceability;
    }

    public double getTempo()
    {
        return mTempo;
    }

    public void setTempo(double tempo)
    {
        this.mTempo = tempo;
    }

    public String getArtistLocation()
    {
        return artistLocation;
    }

    public void setArtistLocation(String artistLocation)
    {
        this.artistLocation = artistLocation;
    }

    public double getArtistHotttnesss()
    {
        return artistHotttnesss;
    }

    public void setArtistHotttnesss(double artistHotttnesss)
    {
        this.artistHotttnesss = artistHotttnesss;
    }

    public String[] getArtistAlbums()
    {
        return artistAlbums;
    }

    public void setArtistAlbums(String[] artistAlbums)
    {
        this.artistAlbums = artistAlbums;
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        this.mId = id;
    }
}
