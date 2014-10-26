package com.thomas.playlists;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.thomas.playlists.api.EchoNestWrapper;
import com.thomas.playlists.fragments.AddPlaylistFragment;
import com.thomas.playlists.fragments.ArtistDetailFragment;
import com.thomas.playlists.fragments.ExistingPlaylistFragment;
import com.thomas.playlists.fragments.HomeFragment;
import com.thomas.playlists.fragments.PlaylistFragment;
import com.thomas.playlists.fragments.ShuffleFragment;
import com.thomas.playlists.fragments.SongDetailFragment;
import com.thomas.playlists.interfaces.OnArtistClicked;
import com.thomas.playlists.interfaces.OnHomeButtonClicked;
import com.thomas.playlists.interfaces.OnPlaylistAdded;
import com.thomas.playlists.interfaces.OnPlaylistItemClicked;
import com.thomas.playlists.listeners.HomeButtonsListener;
import com.thomas.playlists.sqlite.PlaylistItem;
import com.thomas.playlists.viewPager.MyViewPager;
import com.thomas.playlists.viewPager.ViewPagerAdapter;

public class Playlists extends FragmentActivity implements OnHomeButtonClicked, OnPlaylistAdded, OnPlaylistItemClicked, OnArtistClicked
{
    private ViewPagerAdapter mViewPagerAdapter = null;
    private MyViewPager mViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Création du viewPager */
        mViewPager = new MyViewPager(this);
        mViewPager.setId(R.id.appContainer);

        /* Ajout de l'adapter au viewPager */
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.add(new HomeFragment());
//        mViewPagerAdapter.add(new AddPlaylistFragment());
        mViewPager.setAdapter(mViewPagerAdapter);

        /* Ajout de la clé à l'API */
        EchoNestWrapper.setApiKey(Playlists.this);

        setContentView(mViewPager);
    }

    /**
     * Clique sur les boutons d'accueil (ajout ou playlist aléatoire)
     */
    @Override
    public void onHomeButtonClicked(String action)
    {
        /* Ajout d'une nouvelle playlist */
        if(action.equals(HomeButtonsListener.ACTION_ADD_PLAYLIST))
            mViewPagerAdapter.add(new AddPlaylistFragment());

        /* Génération aléatoire d'une nouvelle playlist */
        else if(action.equals(HomeButtonsListener.ACTION_SHUFFLE_PLAYLIST))
            mViewPagerAdapter.add(new ShuffleFragment());

        /* Mise à jour */
        updateViewPager();
    }

    /**
     * Au clic sur une playlist de la home
     *
     * @param item
     */
    @Override
    public void onPlaylistItemClicked(PlaylistItem playlistItem)
    {
        /* Création du fragment de playlist */
        ExistingPlaylistFragment existingPlaylistFragment = new ExistingPlaylistFragment(playlistItem);

        /* Ajout du nouveau fragment */
        mViewPagerAdapter.add(existingPlaylistFragment);

        /* Mise à jour */
        updateViewPager();
    }

    /**
     * Formulaire rempli
     *
     * @see com.thomas.playlists.interfaces.OnPlaylistAdded
     */
    @Override
    public void onPlaylistAdded(PlaylistSearch playlistSearch)
    {
        /* Le fragment */
        PlaylistFragment playlistFragment = new PlaylistFragment();

        /* Ajout du nouveau fragment */
        mViewPagerAdapter.add(playlistFragment);

        /* Mise à jour */
        updateViewPager();

        /* Chargement via le loader */
        playlistFragment.search(playlistSearch);
    }

    /**
     * Un item d'une playlist cliqué
     *
     * @param song
     */
    @Override
    public void onPlaylistItemClicked(PlaylistSong song)
    {
        /* Le fragment */
        SongDetailFragment songDetailFragment = new SongDetailFragment(song);

        /* Ajout du nouveau fragment */
        mViewPagerAdapter.add(songDetailFragment);

        /* Mise à jour */
        updateViewPager();
    }

    /**
     * Au clic sur un artiste de la vue détaillée
     *
     * @param song
     */
    @Override
    public void onArtistClicked(PlaylistSong song)
    {
        /* Le fragment */
        ArtistDetailFragment artistDetailFragment = new ArtistDetailFragment(song);

        /* Ajout du nouveau fragment */
        mViewPagerAdapter.add(artistDetailFragment);

        /* Mise à jour */
        updateViewPager();
    }

    @Override
    public void onBackPressed()
    {
        /* Page d'accueil, on ferme l'appli (comportement par défaut) */
        if(mViewPagerAdapter.getCount() == 1)
        {
            super.onBackPressed();
            return;
        }

        /* Pour supprimer le dernier élement lors du scroll terminé */
        mViewPager.setRemoveLast(true);

        /* Scroll */
        mViewPager.setCurrentItem(mViewPagerAdapter.getCount() - 2);
    }

    /**
     * Met à jour l'adapter et scroll dans la vue
     */
    public void updateViewPager()
    {
        /* Mise à jour de l'adapter */
        mViewPagerAdapter.notifyDataSetChanged();

        /* Scroll dans la vue */
        mViewPager.setCurrentItem(mViewPagerAdapter.getCount() - 1, true);
    }
}
