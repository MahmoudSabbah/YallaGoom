package com.yallagoom.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yallagoom.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class SplashPagerAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int mSize;
    private List<Integer> data=new ArrayList<>();

    public SplashPagerAdapter() {
        mSize = 4;
        data.add(R.drawable.sp1);
        data.add(R.drawable.sp2);
        data.add(R.drawable.sp3);
        data.add(R.drawable.sp4);
    }

    public SplashPagerAdapter(int count) {
        mSize = count;
    }

    @Override public int getCount() {
        return mSize;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override public Object instantiateItem(ViewGroup view, int position) {
        ImageView imageView =new ImageView(view.getContext());
        imageView.setImageResource(data.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      /*  TextView textView = new TextView(view.getContext());
        textView.setText(String.valueOf(position + 1));
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(48);*/
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }
}