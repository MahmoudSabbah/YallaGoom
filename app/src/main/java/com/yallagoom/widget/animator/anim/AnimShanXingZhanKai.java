package com.yallagoom.widget.animator.anim;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

import com.yallagoom.widget.animator.EnterAnimLayout;


/**
 * Created by pdx on 2017/8/26.
 */

public class AnimShanXingZhanKai extends Anim {
    // load from bottom
    public AnimShanXingZhanKai(EnterAnimLayout view) {
        super(view);
        float r = (float) Math.sqrt(Math.pow(w, 2)+ Math.pow(h,2));
        oval = new RectF(w/2 -r , h/2-r, w+r-w/2, h+r-h/2);
    }
    Path path1=new Path();
    RectF oval ;
    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        path1.reset();
        path1.addArc(oval,270-180*rate,360*rate);
        path1.lineTo(w/2,h/2);
        path1.close();
        canvas.clipPath(path1);

        canvas.save();
    }
}
