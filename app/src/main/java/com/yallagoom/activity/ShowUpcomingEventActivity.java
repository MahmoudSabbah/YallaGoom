package com.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewShawAllMyEvent;
import com.yallagoom.entity.Event;
import com.yallagoom.utils.ToolUtils;

public class ShowUpcomingEventActivity extends AppCompatActivity {
    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private RecyclerView all_my_event_list;
    private Event upcomingEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upcoming_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(ShowUpcomingEventActivity.this);
        ToolUtils.setLightStatusBar(parent, ShowUpcomingEventActivity.this);
        Bundle bundle=getIntent().getExtras();
        upcomingEventData= (Event) bundle.getSerializable("upcomingEventData");
        all_my_event_list=(RecyclerView)findViewById(R.id.all_my_event_list);
        left_text=(TextView)findViewById(R.id.left_text);
        right_text=(TextView)findViewById(R.id.right_text);
        header_title=(TextView)findViewById(R.id.name_header);
        header_title.setText(R.string.upcoming_text);
        right_text.setText(getString(R.string.cancel));
        RecycleViewShawAllMyEvent recycleViewShawAllMyEvent=new RecycleViewShawAllMyEvent(upcomingEventData.getData());
        all_my_event_list.setAdapter(recycleViewShawAllMyEvent);
    }

    public void Back(View view) {
        finish();
    }
}
