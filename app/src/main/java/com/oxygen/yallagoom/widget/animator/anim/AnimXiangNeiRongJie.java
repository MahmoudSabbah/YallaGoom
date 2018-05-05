package com.oxygen.yallagoom.widget.animator.anim;

import android.graphics.Canvas;
import android.graphics.Path;

import com.oxygen.yallagoom.widget.animator.EnterAnimLayout;


import java.util.Random;

/**
 * Created by pdx on 2017/8/26.
 */

public class AnimXiangNeiRongJie extends Anim {
    private final int[] ceils;
    int restNum;
    Random random = new Random();

    int total ;
    float rowNum = 20;
    float colNum = 30;
    float ceilWidth;
    float ceilHeight;

    public AnimXiangNeiRongJie(EnterAnimLayout view) {
        super(view);
        total = (int) (rowNum*colNum);
        ceils = new int[total];
        restNum =total;
        ceilHeight = (float) Math.ceil( h / rowNum);
        ceilWidth = (float) Math.ceil(w / colNum);
        for (int i = 0; i < total; i++)
        {
            ceils[i] = i;
        }
    }

    Path path = new Path();
    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        path.reset();
        int needNum = (int) (total * rate - (total - restNum));
        for (int i = 0; i < needNum; i++)
        {
            int r = random.nextInt(restNum - i);
            float left = getLeft(ceils[r]);
            float top = getTop(ceils[r]);
            path.addRect(left, top, left+ceilWidth, top+ceilHeight, Path.Direction.CW);

            int temp = ceils[r];
            ceils[r] = ceils[restNum - i - 1];
            ceils[restNum - 1 -i] = temp;
        }
        for(int i = 0;i<total -restNum;i++) {
            float left = getLeft(ceils[total-1-i]);
            float top = getTop(ceils[total-1-i]);
            path.addRect(left, top, left+ceilWidth, top+ceilHeight, Path.Direction.CW);
        }

        restNum = restNum - needNum;
        canvas.clipPath(path);
        canvas.save();
    }


    public float  getLeft(int index) {
        int leftIndex = index % (int) colNum;
        return leftIndex * ceilWidth;
    }

    public float  getTop(int index) {
        int topIndex = index / (int) colNum;
        return topIndex * ceilHeight-1;
    }


}
