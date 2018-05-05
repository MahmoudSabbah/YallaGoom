package com.oxygen.yallagoom.adapter.Settings;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.News.Channels;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;

public class RecycleViewSettingsNewsSettings extends RecyclerView.Adapter<RecycleViewSettingsNewsSettings.MyViewHolder> {


    private final Activity mContext;
    private final Channels[] channelsList;
    private final ArrayList<String> allChannels;
    public Context context;

    public RecycleViewSettingsNewsSettings(Activity activity, Channels[] channelsList, ArrayList<String> allChannels) {
        this.mContext = activity;
        this.channelsList = channelsList;
        this.allChannels = allChannels;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_settings_news_settings, parent, false);
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
        private final SmoothCheckBox checkbox_select;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            value = (TextView) view.findViewById(R.id.value);
            checkbox_select = (SmoothCheckBox) view.findViewById(R.id.checkbox_select);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}