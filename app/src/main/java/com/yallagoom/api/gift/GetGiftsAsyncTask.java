package com.yallagoom.api.gift;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Category;
import com.yallagoom.entity.gift.Gift;
import com.yallagoom.interfaces.GetCategoryCallback;
import com.yallagoom.interfaces.StringResultCallback;
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

public class GetGiftsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private KProgressHUD progress;
    private String error;
    private String language;
    private StringResultCallback stringResultCallback;
    private String arrayGiftsData;

    public GetGiftsAsyncTask(Context context, StringResultCallback stringResultCallback) {
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
                .setDimAmount(0.5f);
                /*.setWindowColor(R.color.color_ffba00)*/
             //   .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        RequestBody formBody = new FormBody.Builder()
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.all_gifts);
        builder.post(formBody);
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    arrayGiftsData=data.getJSONArray("data").toString();

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
            stringResultCallback.processFinish(arrayGiftsData,progress);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
