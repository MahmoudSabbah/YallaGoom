package com.oxygen.yallagoom.widget.animator.anim;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;

import com.oxygen.yallagoom.widget.animator.EnterAnimLayout;


/**
 * Created by pdx on 2017/8/26.
 */

public class AnimLingXing extends Anim {
    public AnimLingXing(EnterAnimLayout view) {
        super(view);
    }
    Path path1=new Path();

    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        //Cut out the diamond-shaped area
        path1.reset();
        path1.moveTo(w/2, -h/2+ h*rate);
        path1.lineTo(-w/2+w*rate, h/2);
        path1.lineTo(w/2, h+h/2 - h*rate);
        path1.lineTo(w+w/2 -w*rate, h/2);
        path1.close();//封闭
        canvas.clipPath(path1, Region.Op.XOR);

        canvas.save();
    }
}
