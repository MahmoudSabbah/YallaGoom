package com.oxygen.yallagoom.api.event;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Event;
import com.oxygen.yallagoom.interfaces.MyEventCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetUnAuthorizeEventsClickAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private SmartRefreshLayout smartRefreshLayout;
    private KProgressHUD progress;
    private String error;
    private MyEventCallback myEventCallback;
    private Event.DataEvent myEvent;
    private Event upcommingEvents;

    public GetUnAuthorizeEventsClickAsyncTask(Context context, MyEventCallback myEventCallback) {
        mContext = context;
        this.myEventCallback = myEventCallback;
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
        Log.e("event_id2",""+params[0]);
        RequestBody formBody = new FormBody.Builder()
                .add("event_id",params[0])
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.show_details_for_un_authorize);
        builder.post(formBody);
        builder.header("Authorization","Bearer " + Constant.API_KEY);
        Request request = builder.build();
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
                    JSONArray data = jsonObject.getJSONArray(Constant.data);
                    myEvent = new Gson().fromJson(data.get(0).toString(), Event.DataEvent.class);

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
            myEventCallback.processFinish(myEvent );
        } else {

            ToolUtils.viewToast(mContext, error);
        }
    }
}
