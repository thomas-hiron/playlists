package com.thomas.playlists.exceptions;

/**
 * Created by ThomasHiron on 14/10/2014.
 *
 * Exception perso utilisée dans la méthode getInstance de EchoNestWrapper
 */
public class EchoNestWrapperException extends Exception
{

    /**
     * Constructeur
     *
     * @param s La chaîne à afficher
     */
    public EchoNestWrapperException(String s)
    {
        super(s);
    }
}
