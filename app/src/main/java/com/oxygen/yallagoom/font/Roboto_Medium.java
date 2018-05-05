package com.oxygen.yallagoom.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;


public class Roboto_Medium extends android.support.v7.widget.AppCompatTextView {

	private final static String NAME = "Roboto_Medium";
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(12);

	public Roboto_Medium(Context context) {
		super(context);
		if (!isInEditMode()) {
			init();
		}
	}

	public Roboto_Medium(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			init();
	}

	public void init() {

		Typeface typeface = sTypefaceCache.get(NAME);

		if (typeface == null) {

			typeface = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Medium.ttf");
			sTypefaceCache.put(NAME, typeface);

		}

		setTypeface(typeface);

	}


}