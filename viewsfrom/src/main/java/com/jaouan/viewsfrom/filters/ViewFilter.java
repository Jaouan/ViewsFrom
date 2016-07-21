package com.jaouan.viewsfrom.filters;

import android.view.View;


/**
 * View filter.
 */
public interface ViewFilter {

    /**
     * Check if view satisfies the filter.
     *
     * @param view View.
     * @return TRUE if the view satisfies the filter and must be kept in find.
     */
    boolean filter(View view);

}
