package com.oxygen.yallagoom.adapter.event.chat;

/**
 * Created by pdx on 5/7/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.AddFriendChatActivity;
import com.oxygen.yallagoom.entity.Chat.UserCredentials;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.CircularImageView;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;

public class RecycleViewChatChooseFriend extends RecyclerView.Adapter<RecycleViewChatChooseFriend.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<User> friendLists;
    public static ArrayList<UserCredentials> userCredentialsArrayList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView image_friend;
        private final TextView name;
        private final SmoothCheckBox checkbox_select;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            image_friend = (CircularImageView) view.findViewById(R.id.image_friend);
            name = (TextView) view.findViewById(R.id.name);
            checkbox_select = (SmoothCheckBox) view.findViewById(R.id.checkbox_select);
            userCredentialsArrayList = new ArrayList<>();

        }
    }


    public RecycleViewChatChooseFriend(ArrayList<User> friendLists) {
        this.friendLists = friendLists;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend_chat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(friendLists.get(position).getFirst_name() + " " + friendLists.get(position).getLast_name());
        Log.e("getImg_url", "" + friendLists.get(position).getImg_url());
        if (friendLists.get(position).getImg_url() != null) {
            ToolUtils.setImage(Constant.imageUrl + "" + friendLists.get(position).getImg_url(), holder.image_friend, imageLoader);
        } else {
        }
        holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    UserCredentials userCredentials = new UserCredentials();
                    userCredentials.setEmail(friendLists.get(position).getEmail());
                    userCredentials.setKey(friendLists.get(position).getFirebase_auth_user_id());
                    userCredentials.setName(friendLists.get(position).getFirst_name() + " " + friendLists.get(position).getLast_name());
                    userCredentials.setProfilePicLink(Constant.imageUrl + friendLists.get(position).getImg_url());
                    userCredentialsArrayList.add(userCredentials);
                }else {
                    for (int i=0;i<userCredentialsArrayList.size();i++){
                        if (userCredentialsArrayList.get(i).getKey().equalsIgnoreCase(friendLists.get(position).getFirebase_auth_user_id())){
                            userCredentialsArrayList.remove(i);

                        }
                    }

                }
                if (!((Activity)context).getIntent().hasExtra("AddFriend")){
                    if (userCredentialsArrayList.size()>1){
                        AddFriendChatActivity.group_lay.setVisibility(View.VISIBLE);
                    }else {
                        AddFriendChatActivity.group_lay.setVisibility(View.GONE);
                    }
                }

            }
        });

    }


    @Override
    public int getItemCount() {

        return friendLists.size();
    }
}