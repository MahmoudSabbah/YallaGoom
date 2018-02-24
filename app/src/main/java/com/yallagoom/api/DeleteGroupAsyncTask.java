package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.interfaces.DeleteCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class DeleteGroupAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final DeleteCallback deleteCallback;
    private KProgressHUD progress;
    private String error;

    public DeleteGroupAsyncTask(Context context, DeleteCallback deleteCallback) {
        mContext = context;
        this.deleteCallback = deleteCallback;
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

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.delete_groups+"/"+params[0]);
        builder.delete();
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
                    JSONObject data = jsonObject.getJSONObject("data");
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
            ToolUtils.viewToast(mContext,mContext.getString(R.string.delete_group));
            deleteCallback.processFinish(0);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
