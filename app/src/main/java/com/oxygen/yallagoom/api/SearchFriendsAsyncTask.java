package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Player;
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

public class SearchFriendsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final int minage;
    private final int maxage;
    private final String rate;
    private final int sport_id;
    private int country_id;
    private SearchFriendsCallback searchFriendsCallback;
    private String type;
    private String player_name;
    private String gender;
    private KProgressHUD progress;
    private String error;
    private Player playerList;

    public SearchFriendsAsyncTask(Context context, String type, String player_name, String gender,
                                  int country_id, int minage, int maxage, String rate, int sport_id, SearchFriendsCallback searchFriendsCallback) {
        mContext = context;
        this.searchFriendsCallback = searchFriendsCallback;
        this.type = type;
        this.player_name = player_name;
        this.gender = gender;
        this.country_id = country_id;
        this.minage = minage;
        this.maxage = maxage;
        this.rate = rate;
        this.sport_id = sport_id;
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
        // .show();
    }

    @Override
    protected Integer doInBackground(String[] params) {
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        req.addFormDataPart("type", type);
        if (!player_name.equalsIgnoreCase("")) {
            req.addFormDataPart("player_name", player_name);
        }
        if (!gender.equalsIgnoreCase("")) {
            req.addFormDataPart("gender", gender);
        }
        if (country_id != -1) {
            req.addFormDataPart("country_id", country_id + "");
        }
        if (minage != -1) {
            req.addFormDataPart("minage", minage + "");
        }
        if (maxage != -1) {
            req.addFormDataPart("maxage", maxage + "");
        }
        if (sport_id != -1) {
            req.addFormDataPart("sport_id", sport_id + "");
        }
        if (!rate.equalsIgnoreCase("")) {
            req.addFormDataPart("rate", rate + "");
        }

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.friend_search);
        builder.post(req.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString",""+ ToolUtils.bodyToString(request));

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
                    Log.e("playerList",""+playerList.getData().size());
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
        //  progress.dismiss();
        if (status == 1) {
            searchFriendsCallback.finishProcess(playerList);
        } else {

            ToolUtils.viewToast(mContext, error);
        }
    }
}
