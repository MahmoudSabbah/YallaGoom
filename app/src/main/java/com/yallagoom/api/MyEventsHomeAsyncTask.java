package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.entity.Event;
import com.yallagoom.interfaces.MyEventHomeCallback;
import com.yallagoom.interfaces.NearEventCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class MyEventsHomeAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private SmartRefreshLayout smartRefreshLayout;
    private KProgressHUD progress;
    private String error;
    private MyEventHomeCallback myEventHomeCallback;
    private Event myEvent;
    private Event upcommingEvents;

    public MyEventsHomeAsyncTask(Context context, MyEventHomeCallback myEventHomeCallback) {
        mContext = context;
        this.myEventHomeCallback = myEventHomeCallback;
    }

    @Override
    protected void onPreExecute() {
        progress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mContext.getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        RequestBody formBody = new FormBody.Builder()
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.MyEventsHome);
        builder.post(formBody);
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("Bearer", "" + "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("jsonObject", "" + jsonObject);
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                } else {
                    JSONObject data = jsonObject.getJSONObject(Constant.data);
                    JSONObject user_own_events_list = data.getJSONObject("user_own_events_list");
                    JSONObject upcomming_events = data.getJSONObject("upcomming_events");
                    Log.e("user_own_events_list",""+upcomming_events);
                    myEvent = new Gson().fromJson(user_own_events_list.toString(), Event.class);
                    upcommingEvents = new Gson().fromJson(upcomming_events.toString(), Event.class);

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
        if (status == 1) {
            myEventHomeCallback.processFinish(myEvent,upcommingEvents ,progress);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
