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
import com.yallagoom.api.BlockFriendAsyncTask;
import com.yallagoom.api.DeleteFriendAsyncTask;
import com.yallagoom.entity.MyFriendList;
import com.yallagoom.entity.User;
import com.yallagoom.interfaces.BlockFriendCallback;
import com.yallagoom.interfaces.DeleteFriendCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewMyFriendsList extends RecyclerView.Adapter<RecycleViewMyFriendsList.MyViewHolder> implements SectionIndexer {

    private final ImageLoader imageLoader;
    private final int userId;
    private List<User> dataplayer;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = dataplayer.size(); i < size; i++) {
            String section = String.valueOf(dataplayer.get(i).getFirst_name().charAt(0)).toUpperCase();
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

    public void updateList(ArrayList<User> listData) {
        this.dataplayer = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_player;
        private final RelativeLayout container;
        private final TextView player_name;
        private final TextView location_name;
        private final TextView sport_type;
        private final ProgressBar progress_bar;
        private final TextView unfriend;
        private final TextView block;
        private final SwipeRevealLayout paernt_swip;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_player = (CircularImageView) view.findViewById(R.id.image_player);
            container = (RelativeLayout) view.findViewById(R.id.container);
            player_name = (TextView) view.findViewById(R.id.player_name);
            location_name = (TextView) view.findViewById(R.id.location_name);
            sport_type = (TextView) view.findViewById(R.id.sport_type);
            unfriend = (TextView) view.findViewById(R.id.unfriend);
            block = (TextView) view.findViewById(R.id.block);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
            paernt_swip = (SwipeRevealLayout) view.findViewById(R.id.paernt_swip);


        }
    }


    public RecycleViewMyFriendsList(List<User> myFriends, Context mContext) {
        this.dataplayer = myFriends;
        imageLoader = ImageLoader.getInstance();
        userId = ToolUtils.getSharedPreferences(mContext, Constant.userData).getInt(Constant.userId, -1);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friends_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.player_name.setText(dataplayer.get(position).getFirst_name() + " " + dataplayer.get(position).getLast_name());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.paernt_swip.isClosed()) {
                    holder.paernt_swip.open(true);
                } else {
                    holder.paernt_swip.close(true);

                }
            }
        });
        holder.progress_bar.setVisibility(View.GONE);
        try {
            if (dataplayer.get(position).getImg_url() != null) {
                ToolUtils.setImageSmall_50(Constant.imageUrl + dataplayer.get(position).getImg_url(), holder.image_player, imageLoader);
            }
        } catch (NullPointerException e) {

        }
        holder.unfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFriendAsyncTask deleteFriendAsyncTask = new DeleteFriendAsyncTask(context, new DeleteFriendCallback() {
                    @Override
                    public void processFinish() {
                        dataplayer.remove(position);
                        notifyDataSetChanged();
                        if (holder.paernt_swip.isOpened()) {
                            holder.paernt_swip.close(true);
                        }
                    }
                });
                deleteFriendAsyncTask.execute(dataplayer.get(position).getId() + "");
            }
        });
        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlockFriendAsyncTask blockFriendAsyncTask = new BlockFriendAsyncTask(context, new BlockFriendCallback() {
                    @Override
                    public void processFinish() {
                        dataplayer.remove(position);
                        notifyDataSetChanged();
                        if (holder.paernt_swip.isOpened()) {
                            holder.paernt_swip.close(true);
                        }
                    }
                });
                blockFriendAsyncTask.execute(dataplayer.get(position).getId() + "");
            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return dataplayer.size();
    }
}