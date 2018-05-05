package com.oxygen.yallagoom.adapter.event.findPlayer;

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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Player;
import com.oxygen.yallagoom.interfaces.PlayerListCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.util.ArrayList;

public class RecycleViewFindPlayer extends RecyclerView.Adapter<RecycleViewFindPlayer.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<Player.PlayerList> dataplayer;
    private PlayerListCallback playerCallback;
    public Context context;
    private ArrayList<Player.PlayerList> playerLists = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_player;
        private final RelativeLayout parent;
        private final TextView player_name;
        private final TextView location_name;
        private final TextView sport_type;
        private final ProgressBar progress_bar;
        private final CircularImageView default_image;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_player = (CircularImageView) view.findViewById(R.id.image_player);
            default_image = (CircularImageView) view.findViewById(R.id.default_image);
            parent = (RelativeLayout) view.findViewById(R.id.parent);
            player_name = (TextView) view.findViewById(R.id.player_name);
            location_name = (TextView) view.findViewById(R.id.location_name);
            sport_type = (TextView) view.findViewById(R.id.sport_type);
            progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);


        }
    }


    public RecycleViewFindPlayer(Player player, PlayerListCallback playerCallback) {
        this.playerCallback = playerCallback;
        this.dataplayer = player.getData();
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_find_player, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.player_name.setText(dataplayer.get(position).getFirst_name() + " " + dataplayer.get(position).getLast_name());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("isSelected", "" + holder.image_player.isSelected());
                if (holder.image_player.isSelected()) {
                    holder.image_player.setSelected(false);
                    playerLists.remove(dataplayer.get(position));
                    holder.default_image.setVisibility(View.GONE);
                    playerCallback.processFinish(playerLists);
                } else {
                    holder.image_player.setSelected(true);
                    holder.default_image.setVisibility(View.VISIBLE);
                    playerLists.add(dataplayer.get(position));
                    playerCallback.processFinish(playerLists);

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
        if (dataplayer.get(position).getGet_user_sports_list().size()==0){
            holder.sport_type.setVisibility(View.GONE);
        }else {
            holder.sport_type.setText("");
            for (int i = 0; i < dataplayer.get(position).getGet_user_sports_list().size(); i++) {
                Log.e("getGet_user_sports_list",""+dataplayer.get(position).getGet_user_sports_list().get(i).getGet_sports_data().getName_en());
                holder.sport_type.append(dataplayer.get(position).getGet_user_sports_list().get(i).getGet_sports_data().getName_en());
                if (i!=dataplayer.get(position).getGet_user_sports_list().size()-1){
                    holder.sport_type.append(" , ");
                }
            }
        }
        holder.location_name.setText(dataplayer.get(position).getGet_country_data().getName_en());
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return dataplayer.size();
    }
}