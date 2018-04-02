package com.yallagoom.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UpdateUserApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final HashMap<String, String> hashMapsData;
    // private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;
    private JSONObject jsonObject;
    private JSONObject data;

    public UpdateUserApiAsyncTask(Context context, HashMap<String, String> hashMapsData) {
        mContext = context;
        this.hashMapsData = hashMapsData;
    }

    @Override
    protected void onPreExecute() {
        progress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mContext.getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f).show();

    }

    @Override
    protected Integer doInBackground(String[] params) {

        FormBody.Builder formBody = new FormBody.Builder();

        for (int i = 0; i < hashMapsData.size(); i++) {
            String key = (String) hashMapsData.keySet().toArray()[i];
            formBody.add(key, hashMapsData.get(key));
        }

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.profile_update);
        builder.put(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    data = jsonObject.getJSONObject(Constant.data);
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
            SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.userData);
            shared.putString(Constant.allUserData, data.toString());
            shared.apply();
            ToolUtils.viewToast(mContext, mContext.getString(R.string.update_status));
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
