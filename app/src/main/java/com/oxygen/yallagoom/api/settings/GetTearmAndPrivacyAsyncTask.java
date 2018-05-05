package com.oxygen.yallagoom.api.settings;

import android.content.Context;
import android.os.AsyncTask;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetTearmAndPrivacyAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private String error;
    private StringResultCallback stringResultCallback;
    private JSONObject dataResult;
    private KProgressHUD progress;
    private String description;

    public GetTearmAndPrivacyAsyncTask(Context context, StringResultCallback stringResultCallback) {
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

    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("title",params[0]);
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.get_constants_privacy_tearm);
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
                    dataResult = jsonObject.getJSONObject("data");
                    description=  dataResult.getString("description");
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
            stringResultCallback.processFinish(description,progress);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
