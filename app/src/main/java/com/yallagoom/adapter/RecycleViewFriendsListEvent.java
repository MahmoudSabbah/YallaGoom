package com.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.PlayerListCallback;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewFriendsListEvent extends RecyclerView.Adapter<RecycleViewFriendsListEvent.MyViewHolder> implements SectionIndexer {

    private final ImageLoader imageLoader;
    private  ArrayList<Player.PlayerList> dataplayer;
    private PlayerListCallback playerCallback;
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

    public void updateList(ArrayList<Player.PlayerList> listData) {
        dataplayer=listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_player;
        private final RelativeLayout parent;
        private final TextView player_name;
        private final TextView location_name;
        private final TextView sport_type;
        private final ProgressBar progress_bar;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_player = (CircularImageView) view.findViewById(R.id.image_player);
            parent = (RelativeLayout) view.findViewById(R.id.parent);
            player_name = (TextView) view.findViewById(R.id.player_name);
            location_name = (TextView) view.findViewById(R.id.location_name);
            sport_type = (TextView) view.findViewById(R.id.sport_type);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);


        }
    }


    public RecycleViewFriendsListEvent(Player player, PlayerListCallback playerCallback) {
        this.playerCallback = playerCallback;
        this.dataplayer = player.getData();
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friends_event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.player_name.setText(dataplayer.get(position).getFirst_name()+" "+dataplayer.get(position).getLast_name());
       /* holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("isSelected", "" + holder.image_player.isSelected());
                if (holder.image_player.isSelected()) {
                    holder.image_player.setImageResource(R.drawable.default_image);
                    holder.image_player.setSelected(false);
                    playerLists.remove(dataplayer.get(position));
                    playerCallback.processFinish(playerLists);
                } else {
                    holder.image_player.setSelected(true);
                    holder.image_player.setImageResource(R.drawable.image_selected);
                    playerLists.add(dataplayer.get(position));
                    playerCallback.processFinish(playerLists);

                }
            }
        });*/
        holder.progress_bar.setVisibility(View.GONE);

      /*  if (dataplayer.get(position).getEventImage()!=null){
            imageLoader.loadImage(Constant.urlImage+""+dataplayer.get(position).getEventImage(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap
                    holder.progress_bar.setVisibility(View.GONE);
                    holder.image_player.setImageBitmap(loadedImage);
                }
            });
        }else {
            holder.progress_bar.setVisibility(View.GONE);
        }
*/
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return dataplayer.size();
    }
}