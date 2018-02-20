package com.yallagoom.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.entity.Country;
import com.yallagoom.entity.PlaceJSONParser;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.interfaces.GetPlaceCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class GetLocationNameAsyncTask extends AsyncTask<String, String, Integer> {
    private final Context mContext;
    private final GetPlaceCallback getPlaceCallback;
    private KProgressHUD progress;
    private String error;
    private Country country;
    private List<HashMap<String, String>> data_place;

    public GetLocationNameAsyncTask(Context context, GetPlaceCallback getPlaceCallback) {
        mContext = context;
         this.getPlaceCallback = getPlaceCallback;
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

        Request.Builder builder = new Request.Builder();
        builder.url(ToolUtils.searchresult(params[0], Double.parseDouble(params[1]), Double.parseDouble(params[2]), mContext));
        builder.get();
        builder.header("Authorization", Constant.API_KEY);
        Request request = builder.build();

        try {
            Response response = ToolUtils.getOkHttpClient().newCall(request).execute();
            Log.e("urlData", ToolUtils.searchresult(params[0], Double.parseDouble(params[1]), Double.parseDouble(params[2]), mContext));
            if (response.code() != 500) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                Log.e("jsonObjectData", "" + jsonObject);
                String status = jsonObject.getString(Constant.status_callback);
                if (status.equalsIgnoreCase("OK")) {
                    PlaceJSONParser placeJsonParser = new PlaceJSONParser();
                    data_place = placeJsonParser.parse(jsonObject);
                    return 1;
                }


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
            getPlaceCallback.processFinish(data_place);
        } else {
        //    ToolUtils.viewToast(mContext, error);
        }
    }
}
