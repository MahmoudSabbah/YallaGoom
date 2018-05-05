package com.oxygen.yallagoom.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;


public class Gilroy_Black extends android.support.v7.widget.AppCompatTextView {

	private final static String NAME = "Gilroy_Black";
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(12);

	public Gilroy_Black(Context context) {
		super(context);
		if (!isInEditMode()) {
			init();
		}
	}

	public Gilroy_Black(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			init();
	}

	public void init() {

		Typeface typeface = sTypefaceCache.get(NAME);

		if (typeface == null) {

			typeface = Typeface.createFromAsset(getContext().getAssets(), "font/Gilroy-Black.ttf");
			sTypefaceCache.put(NAME, typeface);

		}

		setTypeface(typeface);

	}


}