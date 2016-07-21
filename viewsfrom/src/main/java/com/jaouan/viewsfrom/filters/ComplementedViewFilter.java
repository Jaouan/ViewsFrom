package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Complemented view filter.
 */
public class ComplementedViewFilter implements ViewFilter {

    /**
     * View filter to complement.
     */
    private final ViewFilter viewFilter;

    /**
     * Complemented view filter's constructor.
     * @param viewFilter View filter to complement.
     */
    public ComplementedViewFilter(@NonNull final ViewFilter viewFilter) {
        this.viewFilter = viewFilter;
    }

    @Override
    public boolean filter(final View view) {
        return !viewFilter.filter(view);
    }

}
