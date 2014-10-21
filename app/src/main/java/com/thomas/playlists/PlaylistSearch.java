package com.thomas.playlists;

import com.echonest.api.v4.PlaylistParams;

import java.util.ArrayList;

/**
 * Classe gérant la recherche et la construction de la requête
 * <p/>
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
        artistGenre = "Jimi Hendrix";
        nbResults = 0;
        danceability = 0;
        dateCreation = 0;
        tempo = 0;
        musicTypes = new ArrayList<String>();
    }

    /**
     * Retourne un objet de type PlaylistParams pour la requête
     */
    public PlaylistParams getParams()
    {
        PlaylistParams params = new PlaylistParams();

        params.includeArtistFamiliarity();
        params.includeArtistHotttnesss();
        params.includeSongHotttnesss();

        /* La danceabilité et le tempo min */
        params.setMinDanceability(danceability);
        params.setMinTempo(tempo);

        /* Le nombre de résultats si != 0 */
        if(nbResults != 0)
            params.setResults(nbResults);

        /* La date de création min (année) si != 0 */
        if(dateCreation != 0)
            params.setArtistStartYearAfter(dateCreation);

        /* Les types de musique (live, studio,...) */
        for(String s : musicTypes)
            params.addStyle(s);

        params.includeArtistFamiliarity();
        params.includeAudioSummary();

        /* Les API externes */
        params.addIDSpace("7digital-US");
        params.addIDSpace("spotify-WW");
        params.includeTracks();

        /* On renseigne si artiste ou genre et le type */
        if(isArtist)
        {
            params.addArtist(artistGenre);
            params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        } else
        {
            params.addGenre(artistGenre);
            params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        }

        return params;
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
