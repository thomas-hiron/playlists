package com.thomas.playlists.viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Class héritant de ViewPager pour empêcher le scroll dans les vues
 *
 * Created by ThomasHiron on 16/10/2014.
 */
public class MyViewPager extends ViewPager
{
    public MyViewPager(Context context)
    {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;
    }
}
