package com.oxygen.yallagoom.widget.animator.anim;

import android.graphics.Canvas;

import com.oxygen.yallagoom.widget.animator.EnterAnimLayout;


/**
 * Created by pdx on 2017/8/26.
 */
public abstract class Anim {
    protected EnterAnimLayout view;
    protected float w;
    protected float h;
    public float totalPaintTime;

    public Anim(EnterAnimLayout view) {
        this(view,2000);
    }

    public Anim(EnterAnimLayout view, float totalPaintTime) {
        this.totalPaintTime = totalPaintTime;
        this.view = view;
        this.view.setAnim(this);
        w = view.getWidth();
        h = view.getHeight();
    }

    public void startAnimation() {
        view.setmIsAnimaionRun(true);
        view.setStartTime(System.currentTimeMillis());

        view.invalidate();
    }
    public void startAnimation(long animTime) {
        totalPaintTime = animTime;
        startAnimation();
    }

    public abstract void handleCanvas(Canvas canvas, float rate);
}
