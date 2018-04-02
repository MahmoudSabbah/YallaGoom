package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.SplashScreenActivity;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class LogOutApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private KProgressHUD progress;
    private String error;

    public LogOutApiAsyncTask(Context context) {
        mContext = context;
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
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.logout);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    Log.e("jsonObjectLog",""+jsonObject);
                    //data = jsonObject.getJSONObject(Constant.data);
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
            SharedPreferences.Editor sharedUserData = ToolUtils.setSharedPrefernce(mContext, Constant.userData);
            SharedPreferences.Editor sharedCheck = ToolUtils.setSharedPrefernce(mContext, Constant.loginCheck);
            sharedUserData.putString(Constant.allUserData, null);
            sharedUserData.putString(Constant.userToken, null);
            sharedUserData.apply();
            sharedCheck.putBoolean(Constant.verification_check, false).apply();
            sharedCheck.apply();
            Intent intent = new Intent(mContext, SplashScreenActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
          //  ToolUtils.viewToast(mContext, mContext.getString(R.string.update_status));
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
