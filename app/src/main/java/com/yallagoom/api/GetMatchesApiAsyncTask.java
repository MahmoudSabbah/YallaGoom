package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Matches.LeagueMatches;
import com.yallagoom.entity.Matches.LeagueMatchesList;
import com.yallagoom.entity.Matches.MatchesList;
import com.yallagoom.entity.Matches.NewApi.FinalResultData;
import com.yallagoom.entity.Matches.NewApi.FinalResultData_Data;
import com.yallagoom.interfaces.MatchesApiCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    private FinalResultData finalResultData;

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
        finalResultData=new FinalResultData();
        ArrayList<String> dataStore = ToolUtils.getArrayOfCompAndClub(mContext);

        leagueMatchesList = new LeagueMatchesList();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(" from_date",params[0]);//2018-03-06
        formBody.add(" to_date", params[1]);
       /* for (int i=0;i<dataStore.size();i++){
            String[] value = dataStore.get(i).split("-");
            if (value[1].split(":")[0].equalsIgnoreCase("competitions")){
                formBody.add("competition_id[]",value[0]);
            }else {
                formBody.add("participants[]",value[0]);
            }
        }*/
       if (Constant.alpha3Country.equalsIgnoreCase("PSE")){
           formBody.add(" code_3", "ESP");
       }else {
           formBody.add(" code_3", Constant.alpha3Country);

       }
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.sports_matches_list_1);
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
                     finalResultData=new Gson().fromJson(jsonObject.toString(),FinalResultData.class);
/*
                    Log.e("finalResultData",""+finalResultData.getData().get(1).getData().get(0).getData().get(1).getStart_date());
*/

               /*     dataResult = jsonObject.getJSONObject(Constant.data);
                    Iterator iterator = dataResult.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        //   if (key.equalsIgnoreCase("LaLiga")||key.equalsIgnoreCase("Premier League")){
                        LeagueMatches leagueMatches = new LeagueMatches();

                        JSONArray data = dataResult.getJSONArray(key);

                        MatchesList[] matchesList = new Gson().fromJson(data.toString(), MatchesList[].class);
                        leagueMatches.setLeagueName(key);
                        leagueMatches.setMatchesLists2(matchesList);
                        leagueMatchesList.getLeagueMatches().add(leagueMatches);
                      //    }

                    }*/
                }
                return status;
            } else {
                error = "Internal Server Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            error=e.getMessage();
        }
        return 0;
    }


    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        progress.dismiss();
        if (status == 1) {
            matchesApiCallback.processFinish(finalResultData.getData());
        } else {
          //  ToolUtils.viewToast(mContext, error);
            matchesApiCallback.processFinish(finalResultData.getData());

        }
    }
}
