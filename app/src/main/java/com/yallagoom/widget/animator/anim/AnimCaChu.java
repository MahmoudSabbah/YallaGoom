package com.yallagoom.widget.animator.anim;

import android.graphics.Canvas;

import com.yallagoom.widget.animator.EnterAnimLayout;


/**
 * Created by pdx on 2017/8/26.
 */

public class AnimCaChu extends Anim {
    public AnimCaChu(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        float rectTop =  (h - h * rate);
        canvas.clipRect(0, rectTop, w, h);

        canvas.save();
    }
}
