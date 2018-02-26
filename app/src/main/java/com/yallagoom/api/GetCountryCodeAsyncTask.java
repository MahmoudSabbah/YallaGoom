package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Discover;
import com.yallagoom.interfaces.DiscoverCallback;
import com.yallagoom.interfaces.GetCountryCodeCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetCountryCodeAsyncTask extends AsyncTask<String, String, String> {
    private final Context mContext;
    private final GetCountryCodeCallback getCountryCodeCallback;
    private KProgressHUD progress;
    private String error;
    private String code_3;

    public GetCountryCodeAsyncTask(Context context, GetCountryCodeCallback getCountryCodeCallback) {
        mContext = context;
        this.getCountryCodeCallback = getCountryCodeCallback;

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
    protected String doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.country_code_url);
        builder.post(formBody.build());
        Request request = builder.build();
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + response.code());
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                String status = jsonObject.getString(Constant.status_callback);
                if (status.equalsIgnoreCase("success")) {
                code_3=jsonObject.getString("countryCode");
                } else {


                }
                return status;
            } else {
                error = "Internal Server Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }


    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if (status.equalsIgnoreCase("success")) {
            getCountryCodeCallback.processFinish(code_3,progress);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
