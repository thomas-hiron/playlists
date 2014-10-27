package com.thomas.playlists.listeners;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.thomas.playlists.PlaylistSong;
import com.thomas.playlists.adapters.PlaylistAdapter;
import com.thomas.playlists.sqlite.MyContentProvider;

/**
 * Created by ThomasHiron on 27/10/2014.
 */
public class RemoveSongListener implements View.OnClickListener
{
    private PlaylistAdapter mPlaylistAdaper;
    private PlaylistSong mPlaylistSong;
    private Context mContext;

    public RemoveSongListener(View view, PlaylistSong playlistSong, PlaylistAdapter playlistAdapter)
    {
        mPlaylistAdaper = playlistAdapter;
        mContext = view.getContext();
        mPlaylistSong = playlistSong;
    }

    @Override
    public void onClick(View view)
    {
        /* L'id du son */
        int songId = mPlaylistSong.getId();

        /* Si l'id existe, on supprime dans la BDD */
        if(songId != 0)
        {
            /* La condition */
            String condition = MyContentProvider.SONGS_ID + " = " + songId;

            /* Suppression du son */
            mContext.getContentResolver().delete(MyContentProvider.CONTENT_URI_SONGS, condition, null);
        }

        /* Suppression de la vue */
        mPlaylistAdaper.remove((PlaylistSong) view.getTag());
        mPlaylistAdaper.notifyDataSetChanged();

        /* Toast */
        Toast.makeText(mContext, "Le morceau a bien été supprimé", Toast.LENGTH_LONG).show();
    }
}
