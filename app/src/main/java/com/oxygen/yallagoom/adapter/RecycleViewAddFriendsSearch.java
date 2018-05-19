package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.api.AddFriendAsyncTask;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.interfaces.AddFriendCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAddFriendsSearch extends RecyclerView.Adapter<RecycleViewAddFriendsSearch.MyViewHolder> implements SectionIndexer {

    private final ImageLoader imageLoader;
    private ArrayList<Player.PlayerList> daStringList;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    public RecycleViewAddFriendsSearch(Player player) {
        this.daStringList = player.getData();
        imageLoader= ImageLoader.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend_search, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // holder.player_name.setText(daStringList.get(position).getFirst_name()+" "+daStringList.get(position).getLast_name() );
        holder.player_name.setText(daStringList.get(position).getFirst_name()+" "+daStringList.get(position).getLast_name());
        holder.check_friend.setSelected(true);
        holder.check_friend.setText(context.getString(R.string.add_friend));
        holder.check_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFriendAsyncTask addFriendAsyncTask=new AddFriendAsyncTask(context, new AddFriendCallback() {
                    @Override
                    public void processFinish() {

                    }
                });
                addFriendAsyncTask.execute(daStringList.get(position).getId()+"");

             /*   if (holder.check_friend.isSelected()) {
                    holder.check_friend.setSelected(false);
                    holder.check_friend.setText(context.getString(R.string.already_friend));
                }else {
                    holder.check_friend.setSelected(true);
                    holder.check_friend.setText(context.getString(R.string.add_friend));

                }*/

            }
        });
        try {
            if (daStringList.get(position).getImg_url() != null) {
                ToolUtils.setImageSmall_50(Constant.imageUrl + daStringList.get(position).getImg_url(), holder.image_player, imageLoader);
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    public int getItemCount() {
        return daStringList.size();
    }

    public void updateList(ArrayList<Player.PlayerList> listData) {
        daStringList = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView player_name;
        private final TextView check_friend;
        private final TextView location_name;
        private final CircularImageView image_player;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            player_name = (TextView) itemView.findViewById(R.id.player_name);
            location_name = (TextView) itemView.findViewById(R.id.location_name);
            check_friend = (TextView) itemView.findViewById(R.id.check_friend);
            image_player = (CircularImageView) itemView.findViewById(R.id.image_player);

        }
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = daStringList.size(); i < size; i++) {
            String section = String.valueOf(daStringList.get(i).getFirst_name().charAt(0)).toUpperCase();
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
        return i;
    }

    public int getItemViewType(int position) {
        return position;
    }

}