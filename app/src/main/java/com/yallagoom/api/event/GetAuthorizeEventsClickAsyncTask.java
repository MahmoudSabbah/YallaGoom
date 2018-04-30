package com.yallagoom.api.event;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.entity.Event;
import com.yallagoom.interfaces.MyEventCallback;
import com.yallagoom.interfaces.MyEventHomeCallback;
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

public class GetAuthorizeEventsClickAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private SmartRefreshLayout smartRefreshLayout;
    private KProgressHUD progress;
    private String error;
    private MyEventCallback myEventCallback;
    private Event.DataEvent myEvent;
    private Event upcommingEvents;

    public GetAuthorizeEventsClickAsyncTask(Context context, MyEventCallback myEventCallback) {
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
        Log.e("event_id",""+params[0]);
        RequestBody formBody = new FormBody.Builder()
                .add("event_id",params[0])
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.show_event);
        builder.post(formBody);
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
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
            myEventCallback.processFinish(myEvent );
        } else {

            ToolUtils.viewToast(mContext, error);
        }
    }
}
