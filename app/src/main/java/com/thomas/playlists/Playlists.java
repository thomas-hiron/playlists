package com.thomas.playlists;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.thomas.playlists.fragments.ViewPagerAdapter;
import com.thomas.playlists.interfaces.OnHomeButtonClicked;
import com.thomas.playlists.listeners.HomeButtonsListener;

public class Playlists extends FragmentActivity implements OnHomeButtonClicked
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Création du viewPager */
        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.appContainer);

        /* Ajout de l'adapter au viewPager */
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        setContentView(viewPager);
    }

    @Override
    public void onHomeButtonClicked(String action)
    {
        /* Ajout d'une nouvelle playlist */
        if(action.equals(HomeButtonsListener.ACTION_ADD_PLAYLIST))
        {

        }
        /* Génération aléatoire d'une nouvelle playlist */
        else if(action.equals(HomeButtonsListener.ACTION_SHUFFLE_PLAYLIST))
        {

        }
    }
}
