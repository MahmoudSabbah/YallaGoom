package com.oxygen.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.interfaces.TicketDeatailsCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class TicketDetailsAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final boolean checkReview;
    private final TicketDeatailsCallback ticketDeatailsCallback;
    private KProgressHUD progress;
    private String error;
    private JSONObject dataResult;

    public TicketDetailsAsyncTask(Context context, boolean checkReview, TicketDeatailsCallback ticketDeatailsCallback) {
        mContext = context;
        this.checkReview = checkReview;
        this.ticketDeatailsCallback = ticketDeatailsCallback;
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
        if (!checkReview) {
            progress.show();
        }
    }

    @Override
    protected Integer doInBackground(String[] params) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add(" id", params[0]);
        Request.Builder builder = new Request.Builder();
        if (checkReview) {
            builder.url(Constant.urlData + Constant.show_ticket + "?page=" + params[1]);
        } else {
            builder.url(Constant.urlData + Constant.show_ticket);
        }
        builder.post(formBody.build());
        builder.header("Authorization", "Bearer " + Constant.API_KEY);
        Request request = builder.build();
        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("response", "" + params[0]);
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    dataResult = jsonObject.getJSONObject(Constant.data);

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
        if (!checkReview) {
            progress.dismiss();
        }
        if (status == 1) {
            ticketDeatailsCallback.processFinish(dataResult + "");

        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
