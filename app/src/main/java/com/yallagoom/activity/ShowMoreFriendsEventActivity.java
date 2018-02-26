package com.yallagoom.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewShawAllMyEvent;
import com.yallagoom.adapter.RecycleViewShawMoreFriendEvent;
import com.yallagoom.entity.Event;
import com.yallagoom.entity.FriendsEvents;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class ShowMoreFriendsEventActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private RecyclerView all_my_event_list;
    private ArrayList<FriendsEvents.Data> eventData;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_my_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(ShowMoreFriendsEventActivity.this);
        ToolUtils.setLightStatusBar(parent, ShowMoreFriendsEventActivity.this);
        Bundle bundle = getIntent().getExtras();
        eventData = (ArrayList<FriendsEvents.Data>) bundle.getSerializable("FriendEventData");
        title=bundle.getString("title");
        all_my_event_list = (RecyclerView) findViewById(R.id.all_my_event_list);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(title);
        right_text.setText(getString(R.string.cancel));
        left_text.setText(getString(R.string.friend_event));
        RecycleViewShawMoreFriendEvent recycleViewShawMoreFriendEvent = new RecycleViewShawMoreFriendEvent(eventData);
        all_my_event_list.setAdapter(recycleViewShawMoreFriendEvent);
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Back(View view) {
        finish();
    }
}
