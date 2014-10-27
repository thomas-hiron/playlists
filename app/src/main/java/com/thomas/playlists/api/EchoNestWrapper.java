package com.thomas.playlists.api;

import android.content.Context;

import com.echonest.api.v4.EchoNestAPI;
import com.thomas.playlists.R;
import com.thomas.playlists.exceptions.EchoNestWrapperException;

/**
 * Created by ThomasHiron on 14/10/2014.
 *
 * Classe perso qui renvoie une instance d'EchoNest
 */
public class EchoNestWrapper
{
    /* L'instance d'EchoNest */
    private static EchoNestAPI mInstance = null;

    /* La clé de l'api */
    private static String ECHO_NEST_API_KEY = "";

    public static void setApiKey(Context context)
    {
        ECHO_NEST_API_KEY = context.getString(R.string.echo_nest_api);
    }

    public static EchoNestAPI getInstance() throws EchoNestWrapperException
    {
        /* Si clé vide, on lance une exception */
        if(ECHO_NEST_API_KEY.equals(""))
            throw new EchoNestWrapperException("Must be called before setting API_KEY");

        /* Instance nulle, on la créer */
        if(mInstance == null)
            mInstance = new EchoNestAPI(ECHO_NEST_API_KEY);

        /* Retour de l'instance */
        return mInstance;
    }
}
