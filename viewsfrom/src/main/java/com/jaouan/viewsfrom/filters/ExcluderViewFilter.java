package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Excluder view filter.
 */
public class ExcluderViewFilter implements ViewFilter {

    /**
     * Views to exclude.
     */
    private final View[] views;

    /**
     * Excluder view filter's constructor.
     *
     * @param views Views to exclude.
     */
    public ExcluderViewFilter(@NonNull final View[] views) {
        this.views = views;
    }

    @Override
    public boolean filter(final View view) {
        return !FilterHelper.arrayContains(views, view);
    }

}
