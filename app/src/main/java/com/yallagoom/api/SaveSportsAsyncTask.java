package com.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.HomeActivity;
import com.yallagoom.entity.Country;
import com.yallagoom.entity.SportSave;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class SaveSportsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final List<SportSave> hashMaps;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;

    public SaveSportsAsyncTask(Context context, List<SportSave> hashMaps , StringResultCallback stringResultCallback) {
        mContext = context;
        this.hashMaps = hashMaps;
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
                /*.setWindowColor(R.color.color_ffba00)*/
                .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        for (int i = 0; i < hashMaps.size(); i++) {
            formBody.add("sport_id[]", hashMaps.get(i).getSport_id()+"");
            formBody.add("rate[]", hashMaps.get(i).getRate()+"");

        }
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.user_sport);
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + response.code());
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("SaveSportsAsyncTask", "" + jsonObject);
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
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
           /* ArrayList<String> sportData=new ArrayList<>();
            for (int i = 0; i < hashMaps.size(); i++) {
                sportData.add(hashMaps.get(i).getSport_id()+"-"+hashMaps.get(i).getRate());
            }
            ToolUtils.setArrayToShared(mContext,Constant.userData,Constant.userSport,sportData);*/
            ToolUtils.viewToast(mContext, mContext.getString(R.string.save_sport));
            stringResultCallback.processFinish("",progress);

        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
