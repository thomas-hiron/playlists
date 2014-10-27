package com.thomas.playlists.interfaces;

import com.thomas.playlists.sqlite.PlaylistItem;

/**
 * Created by ThomasHiron on 16/10/2014.
 *
 * Les clics sur la home
 */
public interface OnHomeButtonClicked
{
    /**
     * Au clic sur les boutons d'ajout de playlist ou de playlist aléatoire
     *
     * @param s L'action effectuée
     */
    public void onHomeButtonClicked(String s);

    /**
     * Au clic sur une playlist enregistrée
     *
     * @param item La playlist cliquée
     */
    public void onPlaylistItemClicked(PlaylistItem item);
}
