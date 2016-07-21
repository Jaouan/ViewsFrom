package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Abstract equals filter.
 */
public abstract class AbstractEqualsViewFilter<TypeOfObject> implements ViewFilter {

    /**
     * Texts.
     */
    private final TypeOfObject[] objects;

    /**
     * Text filter's contructor.
     *
     * @param objects Objects.
     */
    public AbstractEqualsViewFilter(@NonNull final TypeOfObject[] objects) {
        this.objects = objects;
    }

    @Override
    public boolean filter(final View view) {
        return FilterHelper.arrayContains(objects, getObjectToMatch(view));
    }

    /**
     * Get object to match.
     *
     * @param view View.
     * @return Object to match.
     */
    protected abstract Object getObjectToMatch(View view);

}
