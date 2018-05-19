package com.oxygen.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.HomeActivity;
import com.oxygen.yallagoom.activity.UpcomingEventListClickActivity;
import com.oxygen.yallagoom.api.event.GetAuthorizeEventsClickAsyncTask;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.entity.Notification;
import com.oxygen.yallagoom.interfaces.MyEventCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

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
        holder.content_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (notification[position].getType()){
                    case "friend_request":
                        Intent intentFriendReguest = new Intent(mContext, HomeActivity.class);
                        intentFriendReguest.putExtra("ActionNotification","friend_request");
                        intentFriendReguest.putExtra("active",true);
                        context.startActivity(intentFriendReguest);
                        break;
                    case "you_got_new_friend":
                        Intent  intentNewFriend = new Intent(mContext, HomeActivity.class);
                        intentNewFriend.putExtra("ActionNotification","you_got_new_friend");
                        intentNewFriend.putExtra("active",true);
                        context.startActivity(intentNewFriend);
                        break;
                    case "Event_invitation":
                        GetAuthorizeEventsClickAsyncTask getAuthorizeEventsClickAsyncTask = new GetAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                            @Override
                            public void processFinish(Event.DataEvent event) {
                                Intent intent = new Intent(context, UpcomingEventListClickActivity.class);
                                intent.putExtra("EventData", event);
                                context.startActivity(intent);
                            }
                        });
                        getAuthorizeEventsClickAsyncTask.execute(notification[position].getObj_id() + "");
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return notification.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView time;
        private final CircularImageView img;
        private final RelativeLayout content_lay;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            img = (CircularImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id._name);
            time = (TextView) itemView.findViewById(R.id.time);
            content_lay = (RelativeLayout) itemView.findViewById(R.id.content_lay);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}