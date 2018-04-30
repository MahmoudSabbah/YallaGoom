package com.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewNotification;
import com.yallagoom.api.GetNotificationAsyncTask;
import com.yallagoom.entity.Notification;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.ToolUtils;

public class NotificationActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView notification_list;
    private TextView close_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ToolUtils.hideStatus(NotificationActivity.this);

        refreshLayout =(SmartRefreshLayout)findViewById(R.id.refreshLayout);
        notification_list =(RecyclerView)findViewById(R.id.notification_list);
        close_activity =(TextView)findViewById(R.id.close_activity);
        close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ToolUtils.checkActivityLAst(NotificationActivity.this)) {
                    Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
        refreshLayout.autoRefresh();
        getNotificationData();
    }
    private void getNotificationData(){
        GetNotificationAsyncTask getNotificationAsyncTask=new GetNotificationAsyncTask(NotificationActivity.this, new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                Notification[] notification=new Gson().fromJson(result,Notification[].class);
                Log.e("result",""+result);
                RecycleViewNotification recycleViewNotification=new RecycleViewNotification(NotificationActivity.this,notification);
                notification_list.setAdapter(recycleViewNotification);
                refreshLayout.finishRefresh();
            }
        });
        getNotificationAsyncTask.execute();
    }
}
