package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.interfaces.SearchFriendsCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class SearchFriendsDefaultAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;

    private SearchFriendsCallback searchFriendsCallback;
    private KProgressHUD progress;
    private String error;
    private Player playerList;

    public SearchFriendsDefaultAsyncTask(Context context, SearchFriendsCallback searchFriendsCallback) {
        mContext = context;
        this.searchFriendsCallback = searchFriendsCallback;

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
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        req.addFormDataPart("type", "default");

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.friend_search);
        builder.post(req.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));

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
                    playerList = new Gson().fromJson(data.toString(), Player.class);
                    Log.e("playerList", "" + playerList.getData().size());
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
            searchFriendsCallback.finishProcess(playerList);
        } else {

            ToolUtils.viewToast(mContext, error);
        }
    }
}
