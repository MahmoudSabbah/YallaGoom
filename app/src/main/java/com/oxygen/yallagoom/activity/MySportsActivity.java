package com.oxygen.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.ramotion.foldingcell.FoldingCell;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewSport;
import com.oxygen.yallagoom.api.GetSportsAsyncTask;
import com.oxygen.yallagoom.api.SaveSportsAsyncTask;
import com.oxygen.yallagoom.entity.AllSport;
import com.oxygen.yallagoom.interfaces.GetSportCallback;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySportsActivity extends AppCompatActivity {

    private RecyclerView sport_list;
    private SmartRefreshLayout refreshLayout;
    private RecycleViewSport recycleViewSport;
    private FoldingCell fc;
    private List<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sports);
        ToolUtils.hideStatus(MySportsActivity.this);
        list = new ArrayList<>();
        sport_list = (RecyclerView) findViewById(R.id.sport_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        sport_list.setLayoutManager(mLayoutManager);
        sport_list.setItemAnimator(new DefaultItemAnimator());
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                GetSportsAsyncTask getSportsAsyncTask = new GetSportsAsyncTask(MySportsActivity.this, refreshLayout, new GetSportCallback() {
                    @Override
                    public void processFinish(AllSport sport) {
                        recycleViewSport = new RecycleViewSport(sport.getData());
                        sport_list.setAdapter(recycleViewSport);
                        refreshLayout.finishRefresh();
                    }
                });
                getSportsAsyncTask.execute();

            }
        });
        // attach click listener to folding cell

    }

    public void Skip(View view) {
        Intent intent = new Intent(MySportsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void Save(View view) {
        SaveSportsAsyncTask saveSportsAsyncTask = new SaveSportsAsyncTask(MySportsActivity.this, recycleViewSport.getList(), new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                Intent intent = new Intent(MySportsActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        saveSportsAsyncTask.execute();
      /*  for (int i=0;i<recycleViewSport.getList().size();i++){
            Log.e("recycleViewSport",""+recycleViewSport.getList().get(i).getSport_id());

        }*/
    /*    Intent intent = new Intent(MySportsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();*/
    }
}
