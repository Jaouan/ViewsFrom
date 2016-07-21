package com.jaouan.viewsfrom;

import android.view.View;

/**
 * View iteration.
 */
public interface ViewIteration {

    /**
     * View iteration.
     * @param view Found view.
     * @param viewIndex View index in views find.
     * @param viewsCount Views find count.
     */
    void onView(View view, int viewIndex, int viewsCount);

}
