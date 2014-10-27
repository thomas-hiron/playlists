package com.thomas.playlists.interfaces;

import com.thomas.playlists.PlaylistSong;

/**
 * Created by ThomasHiron on 21/10/2014.
 *
 * Au clic sur un morceau d'une playlist
 */
public interface OnPlaylistItemClicked
{
    public void onPlaylistItemClicked(PlaylistSong song);
}
