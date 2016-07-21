package com.jaouan.viewsfrom;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/**
 * Views animator.
 */
public final class ViewsAnimator {

    /**
     * Context.
     */
    private Context context;

    /**
     * Animation provider.
     * It provides by default the animation ressource id.
     */
    private AnimationProvider animationProvider = new AnimationProvider() {
        @Override
        public Animation provide() {
            return AnimationUtils.loadAnimation(context, animResId);
        }
    };

    /**
     * Views finder. (ignored if views list is defined)
     */
    private ViewsFinder viewsFinder;

    /**
     * Views list.
     */
    private List<View> views;

    /**
     * Animation ressource id.
     */
    private int animResId;


    /**
     * Delay between each child animation.
     */
    private int delayBetweenEachChild;

    /**
     * End action.
     */
    private Runnable endAction;


    /**
     * Views visibility before animation. NULL if nothing to change.
     */
    private Integer viewsVisibilityBeforeAnimation;

    /**
     * ViewsAnimator's constructor.
     *
     * @param context     Context.
     * @param viewsFinder Views finder.
     * @param animResId   Animation ressource id.
     */
    public ViewsAnimator(@NonNull final Context context, @NonNull final ViewsFinder viewsFinder, @AnimRes final int animResId) {
        FunctionUtils.checkParameterIsNotNull("context", context);
        FunctionUtils.checkParameterIsNotNull("viewsFinder", viewsFinder);
        this.context = context;
        this.viewsFinder = viewsFinder;
        this.animResId = animResId;
    }

    /**
     * ViewsAnimator's constructor.
     *
     * @param context   Context.
     * @param views     Views.
     * @param animResId Animation ressource id.
     */
    public ViewsAnimator(@NonNull final Context context, @NonNull final List<View> views, @AnimRes final int animResId) {
        FunctionUtils.checkParameterIsNotNull("context", context);
        FunctionUtils.checkParameterIsNotNull("views", views);
        this.context = context;
        this.views = views;
        this.animResId = animResId;
    }

    /**
     * ViewAnimator's constructor.
     *
     * @param viewsFinder       Views finder.
     * @param animationProvider Animation provider.
     */
    public ViewsAnimator(@NonNull final ViewsFinder viewsFinder, @NonNull final AnimationProvider animationProvider) {
        FunctionUtils.checkParameterIsNotNull("viewsFinder", viewsFinder);
        FunctionUtils.checkParameterIsNotNull("animationProvider", animationProvider);
        this.viewsFinder = viewsFinder;
        this.animationProvider = animationProvider;
    }

    /**
     * ViewAnimator's constructor.
     *
     * @param views             Views list.
     * @param animationProvider Animation provider.
     */
    public ViewsAnimator(@NonNull final List<View> views, @NonNull final AnimationProvider animationProvider) {
        FunctionUtils.checkParameterIsNotNull("views", views);
        FunctionUtils.checkParameterIsNotNull("animationProvider", animationProvider);
        this.views = views;
        this.animationProvider = animationProvider;
    }


    /**
     * Defines delay between each child animation.
     *
     * @param delayBetweenEachChild Delay between each child animation.
     * @return Views animator.
     */
    public ViewsAnimator withDelayBetweenEachChild(final int delayBetweenEachChild) {
        this.delayBetweenEachChild = delayBetweenEachChild;
        return this;
    }

    /**
     * Defines end action callback.
     *
     * @param endAction End action callback.
     * @return Views animator.
     */
    public ViewsAnimator withEndAction(@NonNull final Runnable endAction) {
        FunctionUtils.checkParameterIsNotNull("endAction", endAction);
        this.endAction = endAction;
        return this;
    }

    /**
     * Defines if views visibility before animation.
     *
     * @param viewsVisibilityBeforeAnimation Views visibility before animation.
     * @return Views animator.
     */
    public ViewsAnimator withViewsVisibilityBeforeAnimation(final int viewsVisibilityBeforeAnimation) {
        this.viewsVisibilityBeforeAnimation = viewsVisibilityBeforeAnimation;
        return this;
    }

    /**
     * Let's animate !
     * @return Views animator for future animation.
     */
    public ViewsAnimator start() {
        // - Find viewsToAnimate from viewsFinder if necessary.
        List<View> viewsToAnimate = this.views;
        if (viewsToAnimate == null) {
            viewsToAnimate = viewsFinder.find();
        }

        // - Update viewsToAnimate visibility before animation if necessary.
        if (viewsVisibilityBeforeAnimation != null) {
            for (final View view : viewsToAnimate) {
                view.setVisibility(viewsVisibilityBeforeAnimation);
            }
        }

        // - Animate all viewsToAnimate.
        Animation animation = null;
        for (int viewIndex = 0; viewIndex < viewsToAnimate.size(); viewIndex++) {
            animation = animationProvider.provide();
            animation.setStartOffset(animation.getStartOffset() + delayBetweenEachChild * viewIndex);
            viewsToAnimate.get(viewIndex).startAnimation(animation);
        }

        // - Call end action on last animation end.
        if (animation != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    if (endAction != null) {
                        endAction.run();
                    }
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        return this;
    }

}
