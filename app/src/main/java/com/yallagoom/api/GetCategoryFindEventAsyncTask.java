package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.entity.Sport;
import com.yallagoom.interfaces.GetSportCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetCategoryFindEventAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private KProgressHUD progress;
    private String error;
    private String language;
    private GetSportCallback getSportCallback;
    private Sport sport;

    public GetCategoryFindEventAsyncTask(Context context, GetSportCallback getSportCallback) {
        mContext = context;
        this.getSportCallback = getSportCallback;
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

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.sports);
        builder.get();
        builder.header("Authorization", Constant.API_KEY);
        Request request = builder.build();

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
                    sport = new Gson().fromJson(data.toString(), Sport.class);
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
            getSportCallback.processFinish(sport);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
