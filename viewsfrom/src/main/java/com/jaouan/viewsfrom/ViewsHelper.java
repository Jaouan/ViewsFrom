package com.jaouan.viewsfrom;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jaouan.viewsfrom.filters.ViewFilter;

import java.util.List;

/**
 * Views helper.
 */
final class ViewsHelper {

    /**
     * Private constructor to disallow instantiation.
     */
    private ViewsHelper() {
    }

    /**
     * Helps to find recursivly all childs in a view group.
     *
     * @param viewGroup                       View group.
     * @param ordoredChilds                   Childs find where visible childs will be added.
     * @param viewFilter                      View filter.
     * @param addChildsFromFilteredGroupViews Add childs from filtered group views.
     * @return Childs find.
     */
    static List<View> findChilds(@NonNull final ViewGroup viewGroup, @NonNull final List<View> ordoredChilds, @NonNull final ViewFilter viewFilter, final boolean addChildsFromFilteredGroupViews) {
        for (int childViewIndex = 0; childViewIndex < viewGroup.getChildCount(); childViewIndex++) {
            final View childView = viewGroup.getChildAt(childViewIndex);
            final boolean isNotFiltered = viewFilter.filter(childView);
            if (isNotFiltered) {
                ordoredChilds.add(childView);
            }
            if (childView instanceof ViewGroup && (isNotFiltered || addChildsFromFilteredGroupViews)) {
                findChilds((ViewGroup) childView, ordoredChilds, viewFilter, addChildsFromFilteredGroupViews);
            }
        }
        return ordoredChilds;
    }

}
