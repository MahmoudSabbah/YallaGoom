package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.SportSave;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UpdatUserSportsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final List<SportSave> hashMaps;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;

    public UpdatUserSportsAsyncTask(Context context, List<SportSave> hashMaps , StringResultCallback stringResultCallback) {
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
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < hashMaps.size(); i++) {
            Log.e("hashMaps",""+hashMaps.get(i).getSport_id());
            req.addFormDataPart("sport_id[]", hashMaps.get(i).getSport_id()+"");
            req.addFormDataPart("rate[]", hashMaps.get(i).getRate()+"");

        }
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.updateUserSports);
        builder.post(req.build());
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
