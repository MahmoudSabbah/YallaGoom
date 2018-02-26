package com.yallagoom.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yallagoom.R;
import com.yallagoom.entity.FriendsEvents;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

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
        ToolUtils.setImage(Constant.imageUrl + data.get(position).getEventImage(),holder.my_event_image,imageLoader);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final SelectableRoundedImageView my_event_image;
        private final TextView event_name;
        private final TextView year;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            my_event_image = (SelectableRoundedImageView) itemView.findViewById(R.id.my_event_image);
            event_name = (TextView) itemView.findViewById(R.id.event_name);
            year = (TextView) itemView.findViewById(R.id.year);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}