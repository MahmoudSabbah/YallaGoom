package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Country;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetChannelsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final KProgressHUD kProgressHUD;
    private KProgressHUD progress;
    private String error;
    private StringResultCallback stringResultCallback;
    private JSONArray dataResult;

    public GetChannelsAsyncTask(Context context, KProgressHUD kProgressHUD, StringResultCallback stringResultCallback) {
        mContext = context;
        this.stringResultCallback = stringResultCallback;
        this.kProgressHUD = kProgressHUD;
    }

    @Override
    protected void onPreExecute() {
        if (kProgressHUD == null) {
            progress = KProgressHUD.create(mContext)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(mContext.getString(R.string.please_wait))
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                /*.setWindowColor(R.color.color_ffba00)*/
                    .show();
        } else {
            progress = kProgressHUD;
        }
    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(" code_3", params[0]);
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.get_news_channel_ids_for_country);
        builder.post(formBody.build());
        builder.header("Authorization", Constant.API_KEY);
        Request request = builder.build();

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                } else {
                    dataResult = jsonObject.getJSONArray("data");
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
            stringResultCallback.processFinish(dataResult.toString(),progress);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
