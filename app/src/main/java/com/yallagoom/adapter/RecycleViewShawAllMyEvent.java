package com.yallagoom.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yallagoom.R;
import com.yallagoom.entity.Event;
import com.yallagoom.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewShawAllMyEvent extends RecyclerView.Adapter<RecycleViewShawAllMyEvent.MyViewHolder> {

    private final ArrayList<Event.DataEvent> data;
    private final ImageLoader imageLoader;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView title;
        private final TextView type_cost_free;
        private final TextView category;
        private final RoundedImageView my_event_image;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            type_cost_free = (TextView) view.findViewById(R.id.type_cost_free);
            category = (TextView) view.findViewById(R.id.type);
            my_event_image = (RoundedImageView) view.findViewById(R.id.my_event_image);


        }
    }


    public RecycleViewShawAllMyEvent(ArrayList<Event.DataEvent> data) {
        this.data = data;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public RecycleViewShawAllMyEvent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_show_all_my_event, parent, false);

        return new RecycleViewShawAllMyEvent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewShawAllMyEvent.MyViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getEventTitle());
        if (data.get(position).getIsFree().equalsIgnoreCase("0")){
            holder.type_cost_free.setText(context.getString(R.string.free));
        }else {
            holder.type_cost_free.setText(data.get(position).getCost()+"$");
        }
        holder.category.setText(data.get(position).getCategory().getCategoryName());
        if (data.get(position).getEventImage()!=null){
            imageLoader.loadImage(Constant.imageUrl+""+data.get(position).getEventImage(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.my_event_image.setImageBitmap(loadedImage);
                }
            });
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return data.size();
    }
}
