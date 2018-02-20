package com.yallagoom.fragment.eventTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;


public class ChatFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private boolean auto =true;
    private RecyclerView alert_list;

    private int page=1;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;

    }


}
