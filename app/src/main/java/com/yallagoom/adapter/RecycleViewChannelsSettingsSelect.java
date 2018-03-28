package com.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Matches.ClubsSettings;
import com.yallagoom.entity.Matches.CompetitionSettings;
import com.yallagoom.entity.News.Channels;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewChannelsSettingsSelect extends RecyclerView.Adapter<RecycleViewChannelsSettingsSelect.MyViewHolder> {


    private final Channels[] channelsList;
    private final ArrayList<String> allChannels;
    private Context context;

    public RecycleViewChannelsSettingsSelect(Channels[] channelsList, ArrayList<String> allChannels) {
        this.channelsList = channelsList;
        this.allChannels = allChannels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_channels_settings_select, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.value.setText(channelsList[position].getChannelName());
        if (allChannels.contains(channelsList[position].getId() + "")) {
            holder.checkbox_select.setChecked(true);
        } else {
            holder.checkbox_select.setChecked(false);

        }
        holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked){
                    if (!allChannels.contains(channelsList[position].getId() + "")){
                        allChannels.add(channelsList[position].getId() + "");
                    }
                }else {
                    allChannels.remove(channelsList[position].getId() + "");
                }
                ToolUtils.setChannels(context,allChannels);

            }
        });
    }

    @Override
    public int getItemCount() {
        return channelsList.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView value;
        private final RelativeLayout parent;
        private final SmoothCheckBox checkbox_select;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            value = (TextView) itemView.findViewById(R.id.value);
            checkbox_select = (SmoothCheckBox) itemView.findViewById(R.id.checkbox_select);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}