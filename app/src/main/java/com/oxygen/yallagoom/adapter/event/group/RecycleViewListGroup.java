package com.oxygen.yallagoom.adapter.event.group;

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
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewListPlayerInGroup;
import com.oxygen.yallagoom.api.DeleteGroupAsyncTask;
import com.oxygen.yallagoom.entity.Group;
import com.oxygen.yallagoom.interfaces.ClickPopUpCallback;
import com.oxygen.yallagoom.interfaces.DeleteCallback;
import com.oxygen.yallagoom.interfaces.DeleteFragmentCallback;
import com.oxygen.yallagoom.widget.PhotoChoicePopup;

import java.util.ArrayList;

public class RecycleViewListGroup extends RecyclerView.Adapter<RecycleViewListGroup.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<Group.MyGroup.Data> group;
    private final int check;
    private final DeleteFragmentCallback deleteCallback;
    public Context context;
    private PhotoChoicePopup photoPopup;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView group_name;
        private final LinearLayout images;
        private final TextView list_icon;
        private final TextView number;
        private final RecyclerView list_recycler;

        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            group_name = (TextView) view.findViewById(R.id.group_name);
            images = (LinearLayout) view.findViewById(R.id.images);
            list_recycler = (RecyclerView) view.findViewById(R.id.list_recycler);
            list_icon = (TextView) view.findViewById(R.id.list_icon);
            number = (TextView) view.findViewById(R.id.number);


        }
    }


    public RecycleViewListGroup(ArrayList<Group.MyGroup.Data> group, int check, DeleteFragmentCallback deleteCallback) {
        this.group = group;
        this.check = check;
        this.deleteCallback = deleteCallback;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (check == 1) {
            holder.list_icon.setVisibility(View.GONE);

        } else {
            holder.list_icon.setVisibility(View.VISIBLE);
        }

        holder.group_name.setText(group.get(position).getTitle());
        Log.e("getMembers_list", "" + group.get(position).getMembers_list().size());
        if (group.get(position).getMembers_list().size() > 5) {
            holder.number.setText("(" + "+" + (group.get(position).getMembers_list().size() - 5) + ")");
        } else {
            holder.number.setVisibility(View.GONE);
        }
        ArrayList<Group.MyGroup.Data.memberslist> memberslists = new ArrayList<>();
        for (int i = 0; i < group.get(position).getMembers_list().size(); i++) {
            if (i < 5) {
                Log.e("getFirst_name", "" + i);

                memberslists.add(group.get(position).getMembers_list().get(i));
           /*     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen._40sdp), (int) context.getResources().getDimension(R.dimen._40sdp));
                CircularImageView circularImageView = new CircularImageView(context);
                circularImageView.setLayoutParams(layoutParams);
                circularImageView.setImageResource(R.drawable.default_image_small);
                holder.images.addView(circularImageView);*/
            }
        }
        RecycleViewListPlayerInGroup recycleViewListPlayerInGroup = new RecycleViewListPlayerInGroup(memberslists);
        holder.list_recycler.setAdapter(recycleViewListPlayerInGroup);
        holder.list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoPopup = new PhotoChoicePopup(context, group.get(position).getTitle(), new ClickPopUpCallback() {
                    @Override
                    public void processFinish(final int check) {
                        DeleteGroupAsyncTask deleteGroupAsyncTask = new DeleteGroupAsyncTask(context, new DeleteCallback() {
                            @Override
                            public void processFinish(int position1) {
                                group.remove(position);
                                notifyDataSetChanged();
                                // deleteCallback.processFinish(position, group, check);

                            }
                        });
                        deleteGroupAsyncTask.execute("" + group.get(position).getId());
                        photoPopup.dismiss();
                    }
                });
                photoPopup.showAtLocation(((Activity) context).findViewById(R.id.main_frame),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return group.size();
    }

}