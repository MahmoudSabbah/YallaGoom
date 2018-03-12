package com.yallagoom.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.BookingNowActivity;
import com.yallagoom.entity.Matches.LeagueMatches;
import com.yallagoom.entity.Matches.LeagueMatchesList;
import com.yallagoom.entity.Matches.MatchesList;
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

public class GetMatchesApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final MatchesApiCallback matchesApiCallback;
    private final String type;
    private KProgressHUD progress;
    private String error;
    private JSONObject dataResult;
    private LeagueMatchesList leagueMatchesList;

    public GetMatchesApiAsyncTask(Context context, String type, MatchesApiCallback matchesApiCallback) {
        mContext = context;
        this.matchesApiCallback = matchesApiCallback;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        progress = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(mContext.getString(R.string.please_wait))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        if (type.equalsIgnoreCase("Default")) {
            progress.show();
        }
    }

    @Override
    protected Integer doInBackground(String[] params) {

        leagueMatchesList = new LeagueMatchesList();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(" from_date", params[0]);
        formBody.add(" to_date", params[1]);
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.sports_matches_list);
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
                    Iterator iterator = dataResult.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        //    if (key.equalsIgnoreCase("LaLiga")||key.equalsIgnoreCase("Premier League")){
                        LeagueMatches leagueMatches = new LeagueMatches();

                        JSONArray data = dataResult.getJSONArray(key);

                        MatchesList[] matchesList = new Gson().fromJson(data.toString(), MatchesList[].class);
                        Log.e("leagueMeatches", "" + matchesList[0].getStatus());
                        leagueMatches.setLeagueName(key);
                        leagueMatches.setMatchesLists2(matchesList);

                    /*    for (int i = 0; i < data.length(); i++) {
                            MatchesList matchesList = new Gson().fromJson(data.getJSONObject(i).toString(), MatchesList.class);
                            Log.e("getScheduled", "" + matchesList.getScheduled());
                            leagueMatches.getMatchesLists().add(matchesList);
                        }*/

                        leagueMatchesList.getLeagueMatches().add(leagueMatches);
                        //  }

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
            matchesApiCallback.processFinish(leagueMatchesList);
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
