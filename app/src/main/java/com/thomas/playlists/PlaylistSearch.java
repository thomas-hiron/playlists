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
    private boolean mIsArtist; /* Rechercher dans les artistes ou les genres */
    private String mArtistGenre; /* La requête tapée (obligatoire) */
    private int mNbResults;
    private float mDanceability;
    private int mDateCreation;
    private int mTempo;

    /* Les checkboxs */
    private ArrayList<String> mMusicTypes;

    /**
     * Constructeur
     */
    public PlaylistSearch()
    {
        /* Initialisation des variables */
        mIsArtist = true;
        mArtistGenre = "Jimi Hendrix";
        mNbResults = 0;
        mDanceability = 0;
        mDateCreation = 0;
        mTempo = 0;
        mMusicTypes = new ArrayList<String>();
    }

    /**
     * Retourne un objet de type PlaylistParams pour la requête
     */
    public PlaylistParams getParams()
    {
        PlaylistParams params = new PlaylistParams();

        /* La danceabilité et le tempo min */
        params.setMinDanceability(mDanceability);
        params.setMinTempo(mTempo);

        /* Le nombre de résultats si != 0 */
        if(mNbResults != 0)
            params.setResults(mNbResults);

        /* La date de création min (année) si != 0 */
        if(mDateCreation != 0)
            params.setArtistStartYearAfter(mDateCreation);

        /* Les types de musique (live, studio,...) */
        for(String s : mMusicTypes)
            params.addStyle(s);

        params.includeArtistHotttnesss();
        params.includeSongHotttnesss();
        params.includeAudioSummary();
        params.includeArtistLocation();

        /* Les API externes */
        params.addIDSpace("7digital-US");
        params.addIDSpace("spotify-WW");
        params.includeTracks();

        /* On renseigne si artiste ou genre et le type */
        if(mIsArtist)
        {
            params.addArtist(mArtistGenre);
            params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        }
        else
        {
            params.addGenre(mArtistGenre);
            params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        }

        return params;
    }


    public void setArtistGenre(String artistGenre)
    {
        this.mArtistGenre = artistGenre;
    }

    public void setNbResults(int nbResults)
    {
        this.mNbResults = nbResults;
    }

    public void setDanceability(float danceability)
    {
        this.mDanceability = danceability;
    }

    public void setDateCreation(int dateCreation)
    {
        this.mDateCreation = dateCreation;
    }

    public void setTempo(int tempo)
    {
        this.mTempo = tempo;
    }

    @Override
    public String toString()
    {
        return "isArtist : " + mIsArtist;
    }

    public void setMusicTypes(ArrayList<String> musicTypes)
    {
        this.mMusicTypes = musicTypes;
    }

    public void isArtist(boolean b)
    {
        mIsArtist = b;
    }
}
