package com.thomas.playlists.viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Class héritant de ViewPager pour empêcher le scroll dans les vues
 * <p/>
 * Created by ThomasHiron on 16/10/2014.
 */
public class MyViewPager extends ViewPager
{
    private boolean removeLast = false;

    public MyViewPager(Context context)
    {
        super(context);

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
                if(i == SCROLL_STATE_IDLE && removeLast)
                {
                    /* Adapter */
                    ViewPagerAdapter viewPagerAdapter = (ViewPagerAdapter) getAdapter();

                    /* On supprime le dernier élément */
                    viewPagerAdapter.removeLast().notifyDataSetChanged();

                    /* Pour ne pas repasser dans la boucle */
                    removeLast = false;
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;
    }

    public void setRemoveLast(boolean removeLast)
    {
        this.removeLast = removeLast;
    }
}
