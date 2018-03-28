package com.yallagoom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/15/2018.
 */

public class Test extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView _list;
    private LinearLayout action_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        // _list=(RecyclerView)findViewById(R.id._list);
        action_layout = (LinearLayout) findViewById(R.id.action_layout);
      /*  RecycleViewTest recycleViewTest =new RecycleViewTest();
        _list.setAdapter(recycleViewTest);*/
        for (int i = 0; i < 10; i++) {

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View inflatedLayout = inflater.inflate(R.layout.list_item_incidents, null, false);
            RelativeLayout mes_left = (RelativeLayout) inflatedLayout.findViewById(R.id.mes_left);
            RelativeLayout view2_lay = (RelativeLayout) inflatedLayout.findViewById(R.id.view2_lay);
            RelativeLayout view1_lay = (RelativeLayout) inflatedLayout.findViewById(R.id.view1_lay);
            RelativeLayout small_circle = (RelativeLayout) inflatedLayout.findViewById(R.id.small_circle);
            RelativeLayout text_half = (RelativeLayout) inflatedLayout.findViewById(R.id.text_half);
            LinearLayout mes_right = (LinearLayout) inflatedLayout.findViewById(R.id.mes_right);
            if (i == 9) {
                view2_lay.setVisibility(View.VISIBLE);
            } else {
                view2_lay.setVisibility(View.GONE);

            }
            if (i == 5) {
                view2_lay.setVisibility(View.VISIBLE);
                text_half.setVisibility(View.VISIBLE);
            }
            if (i % 2 == 0) {
                mes_left.setVisibility(View.VISIBLE);
                mes_right.setVisibility(View.GONE);
            } else {
                mes_left.setVisibility(View.GONE);
                mes_right.setVisibility(View.VISIBLE);
            }
            if (i == 0 || i == 6) {
                small_circle.setVisibility(View.VISIBLE);
            } else {
                small_circle.setVisibility(View.GONE);
            }
            action_layout.addView(inflatedLayout);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }
}
