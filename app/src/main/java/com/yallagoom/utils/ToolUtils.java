package com.yallagoom.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.tapadoo.alerter.Alerter;
import com.yallagoom.R;
import com.yallagoom.interfaces.CheckGPSCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.Buffer;

import static android.content.Context.MODE_PRIVATE;
import static io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider.REQUEST_CHECK_SETTINGS;

/**
 * Created by Mahmoud Sabbah on 2/4/2018.
 */

public class ToolUtils {
    public static Typeface font(Context context, String font) {
        Typeface externalFont = Typeface.createFromAsset(context
                .getAssets(), "font/" + font);
        return externalFont;
    }

    public static void buildAlertMessageNoGps(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener
            , final CheckGPSCallback checkGPSCallback) {

        Log.e("googleApiClient", "googleApiClient");
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {

                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        checkGPSCallback.processFinish(true, status);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        checkGPSCallback.processFinish(false, status);
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        checkGPSCallback.processFinish(false, status);
                        break;
                }
            }
        });

    }
    public static void buildAlertMessageNoGps2(final Activity context,final CheckGPSCallback checkGPSCallback){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
//                    Log.e("qwe21","qwe2"+task.getResult().getLocationSettingsStates().isGpsUsable());
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    checkGPSCallback.processFinish(true,null);
                } catch (ApiException exception) {
                    Log.e("qwe1","qwe1");
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        context,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        context,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }
            }
        });
    }



    public static OkHttpClient getOkHttpClient() {

        OkHttpClient
                client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static void viewToast(Context mContext, String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
    }

    public static SharedPreferences getSharedPreferences(Context context, String title) {
        SharedPreferences prefs = context.getSharedPreferences(title, MODE_PRIVATE);
        return prefs;
    }

    public static SharedPreferences.Editor setSharedPrefernce(Context context, String title) {
        SharedPreferences.Editor editor = context.getSharedPreferences(title, MODE_PRIVATE).edit();
        return editor;

    }

    public static void showSnak(Activity context, String msg) {
        Alerter.create(context)
                .setTitle(msg)
                .setTextTypeface(font(context, "Roboto-Light.ttf"))
                .setTextAppearance(R.style.SnackText)
                .setBackgroundColorRes(R.color.color_1cacad) // or setBackgroundColorInt(Color.CYAN)
                .show();
      /*  TSnackbar snackbar = TSnackbar.make(context.findViewById(android.R.id.content), msg, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#bf000000"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        snackbar.show();*/
    }

    public static void hideStatus(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow(); // in Activity's onCreate() for instance
           w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
/*
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
*/
        }
    }

    public static void setLightStatusBar(View view, Activity activity) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public static String searchresult(String text, double Latitude, double XLongitude, Context context) {

        // String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;
        return getPlaceAutoCompleteUrl(text, Latitude, XLongitude, context);
    }

    public static String getPlaceAutoCompleteUrl(String input, double Latitude, double Longitude, Context context) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/autocomplete/json");
        urlString.append("?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(Latitude + "," + Longitude); // append lat long of current location to show nearby results.
        urlString.append("&radius=2000&language=en");
        urlString.append("&key=" + context.getResources().getString(R.string.google_maps_key));

        Log.d("FINAL URL:::   ", urlString.toString());
        // String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+Latitude+","+Longitude+"&radius=1000&keyword="+input+"&key=" +context.getResources().getString(R.string.API_KEY);
        return urlString.toString();
    }

    public static String getLanLong(String palce, Context context) {
        String dataresult = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + palce + "&key=" + context.getResources().getString(R.string.google_maps_key);

        return dataresult;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideKeyFromScreen(View view_lay, final Context context) {
        view_lay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view, context);
                return false;
            }
        });
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static Bitmap getbitmap(Context context, int id, float w, float h) {


        BitmapDrawable bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(context, id);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, (int) w, (int) h, false);

        return smallMarker;
    }

    public static Bitmap getbitmap(Context context, int id) {


        BitmapDrawable bitmapdraw = (BitmapDrawable) ContextCompat.getDrawable(context, id);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, (int) context.getResources().getDimension(R.dimen._20sdp), (int) context.getResources().getDimension(R.dimen._48sdp), false);

        return smallMarker;
    }

    public static String getFirstDayOnWeek() {
        // get today and clear time of day
        String startDayInWeek;
        DateFormat dateFormat = new DateFormat();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

// get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        int day = Integer.parseInt(simpleDateFormat1.format(cal.getTime()) + "");
        if (getLanguage().equalsIgnoreCase("en")) {
            day = day - 1;
        }
        if (day > 9) {
            startDayInWeek = simpleDateFormat3.format(cal.getTime()) + "-" + simpleDateFormat2.format(cal.getTime()) + "-" + day;

        } else {
            startDayInWeek = simpleDateFormat3.format(cal.getTime()) + "-" + simpleDateFormat2.format(cal.getTime()) + "-0" + day;

        }


        return startDayInWeek;

    }

    public static String getLastDayOnWeek() {
        // get today and clear time of day
        String startDayInWeek;
        DateFormat dateFormat = new DateFormat();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

// get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        int day = Integer.parseInt(simpleDateFormat1.format(cal.getTime()) + "");
        if (getLanguage().equalsIgnoreCase("ar")) {
            day = day + 6;
        } else {
            day = day + 5;
        }
        if (day > 9) {
            startDayInWeek = simpleDateFormat3.format(cal.getTime()) + "-" + simpleDateFormat2.format(cal.getTime()) + "-" + day;

        } else {
            startDayInWeek = simpleDateFormat3.format(cal.getTime()) + "-" + simpleDateFormat2.format(cal.getTime()) + "-0" + day;

        }

        Log.e("startDayInWeek", "" + startDayInWeek);
        return startDayInWeek;

    }

    public static String getCheckDate(int i) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);


        if (i == 0) {
            return todayAsString;
        } else
            return tomorrowAsString;

    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static byte[] BitMapToByte(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return b;
    }

    public static void next7Days() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        for (int i = 0; i > -7; i--) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            Log.e("day " + i, day);
        }
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            Log.e("day " + i, day);
        }
    }

    public static String convert24TimeTo12(String time) {

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            Log.e("timetime", new SimpleDateFormat("hh:mm aa").format(dateObj));
            return new SimpleDateFormat("hh:mm aa").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date converStringToDate(String dtStart, String format_value) {
        Log.e("dtStart", "" + dtStart);
        SimpleDateFormat format = new SimpleDateFormat(format_value);
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //
    public static void getCalenderView(Date date
            , TextView sat, TextView sun, TextView mon, TextView tue, TextView wen, TextView thu, TextView fri) {
        HashMap<String, String> strings = new HashMap<>();

        DateFormat dateFormat = new DateFormat();
        //  for (int i = 0; i < 7; i++) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String dateName = dateFormat.format("EE", calendar.getTime()) + "";

        switch (dateName) {
            case "Sat":
                sat.setSelected(true);
                sun.setSelected(false);
                mon.setSelected(false);
                tue.setSelected(false);
                wen.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(false);
                for (int i = 0; i > -7; i--) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;
            case "Sun":
                sat.setSelected(false);
                sun.setSelected(true);
                mon.setSelected(false);
                tue.setSelected(false);
                wen.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(false);
                for (int i = 0; i < 7; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                    Log.e("dateFormat", dateFormat.format("EE dd", calendar1.getTime()) + "");
                }

                break;
            case "Mon":
                sat.setSelected(false);
                sun.setSelected(false);
                mon.setSelected(true);
                tue.setSelected(false);
                wen.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(false);
                for (int i = -1; i < 6; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;
            case "Tue":
                sat.setSelected(false);
                sun.setSelected(false);
                mon.setSelected(false);
                tue.setSelected(true);
                wen.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(false);
                for (int i = -2; i < 5; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;
            case "Wed":
                sat.setSelected(false);
                sun.setSelected(false);
                mon.setSelected(false);
                tue.setSelected(false);
                wen.setSelected(true);
                thu.setSelected(false);
                fri.setSelected(false);
                for (int i = -3; i < 4; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;
            case "Thu":
                sat.setSelected(false);
                sun.setSelected(false);
                mon.setSelected(false);
                tue.setSelected(false);
                wen.setSelected(false);
                thu.setSelected(true);
                fri.setSelected(false);
                for (int i = -4; i < 3; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;
            case "Fri":
                sat.setSelected(false);
                sun.setSelected(false);
                mon.setSelected(false);
                tue.setSelected(false);
                wen.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(true);
                for (int i = -5; i < 2; i++) {
                    Calendar calendar1 = new GregorianCalendar();
                    calendar1.setTime(date);
                    calendar1.add(Calendar.DATE, i);
                    strings.put(dateFormat.format("EE", calendar1.getTime()) + "", dateFormat.format("dd", calendar1.getTime()) + "");
                }
                break;

        }
        sat.setText(strings.get("Sat"));
        sun.setText(strings.get("Sun"));
        mon.setText(strings.get("Mon"));
        tue.setText(strings.get("Tue"));
        wen.setText(strings.get("Wed"));
        thu.setText(strings.get("Thu"));
        fri.setText(strings.get("Fri"));

        // }
       /* for (int i = -1; i > -7; i--) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, i);
            Log.e("day " + i, "" + calendar.getTime());
        }*/
    }

    public static void sendSMS(Context context, String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public static boolean hexChecker(String c, String value) {
        String string = value;
        return string.indexOf(c) > -1;
    }

    public static String getDay(Date date1) {
        String day = (String) DateFormat.format("dd-MM-yyyy", date1);
        return day;
    }

    public static String getMonth(Date date1) {
        String day = (String) DateFormat.format("MMMM-MM-yyyy", date1);
        return day;
    }

    public static Date getStartWeek() {
       /* Calendar c1 = Calendar.getInstance();
        //first day of week
        c1.set(Calendar.DAY_OF_WEEK, 1);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        //last day of week
        c1.set(Calendar.DAY_OF_WEEK, 7);

        int year7 = c1.get(Calendar.YEAR);
        int month7 = c1.get(Calendar.MONTH)+1;
        int day7 = c1.get(Calendar.DAY_OF_MONTH);*/

        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
        cal.add(Calendar.DATE, -i);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.setFirstDayOfWeek(Calendar.SATURDAY);

        return cal.getTime();
    }

    public static Date getEndWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
        cal.add(Calendar.DATE, -i);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.setFirstDayOfWeek(Calendar.SATURDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY
        );
        return cal.getTime();
    }

    public static Date getFirstDayOfCurrentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Log.e("Start Day1", " Day1 : " + calendar.getTime());

        return calendar.getTime();
    }

    public static Date getLastDayOfCurrentMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        Date lastDayOfMonth = cal.getTime();
        Log.e("Last Day1", "Last Day 1: " + lastDayOfMonth);

        return cal.getTime();
    }

    public static void setImage(String url, final ImageView imageView, ImageLoader imageLoader) {
        List<Bitmap> list = MemoryCacheUtils.findCachedBitmapsForImageUri(url, ImageLoader.getInstance().getMemoryCache());
        Log.e("list.size()", "" + list.size());
        Log.e("list.size()", "" + isDiskCache(url));
        if (list.size() > 0 || isDiskCache(url)) {
            imageLoader.displayImage(url, imageView);
        } else {
            imageLoader.loadImage(url, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }
    public static void setImageSmall_50(String url, final ImageView imageView, ImageLoader imageLoader) {
        ImageSize targetSize = new ImageSize(50, 50); // result Bitmap will be fit to this size

        List<Bitmap> list = MemoryCacheUtils.findCachedBitmapsForImageUri(url, ImageLoader.getInstance().getMemoryCache());
        Log.e("list.size()", "" + list.size());
        Log.e("list.size()", "" + isDiskCache(url));
        if (list.size() > 0 || isDiskCache(url)) {
            imageLoader.displayImage(url, imageView);
        } else {
            imageLoader.loadImage(url,targetSize, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    public static boolean isDiskCache(String url) {
        File file = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());
        return file != null;
    }
    public static void setHtmlToTextView(TextView htmlToTextView, String value){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            htmlToTextView.setText(Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT));
        } else {
            htmlToTextView.setText(Html.fromHtml(value));
        }
    }
}
