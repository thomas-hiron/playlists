package com.thomas.playlists.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.thomas.playlists.Playlists;
import com.thomas.playlists.fragments.SongDetailFragment;

/**
 * Class héritant de ViewPager pour empêcher le scroll dans les vues
 * <p/>
 * Created by ThomasHiron on 16/10/2014.
 */
public class MyViewPager extends ViewPager
{
    private boolean mRemoveLast = false;

    public MyViewPager(final Playlists playlists)
    {
        super(playlists);

        /* Listener de scroll */
        setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int i, float v, int i2)
            {

            }

            @Override
            public void onPageSelected(int i)
            {

            }

            @Override
            public void onPageScrollStateChanged(int i)
            {
                /* Si page à l'arrêt et on supprime le dernier */
                if(i == SCROLL_STATE_IDLE && mRemoveLast)
                {
                    /* Adapter */
                    ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) getAdapter();

                    /* On supprime le dernier élément */
                    viewPagerAdapter.removeLast().notifyDataSetChanged();

                    /* Pour ne pas repasser dans la boucle */
                    mRemoveLast = false;

                    /* L'adapter du viewPager */
                    ViewPagerAdapter adapter = getAdapter();
                    Fragment item = adapter.getItem(adapter.getCount() - 1);

                    /* Si on revient du détail de l'artist, on met à jour la playlist */
                    if(String.valueOf(SongDetailFragment.class).matches(String.valueOf(item.getClass())))
                        playlists.refreshPlaylist();
                }
            }
        });
    }

    @Override
    public ViewPagerAdapter getAdapter()
    {
        return (ViewPagerAdapter) super.getAdapter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;
    }

    public void setRemoveLast(boolean removeLast)
    {
        this.mRemoveLast = removeLast;
    }
}
