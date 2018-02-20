package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.HomeActivity;
import com.yallagoom.activity.SplashScreenActivity;
import com.yallagoom.entity.Country;
import com.yallagoom.entity.User;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.interfaces.LoginFirstStepCallback;
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

public class LoginfirstStepAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private KProgressHUD progress;
    private String error;
    private String language;
    private LoginFirstStepCallback loginFirstStepCallback;
    private User user;

    public LoginfirstStepAsyncTask(Context context, LoginFirstStepCallback loginFirstStepCallback) {
        mContext = context;
        this.loginFirstStepCallback = loginFirstStepCallback;
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
        RequestBody formBody = new FormBody.Builder()
                .add("email_or_mobile", params[0])
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.login);
        builder.post(formBody);
        builder.header("Authorization", Constant.API_KEY);
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
                     user = new Gson().fromJson(data.toString(), User.class);
                 //   Constant.user = user;
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
            loginFirstStepCallback.processFinish(user);
         /*   SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.loginCheck);
            shared.putBoolean(Constant.verification_check, true).apply();
            Intent intent = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();*/
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
