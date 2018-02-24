package com.yallagoom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.action.RecyclerItemClickListener;
import com.yallagoom.adapter.RecycleViewFindPlayer;
import com.yallagoom.adapter.RecycleViewGenderLevel;
import com.yallagoom.api.MyPlayerAsyncTask;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.ClickPopUpCallback;
import com.yallagoom.interfaces.PlayerCallback;
import com.yallagoom.interfaces.PlayerListCallback;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.PhotoChoicePopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/15/2018.
 */

public class Test extends AppCompatActivity implements View.OnClickListener{

    private PhotoChoicePopup photoPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button button=(Button)findViewById(R.id.bttuon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 photoPopup = new PhotoChoicePopup(Test.this.getApplicationContext(), "", new ClickPopUpCallback() {
                     @Override
                     public void processFinish(int check) {
                         photoPopup.dismiss();
                     }
                 });
                photoPopup.showAtLocation(Test.this.findViewById(R.id.main_frame),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


        }
    }
}
