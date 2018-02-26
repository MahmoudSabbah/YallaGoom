package com.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.api.DeleteGroupAsyncTask;
import com.yallagoom.entity.Group;
import com.yallagoom.interfaces.ClickPopUpCallback;
import com.yallagoom.interfaces.DeleteCallback;
import com.yallagoom.interfaces.DeleteFragmentCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;
import com.yallagoom.widget.PhotoChoicePopup;

import java.util.ArrayList;

public class RecycleViewListPlayerInGroup extends RecyclerView.Adapter<RecycleViewListPlayerInGroup.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<Group.MyGroup.Data.memberslist> memberslists;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CircularImageView player_image;


        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            player_image = (CircularImageView) view.findViewById(R.id.player_image);


        }
    }


    public RecycleViewListPlayerInGroup(ArrayList<Group.MyGroup.Data.memberslist> memberslists) {

        imageLoader = ImageLoader.getInstance();
        this.memberslists = memberslists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_player_list_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            if (memberslists.get(position).getMember_data().getImg_url() != null) {
                ToolUtils.setImageSmall_50(Constant.imageUrl + memberslists.get(position).getMember_data().getImg_url(), holder.player_image, imageLoader);
            }
        } catch (NullPointerException e) {

        }
       /* */

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return memberslists.size();
    }

}