package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;

public class RecycleViewFriendsListGroup extends RecyclerView.Adapter<RecycleViewFriendsListGroup.MyViewHolder> {

    private final ImageLoader imageLoader;
    private ArrayList<Player.PlayerList> data;
    public Context context;
    private ArrayList<Integer> mSectionPositions;


    public void updateList(ArrayList<Player.PlayerList> listData) {
        data = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView friend_image;
        private final TextView friend_name;
        private final SmoothCheckBox checkbox;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            friend_image = (CircularImageView) view.findViewById(R.id.friend_image);
            checkbox = (SmoothCheckBox) view.findViewById(R.id.checkbox);
            friend_name = (TextView) view.findViewById(R.id.friend_name);

        }
    }


    public RecycleViewFriendsListGroup(ArrayList<Player.PlayerList> data) {
        this.data = data;
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_group_list_friends, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.friend_name.setText(data.get(position).getFirst_name() + " " + data.get(position).getLast_name());
        if (data.get(position).getCheckSelectGroup() == 1) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);

        }
        holder.checkbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    data.get(position).setCheckSelectGroup(1);
                } else {
                    data.get(position).setCheckSelectGroup(0);

                }
            }
        });
        if (data.get(position).getImg_url() != null) {
            ToolUtils.setImage(Constant.imageUrl + data.get(position).getImg_url(), holder.friend_image, imageLoader);
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
}