package com.yallagoom.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yallagoom.interfaces.OnScrollCallBack;

/**
 * Created by Mahmoud Sabbah on 3/13/2018.
 */

public class CustomScrollListener extends RecyclerView.OnScrollListener {
    private final OnScrollCallBack onScrollCallBack;

    public CustomScrollListener(OnScrollCallBack onScrollCallBack) {
        this.onScrollCallBack=onScrollCallBack;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
             //   Log.e("RecyclerView","The RecyclerView is not scrolling");
                onScrollCallBack.scrollState(RecyclerView.SCROLL_STATE_IDLE);

                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
               // Log.e("RecyclerView","Scrolling now");
                onScrollCallBack.scrollState(RecyclerView.SCROLL_STATE_DRAGGING);
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
             //   Log.e("RecyclerView","Scroll Settling");
                onScrollCallBack.scrollState(RecyclerView.SCROLL_STATE_SETTLING);
                break;

        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dx > 0) {
            System.out.println("Scrolled Right");
        } else if (dx < 0) {
            System.out.println("Scrolled Left");
        } else {
            System.out.println("No Horizontal Scrolled");
        }

        if (dy > 0) {
            System.out.println("Scrolled Downwards");
        } else if (dy < 0) {
            System.out.println("Scrolled Upwards");
        } else {
            System.out.println("No Vertical Scrolled");
        }
    }
}
