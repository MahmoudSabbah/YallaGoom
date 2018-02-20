package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Event;
import com.yallagoom.entity.Player;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UpdateEventAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private int event_id;
    private int EventCategoryId;
    private String eventTitle;
    private String startEventDate;
    private String endEventDate;
    private String startEventTime;
    private String endEventTime;
    private Bitmap eventImage;
    private String eventDescription;
    private int isFree;
    private String cost;
    private String organizerName;
    private String organizerDescription;
    private int privateOrPublic;
    private String activityTitle;
    private final String activityStartDate;
    private final String activityEndDate;
    private String activityStartTime;
    private final String activityEndTime;
    private final String activityDescription;
    private final double eventLat;
    private final double eventLong;
    private KProgressHUD progress;
    private String error;
    private Event.DataEvent myEvent;

    public UpdateEventAsyncTask(Context context, int event_id, int EventCategoryId, String EventTitle, String StartEventDate, String EndEventDate,
                                String StartEventTime, String EndEventTime, Bitmap EventImage, String EventDescription, int IsFree,
                                String Cost, String OrganizerName, String OrganizerDescription, int PrivateOrPublic,
                                String ActivityTitle, String ActivityStartDate, String ActivityEndDate, String ActivityStartTime,
                                String ActivityEndTime, String ActivityDescription, double EventLat, double EventLong
    ) {
        mContext = context;
        this.event_id = event_id;
        this.EventCategoryId = EventCategoryId;
        eventTitle = EventTitle;
        startEventDate = StartEventDate;
        endEventDate = EndEventDate;
        startEventTime = StartEventTime;
        endEventTime = EndEventTime;
        eventImage = EventImage;
        eventDescription = EventDescription;
        isFree = IsFree;
        cost = Cost;
        organizerName = OrganizerName;
        organizerDescription = OrganizerDescription;
        privateOrPublic = PrivateOrPublic;
        activityTitle = ActivityTitle;
        activityStartDate = ActivityStartDate;
        activityEndDate = ActivityEndDate;
        activityStartTime = ActivityStartTime;
        activityEndTime = ActivityEndTime;
        activityDescription = ActivityDescription;
        eventLat = EventLat;
        eventLong = EventLong;
    }

    @Override
    protected void onPreExecute() {
        progress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mContext.getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                /*.setWindowColor(R.color.color_ffba00)*/
                .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("id", event_id + "");
        if (EventCategoryId != -1)
            formBody.add("EventCategoryId", EventCategoryId + "");
        formBody.add("EventTitle", eventTitle);

        formBody.add("StartEventDate", startEventDate);
        if (!endEventDate.equalsIgnoreCase("")) {
            Log.e("endEventDate", "" + endEventDate);
            formBody.add("EndEventDate", endEventDate);
        }
        formBody.add("StartEventTime", startEventTime);
        if (!endEventTime.equalsIgnoreCase(""))
            formBody.add("EndEventTime", endEventTime);
        //  if (eventImage != null)
        // formBody.add("EventImage", "eventImage.png", RequestBody.create(MEDIA_TYPE_PNG, ToolUtils.BitMapToByte(eventImage)));
        if (!eventDescription.equalsIgnoreCase(""))
            formBody.add("EventDescription", eventDescription);
        formBody.add("IsFree", isFree + "");
        if (isFree == 1)
            formBody.add("Cost", cost + "");
        if (!organizerName.equalsIgnoreCase(""))
            formBody.add("OrganizerName", organizerName);
        if (!organizerDescription.equalsIgnoreCase(""))
            formBody.add("OrganizerDescription", organizerDescription);
        if (privateOrPublic == 1) {
            formBody.add("PrivateOrPublic", "public");
        } else {
            formBody.add("PrivateOrPublic", "private");

        }
        if (!activityTitle.equalsIgnoreCase(""))
            formBody.add("ActivityTitle[]", activityTitle);
        if (!activityStartDate.equalsIgnoreCase(""))
            formBody.add("ActivityStartDate[]", activityStartDate);
        if (!activityEndDate.equalsIgnoreCase(""))
            formBody.add("ActivityEndDate[]", activityEndDate);
        if (!activityStartTime.equalsIgnoreCase(""))
            formBody.add("ActivityStartTime[]", activityStartTime);
        if (!activityEndTime.equalsIgnoreCase(""))
            formBody.add("ActivityEndTime[]", activityEndTime);
        if (!activityDescription.equalsIgnoreCase(""))
            formBody.add("ActivityDescription[]", activityDescription);
        formBody.add("EventLat", eventLat + "");
        formBody.add("EventLong", eventLong + "");

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.update_event);
        builder.put(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + response.code());
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    JSONObject data = jsonObject.getJSONObject(Constant.data);
                    myEvent = new Gson().fromJson(data.toString(), Event.DataEvent.class);

                }
                return status;
            } else {
                error = "Internal Server Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        progress.dismiss();
        if (status == 1) {
            ToolUtils.viewToast(mContext, mContext.getString(R.string.update_event));
            Intent intent=new Intent();
            intent.putExtra("UpdateEvent",myEvent);
            ((Activity)mContext).setResult(102,intent);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
