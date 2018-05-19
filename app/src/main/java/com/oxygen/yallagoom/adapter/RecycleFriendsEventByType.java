package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.UpcomingEventListClickActivity;
import com.oxygen.yallagoom.api.event.GetAuthorizeEventsClickAsyncTask;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.entity.event.FriendsEvents;
import com.oxygen.yallagoom.interfaces.MyEventCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.List;

public class RecycleFriendsEventByType extends RecyclerView.Adapter<RecycleFriendsEventByType.MyViewHolder> {

    private final List<FriendsEvents.Data> data;
    public Context context;
    private ImageLoader imageLoader;

    public RecycleFriendsEventByType(List<FriendsEvents.Data> data, ImageLoader imageLoader) {
        this.data = data;
        this.imageLoader = imageLoader;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_event_main, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.event_name.setText(data.get(position).getEventTitle());
        ToolUtils.setImage(Constant.imageUrl + data.get(position).getEventImage(), holder.my_event_image, imageLoader);
        holder.parent_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAuthorizeEventsClickAsyncTask getAuthorizeEventsClickAsyncTask = new GetAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                    @Override
                    public void processFinish(Event.DataEvent event) {
                        Intent intent = new Intent(context, UpcomingEventListClickActivity.class);
                        intent.putExtra("EventData", event);
                        context.startActivity(intent);
                    }
                });
                //  if (data.get(position).getPrivateOrPublic().equalsIgnoreCase("public")){
                getAuthorizeEventsClickAsyncTask.execute(data.get(position).getId() + "");//,"public_event"

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final SelectableRoundedImageView my_event_image;
        private final TextView event_name;
        private final TextView year;
        private final RelativeLayout parent_raw;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            my_event_image = (SelectableRoundedImageView) itemView.findViewById(R.id.my_event_image);
            event_name = (TextView) itemView.findViewById(R.id.event_name);
            year = (TextView) itemView.findViewById(R.id.year);
            parent_raw = (RelativeLayout) itemView.findViewById(R.id.parent_raw);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}