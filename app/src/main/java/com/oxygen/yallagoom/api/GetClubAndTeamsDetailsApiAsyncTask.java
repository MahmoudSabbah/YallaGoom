package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetClubAndTeamsDetailsApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;
    private JSONArray dataResult;
    private String id;
    private JSONObject dataResultObjtect;

    public GetClubAndTeamsDetailsApiAsyncTask(Context context, StringResultCallback stringResultCallback) {
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
                .setDimAmount(0.5f).show();

    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        id = params[0];
        formBody.add("code_3", "QAT");
        if (!id.equalsIgnoreCase("-1")) {
            formBody.add("club_id", id);
        }

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.clubs_list);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + Constant.API_KEY);
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));

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
                    if (id.equalsIgnoreCase("-1")) {
                        dataResult = jsonObject.getJSONArray(Constant.data);
                    } else {
                        dataResultObjtect = jsonObject.getJSONObject(Constant.data);
                    }
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
            if (id.equalsIgnoreCase("-1")) {
                stringResultCallback.processFinish(dataResult + "", progress);
            } else {
                stringResultCallback.processFinish(dataResultObjtect + "", progress);
            }
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
