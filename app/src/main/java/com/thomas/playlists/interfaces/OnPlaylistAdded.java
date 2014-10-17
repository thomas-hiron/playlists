package com.thomas.playlists.interfaces;

import com.thomas.playlists.PlaylistSearch;

/**
 * Created by ThomasHiron on 16/10/2014.
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
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
