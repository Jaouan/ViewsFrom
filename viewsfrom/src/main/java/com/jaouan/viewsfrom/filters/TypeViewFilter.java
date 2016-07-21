package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Type filter.
 */
public class TypeViewFilter extends AbstractEqualsViewFilter<Class<? extends View>> {

    /**
     * Type filter's contructor.
     *
     * @param viewTypes View's types.
     */
    public TypeViewFilter(@NonNull Class<? extends View>[] viewTypes) {
        super(viewTypes);
    }

    @Override
    protected Object getObjectToMatch(final View view) {
        return view.getClass();
    }

}