package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Player;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class NewEventAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private int eventCategoryId;
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
    private ArrayList<Player.PlayerList> selectPlayerLists;
    private KProgressHUD progress;
    private String error;
    private int eventId;

    public NewEventAsyncTask(Context context, int eventCategoryId, String EventTitle, String StartEventDate, String EndEventDate,
                             String StartEventTime, String EndEventTime, Bitmap EventImage, String EventDescription, int IsFree,
                             String Cost, String OrganizerName, String OrganizerDescription, int PrivateOrPublic,
                             String ActivityTitle, String ActivityStartDate, String ActivityEndDate, String ActivityStartTime,
                             String ActivityEndTime, String ActivityDescription, double EventLat, double EventLong,
                             ArrayList<Player.PlayerList> selectPlayerLists) {
        mContext = context;
        this.eventCategoryId = eventCategoryId;
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
        this.selectPlayerLists = selectPlayerLists;
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
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        req.addFormDataPart("EventCategoryId", eventCategoryId + "");
        req.addFormDataPart("EventTitle", eventTitle);

        req.addFormDataPart("StartEventDate", startEventDate);
        if (!endEventDate.equalsIgnoreCase("")) {
            Log.e("endEventDate", "" + endEventDate);
            req.addFormDataPart("EndEventDate", endEventDate);
        }
        req.addFormDataPart("StartEventTime", startEventTime);
        if (!endEventTime.equalsIgnoreCase(""))
            req.addFormDataPart("EndEventTime", endEventTime);
        if (eventImage != null)
            req.addFormDataPart("EventImage", "eventImage.png", RequestBody.create(MEDIA_TYPE_PNG, ToolUtils.BitMapToByte(eventImage)));
        if (!eventDescription.equalsIgnoreCase(""))
            req.addFormDataPart("EventDescription", eventDescription);
        req.addFormDataPart("IsFree", isFree + "");
        if (isFree == 1)
            req.addFormDataPart("Cost", cost + "");
        if (!organizerName.equalsIgnoreCase(""))
            req.addFormDataPart("OrganizerName", organizerName);
        if (!organizerDescription.equalsIgnoreCase(""))
            req.addFormDataPart("OrganizerDescription", organizerDescription);
        if (privateOrPublic == 0) {
            req.addFormDataPart("PrivateOrPublic", "public");
        } else {
            req.addFormDataPart("PrivateOrPublic", "private");

        }
        if (!activityTitle.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityTitle[]", activityTitle);
        if (!activityStartDate.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityStartDate[]", activityStartDate);
        if (!activityEndDate.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityEndDate[]", activityEndDate);
        if (!activityStartTime.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityStartTime[]", activityStartTime);
        if (!activityEndTime.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityEndTime[]", activityEndTime);
        if (!activityDescription.equalsIgnoreCase(""))
            req.addFormDataPart("ActivityDescription[]", activityDescription);
        req.addFormDataPart("EventLat", eventLat + "");
        req.addFormDataPart("EventLong", eventLong + "");

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.add_event);
        builder.post(req.build());
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
                    eventId = data.getInt("id");
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
            //ToolUtils.viewToast(mContext, "Add Event successfully");
            if (selectPlayerLists != null) {
                EventInvitationAsyncTask eventInvitationAsyncTask = new EventInvitationAsyncTask(mContext, selectPlayerLists, eventId);
                eventInvitationAsyncTask.execute();
            } else {
                ToolUtils.viewToast(mContext, mContext.getString(R.string.create_event_success));
                ((Activity) mContext).setResult(102);
                ((Activity) mContext).finish();

            }


        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
