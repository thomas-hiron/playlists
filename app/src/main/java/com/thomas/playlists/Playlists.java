package com.thomas.playlists;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.thomas.playlists.fragments.AddPlaylistFragment;
import com.thomas.playlists.fragments.HomeFragment;
import com.thomas.playlists.interfaces.OnHomeButtonClicked;
import com.thomas.playlists.interfaces.OnPlaylistAdded;
import com.thomas.playlists.listeners.HomeButtonsListener;
import com.thomas.playlists.viewPager.MyViewPager;
import com.thomas.playlists.viewPager.ViewPagerAdapter;

public class Playlists extends FragmentActivity implements OnHomeButtonClicked, OnPlaylistAdded
{
    private ViewPagerAdapter mViewPagerAdapter = null;
    private ViewPager mViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Création du viewPager */
        mViewPager = new MyViewPager(this);
        mViewPager.setId(R.id.appContainer);

        /* Ajout de l'adapter au viewPager */
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.add(HomeFragment.newInstance());
        mViewPager.setAdapter(mViewPagerAdapter);

        setContentView(mViewPager);
    }

    @Override
    /**
     * Clique sur les boutons d'accueil (ajout ou playlist aléatoire)
     */
    public void onHomeButtonClicked(String action)
    {
        /* Ajout d'une nouvelle playlist */
        if(action.equals(HomeButtonsListener.ACTION_ADD_PLAYLIST))
        {
            /* Ajout du nouveau fragment */
            mViewPagerAdapter.add(new AddPlaylistFragment());

            /* Mise à jour */
            updateViewPager();
        }
        /* Génération aléatoire d'une nouvelle playlist */
        else if(action.equals(HomeButtonsListener.ACTION_SHUFFLE_PLAYLIST))
        {

        }
    }

    @Override
    /* Formulaire rempli */
    public void onPlaylistAdded(String s)
    {

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

        /* On supprime le dernier élément */
        mViewPagerAdapter.removeLast();

        /* Mise à jour */
        updateViewPager();
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
