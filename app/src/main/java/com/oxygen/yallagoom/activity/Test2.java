package com.oxygen.yallagoom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.oxygen.yallagoom.R;

/**
 * Created by Mahmoud Sabbah on 2/15/2018.
 */

public class Test2 extends AppCompatActivity implements View.OnClickListener {

    int yPosition;
    private RecyclerView _list;
    private LinearLayout action_layout;
    private RelativeLayout playground;
    private RelativeLayout top_lay;
    private EditText height_edit;
    private Button add;
    private EditText x_pos;
    private EditText y_pos;
    private Button add_point;
    private LinearLayout parent;
    private LinearLayout points;
    private int height_value;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tes2t);
   /*     parent = (LinearLayout) findViewById(R.id.parent);
        points = (LinearLayout) findViewById(R.id.points);
        top_lay = (RelativeLayout) findViewById(R.id.top_lay);
        height_edit = (EditText) findViewById(R.id.height_edit);
        x_pos = (EditText) findViewById(R.id.x_pos);
        y_pos = (EditText) findViewById(R.id.y_pos);
        add = (Button) findViewById(R.id.add);
        add_point = (Button) findViewById(R.id.add_point);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!height_edit.getText().toString().equalsIgnoreCase("")) {
                    relativeLayout = new RelativeLayout(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Integer.parseInt(height_edit.getText().toString()));
                    relativeLayout.setLayoutParams(layoutParams);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green_light));
                    parent.addView(relativeLayout);
                    height_value = Integer.parseInt(height_edit.getText().toString());
                    top_lay.setVisibility(View.GONE);
                    points.setVisibility(View.VISIBLE);
                }
            }
        });
        add_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x_pos.getText().toString().equalsIgnoreCase("") || y_pos.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter y-pos and x-pos", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(y_pos.getText().toString()) > height_value) {
                    Toast.makeText(getApplicationContext(), "Please enter y-pos < from layout height", Toast.LENGTH_LONG).show();
                }else{
                    Log.e("x_pos",""+x_pos.getText().toString());
                    Log.e("y_pos",""+y_pos.getText().toString());
                    int wh = (int) getResources().getDimension(R.dimen._10sdp);
                    ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wh, wh);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_shap_select));
                    imageView.setX(Integer.parseInt(x_pos.getText().toString()));
                    imageView.setY(Integer.parseInt(y_pos.getText().toString()));
                    relativeLayout.addView(imageView);
                }
            }
        });

       *//* yPosition=(int) getResources().getDimension(R.dimen._10sdp);
        playground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                playground.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int value=playground.getHeight()/4;
                Log.e("playground",""+ playground.getHeight());
            }
        });*//*
    *//*  for (int i=0;i<4;i++){
          ImageView imageView = new ImageView(getApplicationContext());
          imageView.setX((int) getResources().getDimension(R.dimen._20sdp));
          imageView.setY(yPosition);
          yPosition=yPosition+(int) getResources().getDimension(R.dimen._10sdp);
          imageView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_shap_white));
          int wh = (int) getResources().getDimension(R.dimen._10sdp);
          LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wh, wh);
          imageView.setLayoutParams(layoutParams);
          playground.addView(imageView);
      }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }
}
