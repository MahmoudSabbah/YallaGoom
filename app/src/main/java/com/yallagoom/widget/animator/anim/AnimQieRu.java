package com.yallagoom.widget.animator.anim;

import android.graphics.Canvas;

import com.yallagoom.widget.animator.EnterAnimLayout;


/**
 * Created by pdx on 2017/8/26.
 */

public class AnimQieRu extends Anim {
    public AnimQieRu(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {

        canvas.translate(0,h-h*rate);

        canvas.save();
    }
}
