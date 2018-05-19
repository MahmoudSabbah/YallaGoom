package com.oxygen.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class EventInvitationAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final ArrayList<Player.PlayerList> selectPlayerLists;
    private final int eventId;

    private KProgressHUD progress;
    private String error;

    public EventInvitationAsyncTask(Context context, ArrayList<Player.PlayerList> selectPlayerLists, int eventId) {
        mContext = context;
        this.selectPlayerLists = selectPlayerLists;
        this.eventId = eventId;
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
        progress.show();


    }

    @Override
    protected Integer doInBackground(String[] params) {
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < selectPlayerLists.size(); i++) {
            if (!selectPlayerLists.get(i).isInvited()){
                req.addFormDataPart("invitee_id[]", selectPlayerLists.get(i).getId() + "");
            }
        }
        req.addFormDataPart("event_id", eventId + "");


        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.EventInvitation);
        Log.e("response", "" + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        builder.post(req.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));
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
                    JSONArray inviataions = data.getJSONArray("inviataions");
                    Log.e("EventInvitation", "" + inviataions.length());

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
            ToolUtils.viewToast(mContext, "Invited successfully");
            ((Activity) mContext).setResult(102);
            ((Activity) mContext).finish();

        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
