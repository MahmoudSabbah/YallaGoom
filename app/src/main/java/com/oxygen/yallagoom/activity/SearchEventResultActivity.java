package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.event.home.RecycleViewHomeList;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.utils.ToolUtils;

public class SearchEventResultActivity extends AppCompatActivity {

    private RecyclerView event_list;
    private RecycleViewHomeList recycleViewHome;
    private LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_result);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(SearchEventResultActivity.this);
        ToolUtils.setLightStatusBar(parent, SearchEventResultActivity.this);

        event_list = (RecyclerView) findViewById(R.id.event_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        event_list.setLayoutManager(mLayoutManager);
        event_list.setItemAnimator(new DefaultItemAnimator());
        Event nearEvent = (Event) getIntent().getSerializableExtra("data");

        recycleViewHome = new RecycleViewHomeList(nearEvent.getData());
        event_list.setAdapter(recycleViewHome);
    }

    public void Back(View view) {
        finish();
    }
}
