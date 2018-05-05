package com.oxygen.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class AddGroupAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final List<Integer> id_list;
    private final Bitmap bitmap;
    private final String title;
    private KProgressHUD progress;
    private String error;
    private JSONObject data;

    public AddGroupAsyncTask(Context context, List<Integer> id_list, Bitmap bitmap, String title) {
        mContext = context;
        this.id_list = id_list;
        this.bitmap = bitmap;
        this.title = title;

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
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (bitmap != null)
            req.addFormDataPart("group_img", "group_img.png", RequestBody.create(MEDIA_TYPE_PNG, ToolUtils.BitMapToByte(bitmap)));
        req.addFormDataPart("title", title);
        for (int i = 0; i < id_list.size(); i++) {
            req.addFormDataPart("members_id[]", id_list.get(i) + "");
        }

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.add_group);
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
                    data = jsonObject.getJSONObject(Constant.data);
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
            ToolUtils.viewToast(mContext, mContext.getString(R.string.add_group));
            Intent intent = new Intent();
            intent.putExtra("data", "" + data);
            ((Activity) mContext).setResult(102, intent);
            ((Activity) mContext).finish();
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
