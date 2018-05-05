package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.oxygen.yallagoom.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAddFriendsMobileSticky implements SectionIndexer, StickyRecyclerHeadersAdapter<RecycleViewAddFriendsMobileSticky.HeaderItemViewHolder> {

    private final ImageLoader imageLoader;
    private final List<String> dataplayer;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = dataplayer.size(); i < size; i++) {
            String section = String.valueOf(dataplayer.get(i).charAt(0)).toUpperCase();
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

    public class HeaderItemViewHolder extends RecyclerView.ViewHolder {
        public TextView player_name;

        public HeaderItemViewHolder(View view) {
            super(view);
            player_name = (TextView) view.findViewById(R.id.player_name);
        }
    }

    public RecycleViewAddFriendsMobileSticky(List<String> strings) {
        this.dataplayer = strings;
        imageLoader = ImageLoader.getInstance();


    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public HeaderItemViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friends_event, parent, false);

        return new HeaderItemViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderItemViewHolder holder, int position) {
        holder.player_name.setText(dataplayer.get(position));
    }


    @Override
    public int getItemCount() {

        return dataplayer.size();
    }

}