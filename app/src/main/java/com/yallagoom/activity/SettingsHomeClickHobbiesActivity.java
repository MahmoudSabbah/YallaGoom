package com.yallagoom.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewSettingsHomeClickHobbies;
import com.yallagoom.api.GetUserSportsListAsyncTask;
import com.yallagoom.api.UpdatUserSportsAsyncTask;
import com.yallagoom.entity.SportObject;
import com.yallagoom.entity.User;
import com.yallagoom.interfaces.GetUserSportCallback;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.util.Map;

public class SettingsHomeClickHobbiesActivity extends AppCompatActivity {

    private LinearLayout parent;
    private ImageLoader imageLoader;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private TextView user_name;
    private CircularImageView user_image;
    private User user;
    private RecyclerView hobbies_list;
    private RecycleViewSettingsHomeClickHobbies recycleViewSettingsHomeClickHobbies;
    private SwipeRefreshLayout swipyrefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home_click_hobbies);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SettingsHomeClickHobbiesActivity.this);
        ToolUtils.setLightStatusBar(parent, SettingsHomeClickHobbiesActivity.this);
        imageLoader = ImageLoader.getInstance();

        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(getString(R.string.save));
        name_header.setText(getString(R.string.hobbies));
        user_name = (TextView) findViewById(R.id.user_name);
        user_image = (CircularImageView) findViewById(R.id.user_image);
        if (ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null) != null) {
            user = new Gson().fromJson(ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null), User.class);
            ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);
            user_name.setText(user.getFirst_name() + " " + user.getLast_name());
        } else {
        }
        swipyrefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyrefreshlayout.setRefreshing(true);
        hobbies_list = (RecyclerView) findViewById(R.id.hobbies_list);
        swipyrefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataSport();

            }
        });
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recycleViewSettingsHomeClickHobbies!=null){
                    if (recycleViewSettingsHomeClickHobbies.listUserSport.size()>0) {
                        UpdatUserSportsAsyncTask saveSportsAsyncTask = new UpdatUserSportsAsyncTask(SettingsHomeClickHobbiesActivity.this, recycleViewSettingsHomeClickHobbies.listUserSport, new StringResultCallback() {
                            @Override
                            public void processFinish(String result, KProgressHUD progress) {

                            }
                        });
                        saveSportsAsyncTask.execute();
                    }else {
                        ToolUtils.viewToast(SettingsHomeClickHobbiesActivity.this,getString(R.string.select_at_least_one_sport));
                    }
                }
            }
        });
        getDataSport();
    }

    public void Back(View view) {
        finish();
    }

    private void getDataSport() {
        GetUserSportsListAsyncTask getUserSportsListAsyncTask=new GetUserSportsListAsyncTask(SettingsHomeClickHobbiesActivity.this, new GetUserSportCallback() {
            @Override
            public void processFinish(SportObject[] allSport, Map<Integer, Integer> userSportId) {
                swipyrefreshlayout.setRefreshing(false);
                if (allSport != null) {
                    recycleViewSettingsHomeClickHobbies = new RecycleViewSettingsHomeClickHobbies(allSport,userSportId);
                    hobbies_list.setAdapter(recycleViewSettingsHomeClickHobbies);
                    swipyrefreshlayout.setEnabled(false);
                }
            }
        });

        getUserSportsListAsyncTask.execute();
    }
}
