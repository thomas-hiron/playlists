package com.thomas.playlists;

import java.util.ArrayList;

/**
 * Classe gérant la recherche et la construction de la requête
 *
 * Created by ThomasHiron on 17/10/2014.
 */
public class PlaylistSearch
{
    /* Les données de la requête */
    private boolean isArtist; /* Rechercher dans les artistes ou les genres */
    private String artistGenre; /* La requête tapée (obligatoire) */
    private int nbResults;
    private float danceability;
    private int dateCreation;
    private int tempo;

    /* Les checkboxs */
    private ArrayList<String> musicTypes;

    /**
     * Constructeur
     */
    public PlaylistSearch()
    {
        /* Initialisation des variables */
        isArtist = true;
        artistGenre = "";
        nbResults = 0;
        danceability = 0;
        dateCreation = 0;
        tempo = 0;
        musicTypes = new ArrayList<String>();
    }

    public void isArtist(boolean isArtist)
    {
        this.isArtist = isArtist;
    }

    public void setArtistGenre(String artistGenre)
    {
        this.artistGenre = artistGenre;
    }

    public void setNbResults(int nbResults)
    {
        this.nbResults = nbResults;
    }

    public void setDanceability(float danceability)
    {
        this.danceability = danceability;
    }

    public void setDateCreation(int dateCreation)
    {
        this.dateCreation = dateCreation;
    }

    public void setTempo(int tempo)
    {
        this.tempo = tempo;
    }

    @Override
    public String toString()
    {
        return "isArtist : " + isArtist;
    }

    public void setMusicTypes(ArrayList<String> musicTypes)
    {
        this.musicTypes = musicTypes;
    }
}
