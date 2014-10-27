package com.thomas.playlists.viewsExtended;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by ThomasHiron on 27/10/2014.
 */
public class ImagesGridView extends GridView
{
    private boolean expanded = false;

    public ImagesGridView(Context context)
    {
        super(context);
    }

    public ImagesGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ImagesGridView(Context context, AttributeSet attrs,
                          int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    /**
     * Affiche tous les élements de la grille sans scroll
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (isExpanded())
        {
            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
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
