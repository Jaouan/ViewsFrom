package com.jaouan.viewsfrom;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.jaouan.viewsfrom.filters.AggregatedViewFilters;
import com.jaouan.viewsfrom.filters.ComplementedViewFilter;
import com.jaouan.viewsfrom.filters.ExcluderViewFilter;
import com.jaouan.viewsfrom.filters.IdViewFilter;
import com.jaouan.viewsfrom.filters.TagRegexViewFilter;
import com.jaouan.viewsfrom.filters.TagViewFilter;
import com.jaouan.viewsfrom.filters.TypeViewFilter;
import com.jaouan.viewsfrom.filters.ViewFilter;
import com.jaouan.viewsfrom.filters.VisibilityViewFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Views utils that helps to find views by id, tags, type, ...
 */
public final class ViewsFinder {

    /**
     * Root view.
     */
    private ViewGroup[] rootViews;

    /**
     * Nested views finder when {@see andFrom} is used.
     */
    private ViewsFinder nestedViewsFinder;

    /**
     * Including root view.
     */

    /**
     * View filters.
     */
    private List<ViewFilter> viewFilters = new ArrayList<>();

    /**
     * Include root view to views find.
     */
    private boolean includeRootView;

    /**
     * Complement next "with" filter.
     */
    private boolean complementNextWithFilter;

    /**
     * View comparator.
     */
    private Comparator<View> viewComparator;

    /**
     * Add childs from filtered group views.
     */
    private boolean addChildsFromFilteredGroupViews = true;

    /**
     * ViewsFinder's constructor.
     *
     * @param rootViews Root view.
     */
    ViewsFinder(@NonNull final ViewGroup... rootViews) {
        FunctionUtils.checkParameterArrayIsNotNull("rootViews", rootViews);
        this.rootViews = rootViews;
    }


    /**
     * ViewsFinder's constructor with nested views finder.
     *
     * @param nestedViewsFinder Nested views finder.
     * @param rootViews         Root view.
     */
    private ViewsFinder(@NonNull ViewsFinder nestedViewsFinder, @NonNull final ViewGroup... rootViews) {
        this(rootViews);
        FunctionUtils.checkParameterIsNotNull("nestedViewsFinder", nestedViewsFinder);
        this.nestedViewsFinder = nestedViewsFinder;
    }

    /**
     * Specifies to include from views.
     *
     * @return View finder.
     */
    public ViewsFinder includingFromViews() {
        this.includeRootView = true;
        return this;
    }

    /**
     * Specifies to exclude childs from filtered group views.
     *
     * @return View finder.
     */
    public ViewsFinder excludingChildsFromFilteredGroupViews() {
        this.addChildsFromFilteredGroupViews = false;
        return this;
    }

    /**
     * Concates current views finder with a new one.
     *
     * @param rootViews Root views.
     * @return View finder.
     */
    public ViewsFinder andFrom(final ViewGroup... rootViews) {
        FunctionUtils.checkParameterIsNotNull("rootViews", rootViews);
        return new ViewsFinder(this, rootViews);
    }

    /**
     * Finds and lists all found views.
     */
    public List<View> find() {
        final List<View> views = new ArrayList<>();

        // - Add all views from nested views finder.
        if (nestedViewsFinder != null) {
            views.addAll(nestedViewsFinder.find());
        }

        // - For each root view.
        for (final ViewGroup rootView : rootViews) {
            // - Include root view to views find if necessary.
            if (includeRootView) {
                views.add(rootView);
            }
            // - Find all childs.
            ViewsHelper.findChilds(rootView, views, new AggregatedViewFilters(viewFilters), addChildsFromFilteredGroupViews);
        }

        // - Sort views if necessary.
        if (viewComparator != null) {
            Collections.sort(views, viewComparator);
        }

        return views;
    }

    /**
     * Iterates all found views.
     *
     * @param viewIteration View iteration.
     */
    public void forEach(final ViewIteration viewIteration) {
        FunctionUtils.checkParameterIsNotNull("viewIteration", viewIteration);
        final List<View> views = find();
        final int viewCount = views.size();
        for (int viewIndex = 0; viewIndex < viewCount; viewIndex++) {
            viewIteration.onView(views.get(viewIndex), viewIndex, viewCount);
        }
    }

    /**
     * Animates all found views.
     *
     * @param context   Context.
     * @param animResId Animation ressource id.
     * @return Views animator.
     */
    public ViewsAnimator animateWith(@NonNull final Context context, @AnimRes final int animResId) {
        FunctionUtils.checkParameterIsNotNull("context", context);
        return new ViewsAnimator(context, this, animResId);
    }

    /**
     * Animates all found views.
     *
     * @param animationProvider Animation provider.
     * @return Views animator.
     */
    public ViewsAnimator animateWith(final AnimationProvider animationProvider) {
        FunctionUtils.checkParameterIsNotNull("animationProvider", animationProvider);
        return new ViewsAnimator(this, animationProvider);
    }

    /**
     * Orders views with specific comparator.
     *
     * @param viewComparator View comparator.
     * @return View finder.
     */
    public ViewsFinder orderedBy(@NonNull final Comparator<View> viewComparator) {
        FunctionUtils.checkParameterIsNotNull("viewComparator", viewComparator);
        this.viewComparator = viewComparator;
        return this;
    }

    /**
     * Filters views.
     *
     * @param viewFilter View filter.
     * @return View finder.
     */
    public ViewsFinder filteredWith(@NonNull final ViewFilter viewFilter) {
        FunctionUtils.checkParameterIsNotNull("viewFilter", viewFilter);
        viewFilters.add(viewFilter);
        return this;
    }

    /**
     * Complements next "with" filter. Only works with "with*" method.
     *
     * @return View finder.
     */
    public ViewsFinder not() {
        this.complementNextWithFilter = true;
        return this;
    }

    /**
     * Filters view's visibility. Can be used with {@see not}.
     *
     * @param viewVisibilities View's visibilites to allow.
     * @return View finder.
     */
    public ViewsFinder withVisibility(final int... viewVisibilities) {
        FunctionUtils.checkParameterArrayIsNotNull("viewVisibilities", viewVisibilities);
        viewFilters.add(complementFilterIfNecessary(new VisibilityViewFilter(viewVisibilities)));
        return this;
    }

    /**
     * Filters view's tag. Can be used with {@see not}.
     *
     * @param tags View's tags to allow.
     * @return View finder.
     */
    public ViewsFinder withTag(final String... tags) {
        FunctionUtils.checkParameterIsNotNull("tags", tags);
        viewFilters.add(complementFilterIfNecessary(new TagViewFilter(tags)));
        return this;
    }

    /**
     * Filters view's tag using regex. Can be used with {@see not}.
     *
     * @param tagRegexes View's tag regexes to allow.
     * @return View finder.
     */
    public ViewsFinder withTagRegex(final String... tagRegexes) {
        FunctionUtils.checkParameterArrayIsNotNull("tagRegexes", tagRegexes);
        viewFilters.add(complementFilterIfNecessary(new TagRegexViewFilter(tagRegexes)));
        return this;
    }

    /**
     * Filters view's tag. Can be used with {@see not}.
     *
     * @param identifiers View's identifiers to allow.
     * @return View finder.
     */
    public ViewsFinder withId(@IdRes final int... identifiers) {
        FunctionUtils.checkParameterArrayIsNotNull("identifiers", identifiers);
        viewFilters.add(complementFilterIfNecessary(new IdViewFilter(identifiers)));
        return this;
    }

    /**
     * Filters view's type. Can be used with {@see not}.
     *
     * @param types View's types to allow.
     * @return View finder.
     */
    @SafeVarargs
    public final ViewsFinder withType(final Class<? extends View>... types) {
        FunctionUtils.checkParameterArrayIsNotNull("types", types);
        viewFilters.add(complementFilterIfNecessary(new TypeViewFilter(types)));
        return this;
    }

    /**
     * Complements filter if necessary.
     *
     * @param viewFilter View filter.
     * @return View filter.
     */
    private ViewFilter complementFilterIfNecessary(final ViewFilter viewFilter) {
        FunctionUtils.checkParameterIsNotNull("viewFilter", viewFilter);
        if (complementNextWithFilter) {
            complementNextWithFilter = false;
            return new ComplementedViewFilter(viewFilter);
        }
        return viewFilter;
    }

    /**
     * Excludes views.
     *
     * @param views Views to exclude.
     * @return View finder.
     */
    public ViewsFinder excludeView(final View... views) {
        FunctionUtils.checkParameterArrayIsNotNull("views", views);
        viewFilters.add(new ExcluderViewFilter(views));
        return this;
    }

}
