package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.AllSport;
import com.yallagoom.entity.SportObject;
import com.yallagoom.interfaces.GetSportCallback;
import com.yallagoom.interfaces.GetUserSportCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetUserSportsListAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final GetUserSportCallback getUserSportCallback;
    private KProgressHUD progress;
    private String error;
    private String language;
    private Map<Integer ,Integer> userSportID;
    private SportObject[] allSport;

    public GetUserSportsListAsyncTask(Context context, GetUserSportCallback getUserSportCallback) {
        mContext = context;
        this.getUserSportCallback = getUserSportCallback;
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

    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.getUserSports);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        userSportID = new HashMap<>();
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
                    JSONArray userSport = data.getJSONArray("user_sports");
                    for (int i = 0; i < userSport.length(); i++) {
                        JSONObject id = userSport.getJSONObject(i);
                        userSportID.put(id.getInt("sport_id"),id.getInt("rate"));
                    }
                    allSport = new Gson().fromJson(data.getString("all_sports"), SportObject[].class);
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
            getUserSportCallback.processFinish(allSport, userSportID);
        } else {
            getUserSportCallback.processFinish(null, null);
            ToolUtils.viewToast(mContext, error);
        }
    }
}
