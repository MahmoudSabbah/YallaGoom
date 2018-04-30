package com.yallagoom.adapter.event.myEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yallagoom.R;
import com.yallagoom.activity.MyEventListClickActivity;
import com.yallagoom.api.event.GetAuthorizeEventsClickAsyncTask;
import com.yallagoom.entity.Event;
import com.yallagoom.interfaces.MyEventCallback;
import com.yallagoom.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewMainMyEvent extends RecyclerView.Adapter<RecycleViewMainMyEvent.MyViewHolder> {

    private final ArrayList<Event.DataEvent> myEvent;
    private  ImageLoader imageLoader;
    public Context context;
    public int LastClickPosition;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final SelectableRoundedImageView my_event_image;
        private final TextView event_name;
        private final RelativeLayout parent_raw;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            my_event_image = (SelectableRoundedImageView) view.findViewById(R.id.my_event_image);
            event_name = (TextView) view.findViewById(R.id.event_name);
            parent_raw = (RelativeLayout) view.findViewById(R.id.parent_raw);

        }
    }


    public RecycleViewMainMyEvent(Event myEvent) {
        this.myEvent=myEvent.getData();
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public RecycleViewMainMyEvent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_event_main, parent, false);

        return new RecycleViewMainMyEvent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewMainMyEvent.MyViewHolder holder, final int position) {
        holder.parent_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAuthorizeEventsClickAsyncTask getAuthorizeEventsClickAsyncTask =new GetAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                    @Override
                    public void processFinish(Event.DataEvent event) {
                        LastClickPosition=position;
                        Intent intent = new Intent(context, MyEventListClickActivity.class);
                        intent.putExtra("event_data",event);
                        if (myEvent.get(position).getInvited_lis() != null && myEvent.get(position).getInvited_lis().size() > 0) {
                            intent.putExtra("Invited_list", myEvent.get(position).getInvited_lis());
                        }
                        ((Activity)context).startActivityForResult(intent,206);
                    }
                });
                getAuthorizeEventsClickAsyncTask.execute(myEvent.get(position).getId()+"");//,"my_event"


            }
        });
        holder.event_name.setText(myEvent.get(position).getEventTitle());
        if (myEvent.get(position).getEventImage()!=null){
            Log.e("getEventImage","'"+myEvent.get(position).getEventImage());
            imageLoader.loadImage(Constant.imageUrl+""+myEvent.get(position).getEventImage(), new SimpleImageLoadingListener() {
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
        return myEvent.size();
    }
}
