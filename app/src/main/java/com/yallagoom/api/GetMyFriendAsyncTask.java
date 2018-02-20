package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.MyFriendList;
import com.yallagoom.entity.MyFriends;
import com.yallagoom.interfaces.AcceptFriendCallback;
import com.yallagoom.interfaces.GetMyFriendCallback;
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

public class GetMyFriendAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final GetMyFriendListCallback getMyFriendCallback;
    private KProgressHUD progress;
    private String error;
    private MyFriendList myFriend;

    public GetMyFriendAsyncTask(Context context, GetMyFriendListCallback getMyFriendCallback) {
        mContext = context;
        this.getMyFriendCallback = getMyFriendCallback;

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
              //  .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.MyFriendList);
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
                    Log.e("datadata", "" + data);

                    myFriend = new Gson().fromJson(data.toString(), MyFriendList.class);

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
            getMyFriendCallback.processFinish(myFriend);
        } else {
            progress.dismiss();
            ToolUtils.viewToast(mContext, error);
        }
    }
}
