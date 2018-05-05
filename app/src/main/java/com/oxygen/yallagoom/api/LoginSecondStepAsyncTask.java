package com.oxygen.yallagoom.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.FirebaseUtils;
import com.oxygen.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class LoginSecondStepAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private KProgressHUD progress;
    private String error;

    public LoginSecondStepAsyncTask(Context context) {
        mContext = context;
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
        RequestBody formBody = new FormBody.Builder()
                .add("email_or_mobile", params[0])
                .add("password", params[1])
                .build();
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.urlData + Constant.login);
        builder.post(formBody);
        builder.header("Authorization", Constant.API_KEY);
        Request request = builder.build();

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("jsonObject", "" + jsonObject);
                int status = jsonObject.getInt(Constant.status_callback);
                if (status == 0) {
                    JSONObject errorMsg = jsonObject.getJSONObject(Constant.error_callback);
                    error = errorMsg.names().getString(0);

                  /*  error = errorMsg.getString(Constant.error_callback);*/
                } else {
                    //    JSONObject data = jsonObject.getJSONObject(Constant.data);
                    //  user = new Gson().fromJson(data.toString(), User.class);
                    //   Constant.user = user;
                    JSONObject data = jsonObject.getJSONObject(Constant.data);

                    SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(mContext, Constant.userData);
                    shared.putString(Constant.userToken, data.getString("token"));
                    shared.putString(Constant.allUserData, data.toString());
                    shared.putInt(Constant.userId, data.getInt("id"));
                    shared.putString(Constant.password, params[1]);
                    shared.apply();
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
            RegisterUserTokenToFirebaseAsyncTask registerUserTokenToFirebaseAsyncTask = new
                    RegisterUserTokenToFirebaseAsyncTask(mContext, new StringResultCallback() {
                @Override
                public void processFinish(String result, KProgressHUD progress) {
                    User user = new Gson().fromJson(ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.allUserData, null), User.class);
                    if (user.getFirebase_auth_user_id() != null) {
                        FirebaseUtils.loginUserToFirebase(mContext, user.getEmail(), ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.password, "")
                                , progress);
                    } else {
                        FirebaseUtils.CreateFirebaseUser(user.getEmail(), ToolUtils.getSharedPreferences(mContext, Constant.userData).getString(Constant.password, ""),
                                user.getFirst_name()+" "+user.getLast_name(),Constant.imageUrl+user.getImg_url(),(Activity)mContext,progress,0) ;
                    }

                }
            });
            registerUserTokenToFirebaseAsyncTask.execute();

            //
        } else {
            ToolUtils.viewToast(mContext, error);
        }
    }
}
