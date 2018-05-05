package com.oxygen.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewChannelsSettingsSelect;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;


public class ChannelSettingsFragment extends Fragment {
    private RecyclerView channels_list;

    public ChannelSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channels_settings, container, false);
        channels_list = (RecyclerView) view.findViewById(R.id.channels_list);


        getData();
        return view;

    }

    private void getData() {
        ArrayList<String> allChannels = ToolUtils.getArrayOfChannels(ChannelSettingsFragment.this.getActivity());

        RecycleViewChannelsSettingsSelect recycleViewChannelsSettingsSelect = new RecycleViewChannelsSettingsSelect(Constant.ChannelsList,allChannels);
        channels_list.setAdapter(recycleViewChannelsSettingsSelect);
    }


}
