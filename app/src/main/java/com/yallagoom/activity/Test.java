package com.yallagoom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/15/2018.
 */

public class Test extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        RecordView recordView = (RecordView) findViewById(R.id.record_view);
        RecordButton recordButton = (RecordButton) findViewById(R.id.record_button);

        //IMPORTANT
        recordButton.setRecordView(recordView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }
}
