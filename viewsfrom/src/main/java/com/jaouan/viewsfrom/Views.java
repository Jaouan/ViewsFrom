package com.jaouan.viewsfrom;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * Views librarie entry point.
 */
public final class Views {

    /**
     * Helps to find child views.
     * @param rootViews Root views.
     * @return Views finder.
     */
    public static ViewsFinder from(@NonNull final ViewGroup... rootViews){
        return new ViewsFinder(rootViews);
    }

}
