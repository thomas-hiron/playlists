package com.thomas.playlists.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ThomasHiron on 16/10/2014.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i)
    {
        return HomeFragment.newInstance();
    }

    @Override
    public int getCount()
    {
        return 1;
    }
}
