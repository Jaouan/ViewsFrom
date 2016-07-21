package com.jaouan.viewsfrom.filters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Abstract regex filter.
 */
public abstract class AbstractRegexViewFilter implements ViewFilter {

    /**
     * Regex patterns.
     */
    private final List<Pattern> patterns;

    /**
     * Visibility filter's contructor.
     *
     * @param regexes View's regexes.
     */
    public AbstractRegexViewFilter(@NonNull final String[] regexes) {
        patterns = new ArrayList<>(regexes.length);
        for (final String regex : regexes) {
            patterns.add(Pattern.compile(regex));
        }
    }

    @Override
    public boolean filter(final View view) {
        final String textToMatch = getTextToMatch(view);
        if (textToMatch != null) {
            for (final Pattern pattern : patterns) {
                if (pattern.matcher(textToMatch).find()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get text to match.
     *
     * @param view View.
     * @return Text to match.
     */
    protected abstract String getTextToMatch(View view);

}
