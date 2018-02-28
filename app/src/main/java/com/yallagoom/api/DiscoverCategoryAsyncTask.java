package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.interfaces.TicketDeatailsCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class DiscoverCategoryAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;
    private JSONObject dataResult;

    public DiscoverCategoryAsyncTask(Context context, StringResultCallback stringResultCallback) {
        mContext = context;
        this.stringResultCallback = stringResultCallback;
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
                /*.setWindowColor(R.color.color_ffba00)*/
    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(" category_id", params[0]);
        formBody.add(" code_3", params[1]);
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.discover_category);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " +Constant.API_KEY);
        Request request = builder.build();
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + params[0]);
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    dataResult = jsonObject.getJSONObject(Constant.data);

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
            stringResultCallback.processFinish(dataResult + "");

        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
