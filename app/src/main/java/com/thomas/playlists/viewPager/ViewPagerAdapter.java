package com.thomas.playlists.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ThomasHiron on 16/10/2014.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    ArrayList<Fragment> mList = null;

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
        mList = new ArrayList<Fragment>();
    }

    /**
     * Ajoute une vue au tableau
     *
     * @param fragment
     */
    public void add(Fragment fragment)
    {
        mList.add(fragment);
    }

    /**
     * Supprime le dernier élément de la liste
     */
    public void removeLast()
    {
        /* On supprime le dernier s'il y a plus d'un élément */
        if(mList.size() > 1)
            mList.remove(mList.size() - 1);
    }

    @Override
    public Fragment getItem(int i)
    {
        return mList.get(i);
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }
}
