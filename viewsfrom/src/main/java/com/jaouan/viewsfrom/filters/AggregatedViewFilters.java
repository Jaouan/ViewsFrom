package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

/**
 * Global filter that aggregate multiples filters.
 */
public class AggregatedViewFilters implements ViewFilter {

    /**
     * View filters.
     */
    private final List<ViewFilter> viewFilters;

    /**
     * AggregatedViewFilters's constructor.
     *
     * @param viewFilters View filter to onView.
     */
    public AggregatedViewFilters(@NonNull final List<ViewFilter> viewFilters) {
        this.viewFilters = viewFilters;
    }

    @Override
    public boolean filter(final View view) {
        // - Apply all filters.
        for (final ViewFilter viewFilter : viewFilters) {
            if (!viewFilter.filter(view)) {
                return false;
            }
        }
        return true;
    }

}
