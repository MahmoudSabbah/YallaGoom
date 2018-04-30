package com.yallagoom.adapter.event.friends;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.api.AcceptFriendAsyncTask;
import com.yallagoom.api.RejectFriendAsyncTask;
import com.yallagoom.entity.MyFriends;
import com.yallagoom.interfaces.AcceptFriendCallback;
import com.yallagoom.interfaces.RejectFriendCallback;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewNewFriendsRequest extends RecyclerView.Adapter<RecycleViewNewFriendsRequest.MyViewHolder> implements SectionIndexer {

    private final ImageLoader imageLoader;
    private ArrayList<MyFriends.FriendsList> dataplayer;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = dataplayer.size(); i < size; i++) {
            String section = String.valueOf(dataplayer.get(i).getUser().getFirst_name().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int i) {
        return mSectionPositions.get(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    public void updateList(ArrayList<MyFriends.FriendsList> listData) {
        this.dataplayer = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_player;
        private final RelativeLayout content;
        private final TextView player_name;
        private final TextView location_name;
        private final TextView sport_type;
        private final ProgressBar progress_bar;
        private final TextView reject;
        private final TextView accept;
        private final SwipeRevealLayout paernt_swip;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_player = (CircularImageView) view.findViewById(R.id.image_player);
            content = (RelativeLayout) view.findViewById(R.id.content);
            player_name = (TextView) view.findViewById(R.id.player_name);
            location_name = (TextView) view.findViewById(R.id.location_name);
            sport_type = (TextView) view.findViewById(R.id.sport_type);
            reject = (TextView) view.findViewById(R.id.reject);
            accept = (TextView) view.findViewById(R.id.accept);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
            paernt_swip = (SwipeRevealLayout) view.findViewById(R.id.paernt_swip);

        }
    }


    public RecycleViewNewFriendsRequest(ArrayList<MyFriends.FriendsList> myFriends) {
        this.dataplayer = myFriends;
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friends_new_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.player_name.setText(dataplayer.get(position).getUser().getFirst_name() + " " + dataplayer.get(position).getUser().getLast_name());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.paernt_swip.isClosed()) {
                    holder.paernt_swip.open(true);
                }else {
                    holder.paernt_swip.close(true);

                }
            }
        });
        holder.progress_bar.setVisibility(View.GONE);
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectFriendAsyncTask rejectFriendAsyncTask = new RejectFriendAsyncTask(context, new RejectFriendCallback() {
                    @Override
                    public void processFinish() {
                        dataplayer.remove(position);
                        notifyDataSetChanged();
                        if (holder.paernt_swip.isOpened()) {
                            holder.paernt_swip.close(true);
                        }
                    }
                });
                rejectFriendAsyncTask.execute(dataplayer.get(position).getId() + "");
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptFriendAsyncTask acceptFriendAsyncTask = new AcceptFriendAsyncTask(context, new AcceptFriendCallback() {
                    @Override
                    public void processFinish() {
                        dataplayer.remove(position);
                        notifyDataSetChanged();
                        if (holder.paernt_swip.isOpened()) {
                            holder.paernt_swip.close(true);
                        }
                    }
                });
                acceptFriendAsyncTask.execute(dataplayer.get(position).getId() + "");
            }
        });

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (dataplayer == null) {
            return 0;
        }
        return dataplayer.size();
    }
}