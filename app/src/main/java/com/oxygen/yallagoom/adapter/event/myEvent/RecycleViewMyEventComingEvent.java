package com.oxygen.yallagoom.adapter.event.myEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.UpcomingEventListClickActivity;
import com.oxygen.yallagoom.api.event.GetAuthorizeEventsClickAsyncTask;
import com.oxygen.yallagoom.entity.event.Event;
import com.oxygen.yallagoom.interfaces.MyEventCallback;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewMyEventComingEvent extends RecyclerView.Adapter<RecycleViewMyEventComingEvent.MyViewHolder> {

    private ArrayList<Event.DataEvent> upcomingEvent;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout parent_view;
        private final TextView title_event;
        private final TextView description;
        private final TextView time;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            parent_view = (RelativeLayout) view.findViewById(R.id.parent_view);
            title_event = (TextView) view.findViewById(R.id.title_event);
            description = (TextView) view.findViewById(R.id.description);
            time = (TextView) view.findViewById(R.id.time);

        }
    }

    public RecycleViewMyEventComingEvent(Event upcomingEvent) {
        this.upcomingEvent = upcomingEvent.getData();
    }

    @Override
    public RecycleViewMyEventComingEvent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_coming_event, parent, false);

        return new RecycleViewMyEventComingEvent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewMyEventComingEvent.MyViewHolder holder, final int position) {
        holder.parent_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAuthorizeEventsClickAsyncTask getAuthorizeEventsClickAsyncTask = new GetAuthorizeEventsClickAsyncTask(context, new MyEventCallback() {
                    @Override
                    public void processFinish(Event.DataEvent event) {
                        Intent intent = new Intent(context, UpcomingEventListClickActivity.class);
                        intent.putExtra("EventData", event);
                        if (upcomingEvent.get(position).getInvited_lis() != null && upcomingEvent.get(position).getInvited_lis().size() > 0) {
                            intent.putExtra("Invited_list", upcomingEvent.get(position).getInvited_lis());
                        }
                        context.startActivity(intent);
                    }
                });
                getAuthorizeEventsClickAsyncTask.execute(upcomingEvent.get(position).getId() + "");//,"event_i_invited_to_it"


            }
        });
        holder.title_event.setText(upcomingEvent.get(position).getEventTitle());
        holder.description.setText(upcomingEvent.get(position).getEventDescription());
        holder.time.setText(upcomingEvent.get(position).getStartEventTime() + " - " + upcomingEvent.get(position).getEndEventTime());
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return upcomingEvent.size();
    }
}
