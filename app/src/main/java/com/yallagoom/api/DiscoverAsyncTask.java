package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Discover;
import com.yallagoom.entity.MyFriendList;
import com.yallagoom.interfaces.DiscoverCallback;
import com.yallagoom.interfaces.GetMyFriendListCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class DiscoverAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final DiscoverCallback discoverCallback;
    private KProgressHUD progress;
    private String error;
    private Discover discover;

    public DiscoverAsyncTask(Context context, KProgressHUD progress, DiscoverCallback discoverCallback) {
        mContext = context;
        this.discoverCallback = discoverCallback;
        this.progress = progress;

    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("code_3",params[0]);
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.discover_ticket);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + response.code());
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    JSONObject data = jsonObject.getJSONObject(Constant.data);
                    discover = new Gson().fromJson(data.toString(), Discover.class);

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
            discoverCallback.processFinish(discover);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
