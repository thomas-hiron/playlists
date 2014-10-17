package com.thomas.playlists.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ThomasHiron on 16/10/2014.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter
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
    public ViewPagerAdapter removeLast()
    {
        /* On supprime le dernier s'il y a plus d'un élément */
        if(mList.size() > 1)
            mList.remove(getCount() - 1);

        Log.v("test", "removed");

        /* Retour de l'instance */
        return this;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }
}
