package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UpdateInvitationStatusAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;


    private KProgressHUD progress;
    private String error;

    public UpdateInvitationStatusAsyncTask(Context context) {
        mContext = context;

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
        RequestBody formBody = new FormBody.Builder()
                .add("event_id", params[0])
                .add("invitation_status", params[1])
                .build();
    /*    MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        req.addFormDataPart("event_id", params[0] + "");
        req.addFormDataPart("invitation_status", params[1] + "");*/

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.ResponseToInvitation);
        Log.e("response", "" + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        builder.put(formBody);
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
            ToolUtils.viewToast(mContext, "Update successfully");
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
