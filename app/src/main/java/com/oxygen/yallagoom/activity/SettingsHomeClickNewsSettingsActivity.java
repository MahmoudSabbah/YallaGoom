package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.Settings.RecycleViewSettingsNewsSettings;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.util.ArrayList;

public class SettingsHomeClickNewsSettingsActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView name_header;
    private TextView left_text;
    private TextView right_text;
    private RecyclerView channel_list;
    private TextView user_name;
    private CircularImageView user_image;
    private User user;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home_click_news_settings);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SettingsHomeClickNewsSettingsActivity.this);
        ToolUtils.setLightStatusBar(parent, SettingsHomeClickNewsSettingsActivity.this);
        imageLoader = ImageLoader.getInstance();

        name_header = (TextView) findViewById(R.id.name_header);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(getString(R.string.save));
        right_text.setVisibility(View.INVISIBLE);
        name_header.setText(getString(R.string.news_settings));
        user_name = (TextView) findViewById(R.id.user_name);
        user_image = (CircularImageView) findViewById(R.id.user_image);
        if (ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null)!=null){
            user = new Gson().fromJson(ToolUtils.getSharedPreferences(this, Constant.userData).getString(Constant.allUserData, null), User.class);
            ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), user_image, imageLoader);
            user_name.setText(user.getFirst_name()+" "+user.getLast_name());
        }else {
            user_name.setText(getString(R.string.not_registered));
        }

        channel_list = (RecyclerView) findViewById(R.id.channel_list);
        ArrayList<String> allChannels = ToolUtils.getArrayOfChannels(SettingsHomeClickNewsSettingsActivity.this);

        RecycleViewSettingsNewsSettings recycleViewSettingsNewsSettings = new RecycleViewSettingsNewsSettings(this,Constant.ChannelsList,allChannels);
        channel_list.setAdapter(recycleViewSettingsNewsSettings);

    }

    public void Back(View view) {
        finish();
    }
}
