package com.oxygen.yallagoom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewDiscoverCategory;
import com.oxygen.yallagoom.entity.TicketClasses.TicketFullData;
import com.oxygen.yallagoom.utils.ToolUtils;

public class DiscoverCategoryActivity extends AppCompatActivity {

    private LinearLayout parent;
    private TextView left_text;
    private TextView right_text;
    private RecyclerView list_category;
    private TicketFullData resultData;
    private TextView name_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_category);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.hideStatus(DiscoverCategoryActivity.this);
        ToolUtils.setLightStatusBar(parent, DiscoverCategoryActivity.this);
        name_header = (TextView) findViewById(R.id.name_header);
        right_text = (TextView) findViewById(R.id.right_text);
        left_text = (TextView) findViewById(R.id.left_text);
        left_text.setText(getString(R.string.back));
        right_text.setVisibility(View.GONE);
        list_category = (RecyclerView) findViewById(R.id.list_category);
        resultData = new Gson().fromJson(getIntent().getExtras().getString("resultData"), TicketFullData.class);
        name_header.setText(getIntent().getExtras().getString("CategoryName")+" "+getIntent().getExtras().getString("countryDataEN"));
        RecycleViewDiscoverCategory recycleViewDiscoverCategory = new RecycleViewDiscoverCategory(resultData.getData());
        list_category.setAdapter(recycleViewDiscoverCategory);
    }

    public void Back(View view) {
        finish();
    }
}
