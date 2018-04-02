package com.yallagoom.api;

import android.content.Context;
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

public class GetPlayersApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final StringResultCallback stringResultCallback;
    private final int id;
    private KProgressHUD progress;
    private String error;
    private JSONArray dataResult;
    private JSONObject jsonObject;

    public GetPlayersApiAsyncTask(Context context, int id, StringResultCallback stringResultCallback) {
        mContext = context;
        this.stringResultCallback = stringResultCallback;
        this.id = id;
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
        formBody.add("code_3", "QAT");
        if (id != -1) {
            formBody.add("participant_id", id + "");
        }

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.players_list);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + Constant.API_KEY);
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
                    //dataResult = jsonObject.getJSONArray(Constant.data);
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
            if (id == -1) {
                try {
                    dataResult = jsonObject.getJSONArray(Constant.data);
                    stringResultCallback.processFinish(dataResult + "", progress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    stringResultCallback.processFinish(jsonObject.getJSONObject(Constant.data) + "", progress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
