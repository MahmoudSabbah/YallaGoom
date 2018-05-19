package com.oxygen.yallagoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewShawAllMyEvent;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.utils.ToolUtils;

public class ShowAllMyEventActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private TextView header_title;
    private RecyclerView all_my_event_list;
    private Event myEventData;
    private RecycleViewShawAllMyEvent recycleViewShawAllMyEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_my_event);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(ShowAllMyEventActivity.this);
        ToolUtils.setLightStatusBar(parent, ShowAllMyEventActivity.this);
        Bundle bundle = getIntent().getExtras();
        myEventData = (Event) bundle.getSerializable("myEventData");
        all_my_event_list = (RecyclerView) findViewById(R.id.all_my_event_list);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);
        header_title = (TextView) findViewById(R.id.name_header);
        header_title.setText(getString(R.string.my_event));
        right_text.setText(getString(R.string.cancel));
        recycleViewShawAllMyEvent = new RecycleViewShawAllMyEvent(myEventData.getData());
        all_my_event_list.setAdapter(recycleViewShawAllMyEvent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 206:
                if (resultCode == Activity.RESULT_OK && recycleViewShawAllMyEvent.lastPosition!=-1) {
                    myEventData.getData().remove(recycleViewShawAllMyEvent.lastPosition);
                    recycleViewShawAllMyEvent.notifyDataSetChanged();
                    Intent intent =new Intent();
                    intent.putExtra("deletePosition",recycleViewShawAllMyEvent.lastPosition);
                    setResult(Activity.RESULT_OK,intent);
                }
                break;
        }
    }
}
