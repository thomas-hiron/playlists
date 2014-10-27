package com.thomas.playlists.viewsExtended;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by ThomasHiron on 27/10/2014.
 *
 * GridView pour les albums qui affiche tout
 */
public class AlbumsListView extends ListView
{
    boolean expanded = false;

    public AlbumsListView(Context context)
    {
        super(context);
    }

    public AlbumsListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AlbumsListView(Context context, AttributeSet attrs,int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (isExpanded())
        {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
}
