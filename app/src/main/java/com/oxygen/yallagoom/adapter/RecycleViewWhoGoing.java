package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.event.InvitationRecord;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewWhoGoing extends RecyclerView.Adapter<RecycleViewWhoGoing.MyViewHolder> {

    private final ArrayList<InvitationRecord> invitationRecords;
    private final String key;
    private final ImageLoader imageLoader;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView user_image;
        private final TextView user_name;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            user_image = (CircularImageView) view.findViewById(R.id.user_image);
            user_name = (TextView) view.findViewById(R.id.user_name);

        }
    }


    public RecycleViewWhoGoing(String key, ArrayList<InvitationRecord> invitationRecords) {
        this.invitationRecords = invitationRecords;
        this.key = key;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecycleViewWhoGoing.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_who_going, parent, false);

        return new RecycleViewWhoGoing.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewWhoGoing.MyViewHolder holder, final int position) {
        holder.user_name.setText(invitationRecords.get(position).getUser_invitee_data().getFirst_name()+" "+
                invitationRecords.get(position).getUser_invitee_data().getLast_name());
        if (invitationRecords.get(position).getUser_invitee_data().getImg_url() != null) {
            ToolUtils.setImage(Constant.imageUrl + invitationRecords.get(position).getUser_invitee_data().getImg_url(),
                    holder.user_image, imageLoader);
        }

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return invitationRecords.size();
    }
}
