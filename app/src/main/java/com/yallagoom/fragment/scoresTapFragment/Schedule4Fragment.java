package com.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yallagoom.R;


public class Schedule4Fragment extends Fragment {
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


    public Schedule4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tes2t, container, false);
        parent = (LinearLayout) view.findViewById(R.id.parent);
        points = (LinearLayout) view.findViewById(R.id.points);
        top_lay = (RelativeLayout) view.findViewById(R.id.top_lay);
        height_edit = (EditText) view.findViewById(R.id.height_edit);
        x_pos = (EditText) view.findViewById(R.id.x_pos);
        y_pos = (EditText) view.findViewById(R.id.y_pos);
        add = (Button) view.findViewById(R.id.add);
        add_point = (Button) view.findViewById(R.id.add_point);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!height_edit.getText().toString().equalsIgnoreCase("")) {
                    relativeLayout = new RelativeLayout(getActivity());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Integer.parseInt(height_edit.getText().toString()));
                    relativeLayout.setLayoutParams(layoutParams);
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green_light));
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
                    Toast.makeText(getActivity(), "Please enter y-pos and x-pos", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(y_pos.getText().toString()) > height_value) {
                    Toast.makeText(getActivity(), "Please enter y-pos < from layout height", Toast.LENGTH_LONG).show();
                }else{
                    Log.e("x_pos",""+x_pos.getText().toString());
                    Log.e("y_pos",""+y_pos.getText().toString());
                    int wh = (int) getResources().getDimension(R.dimen._10sdp);
                    ImageView imageView = new ImageView(getActivity());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wh, wh);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.circle_shap_select));
                    imageView.setX(Integer.parseInt(x_pos.getText().toString()));
                    imageView.setY(Integer.parseInt(y_pos.getText().toString()));
                    relativeLayout.addView(imageView);
                }
            }
        });
        return view;

    }


}
