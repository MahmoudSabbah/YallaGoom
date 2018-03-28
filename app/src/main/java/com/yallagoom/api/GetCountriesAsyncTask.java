package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Country;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;


import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetCountriesAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final KProgressHUD kProgressHUD;
    private KProgressHUD progress;
    private String error;
    private String language;
    private GetCountriesCallback getCountriesCallback;
    private Country country;

    public GetCountriesAsyncTask(Context context, KProgressHUD kProgressHUD, GetCountriesCallback getCountriesCallback) {
        mContext = context;
        this.getCountriesCallback = getCountriesCallback;
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

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.countries);
        builder.get();
        builder.header("Authorization", Constant.API_KEY);
        Request request = builder.build();

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("urlData", "" + Constant.urlData + Constant.countries);
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("jsonObject", "" + jsonObject);
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                } else {
                    JSONObject data = jsonObject.getJSONObject("data");
                    country = new Gson().fromJson(data.toString(), Country.class);
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
            getCountriesCallback.processFinish(country);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
