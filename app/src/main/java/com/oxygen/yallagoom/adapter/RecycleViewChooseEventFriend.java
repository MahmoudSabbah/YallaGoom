package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.event.Player;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.util.ArrayList;

public class RecycleViewChooseEventFriend extends RecyclerView.Adapter<RecycleViewChooseEventFriend.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<Player.PlayerList> playerLists;
    public Context context;
    public static CircularImageView image_player_not_send;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_player;
        private final TextView check_invited;
        private final RelativeLayout check_invited_lay;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_player = (CircularImageView)view.findViewById(R.id.image_player);
            check_invited = (TextView)view.findViewById(R.id.check_invited);
            check_invited_lay = (RelativeLayout)view.findViewById(R.id.check_invited_lay);


        }
    }


    public RecycleViewChooseEventFriend(ArrayList<Player.PlayerList> playerLists) {
        this.playerLists=playerLists;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_choose_event_friend, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       /* if (playerLists.get(position).getEventImage()!=null){
            imageLoader.loadImage(Constant.urlImage+""+playerLists.get(position).getEventImage(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap
                   // holder.progress_bar.setVisibility(View.GONE);
                    holder.image_player.setImageBitmap(loadedImage);
                }
            });
        }else {
           // holder.progress_bar.setVisibility(View.GONE);
        }*/

       if (playerLists.get(position).isInvited()){
           holder.check_invited.setText(context.getString(R.string.fa_close));
           holder.check_invited_lay.setVisibility(View.VISIBLE);
           image_player_not_send=holder.image_player;
       }else {
           holder.check_invited.setText(context.getString(R.string.fa_check));
           holder.check_invited_lay.setVisibility(View.GONE);

       }
        try {
            if (playerLists.get(position).getImg_url() != null) {
                ToolUtils.setImageSmall_50(Constant.imageUrl + playerLists.get(position).getImg_url() , holder.image_player, imageLoader);
            }
        } catch (NullPointerException e) {

        }
    }


    @Override
    public int getItemCount() {

        return playerLists.size();
    }
}