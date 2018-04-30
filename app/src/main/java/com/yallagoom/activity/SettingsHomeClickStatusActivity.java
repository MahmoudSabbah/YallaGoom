package com.yallagoom.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.adapter.Settings.RecycleViewSettingsStatus;
import com.yallagoom.api.UpdateUserApiAsyncTask;
import com.yallagoom.entity.User;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingsHomeClickStatusActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private RecyclerView status_list;
    private RecycleViewSettingsStatus recycleViewSettingsStatus;
    private User user;
    private CircularImageView user_image;
    private ImageLoader imageLoader;
    private TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home_click_status);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SettingsHomeClickStatusActivity.this);
        ToolUtils.setLightStatusBar(parent, SettingsHomeClickStatusActivity.this);
        String[] data = {getString(R.string.online), getString(R.string.offline), getString(R.string.disturbed),
                getString(R.string.away), getString(R.string.in_meeting), getString(R.string.working)
        };
        user = new Gson().fromJson(ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null), User.class);
        imageLoader = ImageLoader.getInstance();
        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(getString(R.string.save));
        name_header.setText(getString(R.string.status));
        user_name = (TextView) findViewById(R.id.user_name);
        user_image = (CircularImageView) findViewById(R.id.user_image);
        ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);
        user_name.setText(user.getFirst_name()+" "+user.getLast_name());
        status_list = (RecyclerView) findViewById(R.id.status_list);
        recycleViewSettingsStatus = new RecycleViewSettingsStatus(this, data, user.getStatus());
        status_list.setAdapter(recycleViewSettingsStatus);

        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ArrayList<HashMap<String, String>> hashMapsData = new ArrayList<>();
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("status", recycleViewSettingsStatus.valueSelect);
             //   hashMapsData.add(stringStringHashMap);
                UpdateUserApiAsyncTask updateUserApiAsyncTask = new UpdateUserApiAsyncTask(SettingsHomeClickStatusActivity.this,
                        stringStringHashMap, new StringResultCallback() {
                    @Override
                    public void processFinish(String result, KProgressHUD progress) {
                        SharedPreferences.Editor shared = ToolUtils.setSharedPrefernce(SettingsHomeClickStatusActivity.this, Constant.userData);
                        shared.putString(Constant.allUserData, result);
                        shared.apply();
                        ToolUtils.viewToast(SettingsHomeClickStatusActivity.this, getString(R.string.update_status));
                    }
                });
                updateUserApiAsyncTask.execute();
            }
        });
    }

    public void Back(View view) {
        finish();
    }
}
