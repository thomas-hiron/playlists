package com.thomas.playlists.interfaces;

import com.thomas.playlists.PlaylistSearch;

/**
 * Created by ThomasHiron on 16/10/2014.
 *
 * Validation du formulaire
 */
public interface OnPlaylistAdded
{
    /**
     * Formulaire validé
     *
     * @param playlistSearch L'objet contenant les infos pour la recherche
     */
    public void onPlaylistAdded(PlaylistSearch playlistSearch);
}
