package com.thomas.playlists.sqlite;

/**
 * Created by ThomasHiron on 25/10/2014.
 *
 * Une playlist de la home
 */
public class PlaylistItem
{
    private int id;
    private String title;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
