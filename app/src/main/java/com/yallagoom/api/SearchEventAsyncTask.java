package com.yallagoom.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.SearchEventResultActivity;
import com.yallagoom.entity.Event;
import com.yallagoom.fragment.eventTapFragment.FindEventFragment;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class SearchEventAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final String title;
    private final int idSegmented1Sellect;
    private final int catId;
    private final int dateIdxSeg;
    private final String checkDateType;
    private final double lat;
    private final double lng;
    private KProgressHUD progress;
    private String error;
    private Event nearEvent;

    public SearchEventAsyncTask(Context context, String title, int catId, int idSegmented1Sellect
            , int dateIdxSeg, String checkDateType, double lat, double lng) {
        mContext = context;
        this.title = title;
        this.idSegmented1Sellect = idSegmented1Sellect;
        this.catId = catId;
        this.dateIdxSeg = dateIdxSeg;
        this.checkDateType = checkDateType;
        this.lat = lat;
        this.lng = lng;

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
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (title != null) {
            req.addFormDataPart("EventTitle", title);
        }
        if (catId != -1) {
            req.addFormDataPart("EventCategoryId", catId + "");
        }
        if (idSegmented1Sellect!=-1){
            req.addFormDataPart("IsFree", idSegmented1Sellect + "");
        }
        if (dateIdxSeg == 0) {
            req.addFormDataPart("operator", "specific_date");
            req.addFormDataPart("StartDateFrom", ToolUtils.getCheckDate(0));

        } else if (dateIdxSeg == 1) {
            req.addFormDataPart("operator", "specific_date");
            req.addFormDataPart("StartDateFrom", ToolUtils.getCheckDate(1));


        } else if (dateIdxSeg == 2) {
            req.addFormDataPart("operator ", "between");
            req.addFormDataPart("StartDateFrom ", ToolUtils.getFirstDayOnWeek());
            req.addFormDataPart("StartDateTo ", ToolUtils.getLastDayOnWeek());

        } else {
            if (!FindEventFragment.choose_dateEditText.getText().toString().equalsIgnoreCase("")) {
                req.addFormDataPart("operator ", "between");
                req.addFormDataPart("StartDateFrom ", FindEventFragment.start_date);
                req.addFormDataPart("StartDateTo ", FindEventFragment.end_date);
            }
        }
        if (lat != -1) {
            req.addFormDataPart("lat ", lat + "");
            req.addFormDataPart("lng ", lng + "");
        }

        req.addFormDataPart("distance ", Constant.defultDistance);

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.search_event);
        Log.e("response", "" + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        builder.post(req.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
       Log.e("bodyToString",""+ ToolUtils.bodyToString(request));
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + response.code());
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("SaveSportsAsyncTask", "" + jsonObject);
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    JSONObject data = jsonObject.getJSONObject(Constant.data);
                    nearEvent = new Gson().fromJson(data.toString(), Event.class);
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
            Intent intent=new Intent(mContext, SearchEventResultActivity.class);
            intent.putExtra("data",nearEvent);
            mContext.startActivity(intent);

        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
