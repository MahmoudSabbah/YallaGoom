package com.oxygen.yallagoom.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;


public class Roboto_RegularRadio extends android.support.v7.widget.AppCompatRadioButton {

	private final static String NAME = "Roboto_RegularRadio";
	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(12);

	public Roboto_RegularRadio(Context context) {
		super(context);
		if (!isInEditMode()) {
			init();
		}
	}

	public Roboto_RegularRadio(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			init();
	}

	public void init() {

		Typeface typeface = sTypefaceCache.get(NAME);

		if (typeface == null) {

			typeface = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");
			sTypefaceCache.put(NAME, typeface);

		}

		setTypeface(typeface);

	}


}