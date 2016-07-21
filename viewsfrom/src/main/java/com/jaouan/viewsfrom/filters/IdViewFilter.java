package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Identifier filter.
 */
public class IdViewFilter implements ViewFilter {
    /**
     * View's identifiers.
     */
    private final int[] viewIdentifiers;

    /**
     * Identifier filter's contructor.
     *
     * @param viewIdentifiers View's visibilities.
     */
    public IdViewFilter(@NonNull final int[] viewIdentifiers) {
        this.viewIdentifiers = viewIdentifiers;
    }

    @Override
    public boolean filter(final View view) {
        return FilterHelper.arrayContains(viewIdentifiers, view.getId());
    }

}
