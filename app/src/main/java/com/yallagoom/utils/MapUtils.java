package com.yallagoom.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/*
import com.squareup.picasso.Picasso;
*/
import com.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/20/2018.
 */

public class MapUtils {
    /*    private Bitmap getMarkerBitmapFromView(View view, @DrawableRes int resId) {

    //        mMarkerImageView.setImageResource(resId);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnedBitmap);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
            Drawable drawable = view.getBackground();
            if (drawable != null)
                drawable.draw(canvas);
            view.draw(canvas);
            return returnedBitmap;
        }*/
    public static MarkerOptions createMarker(Context context, LatLng point, String bedroomCount, String url, ImageLoader imageLoader) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(point);
        int px = context.getResources().getDimensionPixelSize(R.dimen._60sdp);
        View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        markerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        markerView.layout(0, 0, px, px);
        markerView.buildDrawingCache();
        TextView bedNumberTextView = (TextView) markerView.findViewById(R.id.name);
        final ImageView image_event = (ImageView) markerView.findViewById(R.id.image_event);
        imageLoader.displayImage(url, image_event);
        Bitmap mDotMarkerBitmap = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDotMarkerBitmap);
        bedNumberTextView.setText(bedroomCount);
        markerView.draw(canvas);
        marker.icon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap));
        return marker;
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);

            return bitmap;
        } catch (OutOfMemoryError outOfMemoryError) {
            return null;
        }

    }
  /*  public static void PicassoImage(Context context, String url, ImageView image) {
        Log.e("urlurl",""+url);
        try {
            Picasso.with(context).load(url).error(R.drawable.default_image_small).placeholder(R.drawable.default_image_small)

                    .resize(80, 80).centerCrop()
                    .into(image);
        } catch (Exception e) {
            Log.e("Exception2", "" + e.getMessage());
         *//*   Picasso.with(context).load(R.drawable.default_image_small).placeholder(R.drawable.default_image_small).error(R.drawable.default_image_small)
                    .resize(80, 80).centerCrop()
                    .into(image);*//*
        }

    }*/
}
