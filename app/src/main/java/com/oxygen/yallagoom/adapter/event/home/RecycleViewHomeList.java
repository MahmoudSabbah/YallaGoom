package com.oxygen.yallagoom.adapter.event.home;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.UpcomingEventListClickActivity;
import com.oxygen.yallagoom.api.event.GetAuthorizeEventsClickAsyncTask;
import com.oxygen.yallagoom.api.event.GetUnAuthorizeEventsClickAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.interfaces.MyEventCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

public class RecycleViewHomeList extends RecyclerView.Adapter<RecycleViewHomeList.MyViewHolder> {

    private final ImageLoader imageLoader;
    private ArrayList<Event.DataEvent> nearEvent;
    public Context context;
    // private List<Driver> drivers;
    public int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView cat_type;
        private final TextView title;
        private final TextView start_date;
        private final TextView cost;
        private final ImageView event_image;
        private final RelativeLayout parent_;


        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            cat_type = (TextView) view.findViewById(R.id.cat_type);
            title = (TextView) view.findViewById(R.id.title);
            start_date = (TextView) view.findViewById(R.id.start_date);
            cost = (TextView) view.findViewById(R.id.cost);
            event_image = (ImageView) view.findViewById(R.id.event_image);
            parent_ = (RelativeLayout) view.findViewById(R.id.parent_);


        }
    }


    public RecycleViewHomeList(ArrayList<Event.DataEvent> nearEvent) {
        this.nearEvent = nearEvent;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_home_fragment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.cat_type.setText("#" + nearEvent.get(position).getCategory().getCategoryName());
        holder.title.setText(nearEvent.get(position).getEventTitle());
        holder.start_date.setText(nearEvent.get(position).getStartEventDate() + " " + nearEvent.get(position).getStartEventTime());
        Log.e("getCost",""+nearEvent.get(position).getCost());

        if (nearEvent.get(position).getCost() == null) {
            holder.cost.setText(context.getString(R.string.free));

        } else if (Double.parseDouble(nearEvent.get(position).getCost()) == 0) {
            holder.cost.setText(context.getString(R.string.free));
        } else {
            holder.cost.setText("$" + nearEvent.get(position).getCost());
        }
        if (nearEvent.get(position).getEventImage()!=null){
            ToolUtils.setImage(Constant.imageUrl +nearEvent.get(position).getEventImage(),holder.event_image,imageLoader);
          //  imageLoader.displayImage(Constant.imageUrl +nearEvent.get(position).getEventImage(), holder.event_image);
        }
        holder.parent_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainApplication.verification_check){
                    GetAuthorizeEventsClickAsyncTask getAuthorizeEventsClickAsyncTask =new GetAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                        @Override
                        public void processFinish(Event.DataEvent event) {
                            Intent intent = new Intent(context, UpcomingEventListClickActivity.class);
                            intent.putExtra("EventData", event);
                            if (nearEvent.get(position).getInvited_lis() != null && nearEvent.get(position).getInvited_lis().size() > 0) {
                                intent.putExtra("Invited_list", nearEvent.get(position).getInvited_lis());
                            }
                            context.startActivity(intent);
                        }
                    });
                    getAuthorizeEventsClickAsyncTask.execute(nearEvent.get(position).getId()+"");//,"event_i_invited_to_it"

                }else {
                    GetUnAuthorizeEventsClickAsyncTask getUnAuthorizeEventsClickAsyncTask =new GetUnAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                        @Override
                        public void processFinish(Event.DataEvent event) {
                            Intent intent = new Intent(context, UpcomingEventListClickActivity.class);
                            intent.putExtra("EventData", event);
                            if (nearEvent.get(position).getInvited_lis() != null && nearEvent.get(position).getInvited_lis().size() > 0) {
                                intent.putExtra("Invited_list", nearEvent.get(position).getInvited_lis());
                            }
                            context.startActivity(intent);
                        }
                    });
                    getUnAuthorizeEventsClickAsyncTask.execute(nearEvent.get(position).getId()+"");
                }
            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return nearEvent.size();
    }
}