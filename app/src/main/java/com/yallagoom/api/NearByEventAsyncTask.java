package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.entity.Event;
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

public class NearByEventAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final int flag;
    private SmartRefreshLayout smartRefreshLayout;
    private KProgressHUD progress;
    private String error;
    private NearEventCallback nearEventCallback;
    private Event nearEvent;

    public NearByEventAsyncTask(Context context, int flag, SmartRefreshLayout smartRefreshLayout, NearEventCallback nearEventCallback) {
        mContext = context;
        this.nearEventCallback = nearEventCallback;
        this.smartRefreshLayout = smartRefreshLayout;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        progress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mContext.getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
                /*.setWindowColor(R.color.color_ffba00)*/
        // .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        RequestBody formBody = new FormBody.Builder()
                .add(Constant.lat, params[0])
                .add(Constant.lng, params[1])
                .add(Constant.distance, params[2])
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.nearby);
        builder.post(formBody);
        builder.header("Authorization", "Bearer " + Constant.API_KEY);
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
        //  progress.dismiss();
        if (status == 1) {
            if (flag == 0) {
                nearEventCallback.processFinish(nearEvent);
            } else {
                nearEventCallback.processFinish(nearEvent);

            }
        } else {
            if (smartRefreshLayout != null)
                smartRefreshLayout.finishRefresh();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
