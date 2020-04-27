package com.koolearn.android.kooreader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainMenuWindow extends RelativeLayout {
    private Animator.AnimatorListener myEndHideListener;
    private Animator.AnimatorListener myEndShowListener;
    private Animator myShowHideAnimator;

    public MainMenuWindow(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            initAnimator();
    }

    public MainMenuWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            initAnimator();
    }

    public MainMenuWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            initAnimator();
    }

    public MainMenuWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            initAnimator();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initAnimator()
    {
        this.myEndShowListener = new AnimatorListenerAdapter()
        {
            public void onAnimationEnd(Animator paramAnonymousAnimator)
            {
                myShowHideAnimator = null;
                MainMenuWindow.this.requestLayout();
            }
        };
        this.myEndHideListener = new AnimatorListenerAdapter()
        {
            public void onAnimationEnd(Animator paramAnonymousAnimator)
            {
                myShowHideAnimator = null;
                MainMenuWindow.this.setVisibility(GONE);
            }
        };
    }
    @TargetApi(11)
    private void hideAnimatedInternal()
    {
        if (this.myShowHideAnimator != null)
            this.myShowHideAnimator.end();
        if (getVisibility() ==View.GONE)
            return;
        setAlpha(1.0F);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.setDuration(420L);
        localAnimatorSet.play(ObjectAnimator.ofFloat(this, "alpha", new float[] { 0.0F }));
        localAnimatorSet.addListener(this.myEndHideListener);
        this.myShowHideAnimator = localAnimatorSet;
        localAnimatorSet.start();
    }
    @TargetApi(11)
    private void showAnimatedInternal()
    {
        if (this.myShowHideAnimator != null)
            this.myShowHideAnimator.end();
        if (getVisibility() ==View.VISIBLE)
            return;
        setVisibility(View.VISIBLE);
        setAlpha(0.0F);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.setDuration(420L);
        localAnimatorSet.play(ObjectAnimator.ofFloat(this, "alpha", new float[] { 1.0F }));
        localAnimatorSet.addListener(this.myEndShowListener);
        this.myShowHideAnimator = localAnimatorSet;
        localAnimatorSet.start();
    }
    public void hide()
    {
        post(new Runnable()
        {
            public void run()
            {
                if (Build.VERSION.SDK_INT >= 11)
                {
                    MainMenuWindow.this.hideAnimatedInternal();
                    return;
                }
                MainMenuWindow.this.setVisibility(View.GONE);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        return true;
    }
    public void show()
    {
        post(new Runnable()
        {
            public void run()
            {
                if (Build.VERSION.SDK_INT >= 11)
                {
                    MainMenuWindow.this.showAnimatedInternal();
                    return;
                }
                MainMenuWindow.this.setVisibility(View.VISIBLE);
            }
        });
    }
}
