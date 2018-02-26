package com.yallagoom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.activity.LoginActivity;

/**
 * Created by Mahmoud Sabbah on 2/1/2018.
 */

public class GiftsFragment extends Fragment {


    private TextView login_bt;
    private TextView header_title;

    public GiftsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gifts, container, false);
        header_title=(TextView)getActivity().findViewById(R.id.header_title);
        header_title.setText(getString(R.string.gifts));

        login_bt=(TextView)view.findViewById(R.id.login_bt);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GiftsFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;

    }


}

