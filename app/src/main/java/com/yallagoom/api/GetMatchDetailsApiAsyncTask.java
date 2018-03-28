package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Matches.LeagueMatches;
import com.yallagoom.entity.Matches.LeagueMatchesList;
import com.yallagoom.entity.Matches.MatchDetails;
import com.yallagoom.entity.Matches.MatchesList;
import com.yallagoom.interfaces.MatchDetailsCallback;
import com.yallagoom.interfaces.MatchesApiCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetMatchDetailsApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final MatchDetailsCallback matchDetailsCallback;
    private KProgressHUD progress;
    private String error;
    private JSONObject dataResult;
    private MatchDetails matchDetails;

    public GetMatchDetailsApiAsyncTask(Context context, MatchDetailsCallback matchDetailsCallback) {
        mContext = context;
        this.matchDetailsCallback = matchDetailsCallback;
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
        formBody.add(" id", params[0]);//2326394
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.match_details);
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
                    dataResult = jsonObject.getJSONObject(Constant.data);
                  //  matchDetails = new Gson().fromJson(dataResult.toString(), MatchDetails.class);
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
              matchDetailsCallback.processFinish(dataResult+"");
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
