package com.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.activity.ClubsDetailsActivity;
import com.yallagoom.api.GetClubAndTeamsDetailsApiAsyncTask;
import com.yallagoom.entity.Matches.ClubsAndTeams;
import com.yallagoom.entity.Notification;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewNotification extends RecyclerView.Adapter<RecycleViewNotification.MyViewHolder> {


    private final Activity mContext;
    private final Notification[] notification;
    private final ImageLoader imageLoader;
    public Context context;

    public RecycleViewNotification(Activity activity, Notification[] notification) {
        this.mContext = activity;
        this.notification = notification;
        imageLoader = ImageLoader.getInstance();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(notification[position].getBody());
        holder.time.setText(ToolUtils.getTimeSince(ToolUtils.converStringToDate(notification[position].getCreated_at(), Constant.yyyy_MM_dd__HH_mm_ss)));
        if (notification[position].getNotify_user_id() == notification[position].getDb_reciever().getId()) {
            if (notification[position].getDb_sender() != null && notification[position].getDb_sender().getImg_url() != null) {
                ToolUtils.setImage(notification[position].getDb_sender().getImg_url(), holder.img, imageLoader);
            }
        } else {
            if (notification[position].getDb_reciever() != null && notification[position].getDb_reciever().getImg_url() != null) {
                ToolUtils.setImage(notification[position].getDb_reciever().getImg_url(), holder.img, imageLoader);
            }
        }

    }

    @Override
    public int getItemCount() {
        return notification.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView time;
        private final CircularImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            img = (CircularImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id._name);
            time = (TextView) itemView.findViewById(R.id.time);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}