package com.yallagoom.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.User;
import com.yallagoom.fragment.SettingsFragment;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UpdateUseImagerApiAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final Bitmap bitmap;
    private final StringResultCallback stringResultCallback;
    private KProgressHUD progress;
    private String error;
    private JSONArray dataResult;
    private JSONObject jsonObject;
    private JSONObject data;

    public UpdateUseImagerApiAsyncTask(Context context, Bitmap bitmap, StringResultCallback stringResultCallback) {
        mContext = context;
        this.bitmap = bitmap;
        this.stringResultCallback = stringResultCallback;
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

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        MultipartBody.Builder req = new MultipartBody.Builder().setType(MultipartBody.FORM);
        req.addFormDataPart("image", "profile.png", RequestBody.create(MEDIA_TYPE_PNG, ToolUtils.getByteFromBitmap(bitmap)));

        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.update_user_image);
        builder.post(req.build());
        builder.header("Authorization", "Bearer " + ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.userToken, null));
        Request request = builder.build();
        Log.e("bodyToString", "" + ToolUtils.bodyToString(request));

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                //      Log.e("response", "" +response.body().string());
                jsonObject = new JSONObject(response.body().string());
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);
                    error = errorMsg.getString(error);
                } else {
                    data = jsonObject.getJSONObject(Constant.data);
                Log.e("jsonObject",""+data);
                }
                return 1;
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
            User user = new Gson().fromJson(ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.allUserData, null), User.class);
            try {
                user.setImg_url(data.getString("path"));
                Gson gson = new Gson();
                String data = gson.toJson(user);
                SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.userData);
                shared.putString(Constant.allUserData, data);
                shared.apply();
                ToolUtils.viewToast(mContext, mContext.getString(R.string.update_status));
                stringResultCallback.processFinish("", progress);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
