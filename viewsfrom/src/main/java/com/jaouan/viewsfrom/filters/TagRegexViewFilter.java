package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Tag regex filter.
 */
public class TagRegexViewFilter extends AbstractRegexViewFilter {

    /**
     * Tag regex filter's contructor.
     *
     * @param viewTagRegexes View's tags regexes.
     */
    public TagRegexViewFilter(@NonNull final String[] viewTagRegexes) {
        super(viewTagRegexes);
    }

    @Override
    protected String getTextToMatch(final View view) {
        if (view.getTag() == null || !(view.getTag() instanceof String)) {
            return null;
        }
        return (String) view.getTag();
    }

}
