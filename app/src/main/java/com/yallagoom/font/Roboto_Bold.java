package com.yallagoom.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;


public class Roboto_Bold extends android.support.v7.widget.AppCompatTextView {

	private final static String NAME = "Roboto_Bold";
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(12);

	public Roboto_Bold(Context context) {
		super(context);
		if (!isInEditMode()) {
			init();
		}
	}

	public Roboto_Bold(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			init();
	}

	public void init() {

		Typeface typeface = sTypefaceCache.get(NAME);

		if (typeface == null) {

			typeface = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Black.ttf");
			sTypefaceCache.put(NAME, typeface);

		}

		setTypeface(typeface);

	}


}