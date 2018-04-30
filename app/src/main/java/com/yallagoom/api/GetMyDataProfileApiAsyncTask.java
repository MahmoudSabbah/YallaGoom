package com.yallagoom.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetMyDataProfileApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;
    private JSONArray dataResult;
    private JSONObject jsonObject;
    private final KProgressHUD kProgressHUD;
    private JSONObject data;

    public GetMyDataProfileApiAsyncTask(Context context, KProgressHUD kProgressHUD, StringResultCallback stringResultCallback) {
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
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.get_user_data);
        builder.post(formBody.build());
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
                    SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.userData);
                    shared.putString(Constant.allUserData, data.toString());
                    shared.putInt(Constant.userId, data.getInt("id"));
                    shared.apply();

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
            stringResultCallback.processFinish(data.toString(),progress);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
