package com.yallagoom.adapter.event.chat;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.activity.ChatActivity;
import com.yallagoom.entity.Chat.ConversationsGroup;
import com.yallagoom.entity.Chat.UserConversations;
import com.yallagoom.entity.Chat.UserCredentials;
import com.yallagoom.entity.User;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.CircularImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class RecycleViewConversations extends RecyclerView.Adapter<RecycleViewConversations.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final Map<String, UserConversations> lastMes;
    private final Map<String, ArrayList<UserCredentials>> userCredentials;
    private final User myUser;
    private final Map<String, ConversationsGroup> conversationsGroupMap;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircularImageView ivUserChat;
        private final TextView txtName;
        private final TextView txtMessage;
        private final TextView txtTime;
        private final LinearLayout topView;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            ivUserChat = (CircularImageView) view.findViewById(R.id.icon_avata);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            topView = (LinearLayout) view.findViewById(R.id.topView);


        }
    }


    public RecycleViewConversations(Map<String, UserConversations> lastMes, Map<String, ArrayList<UserCredentials>> userCredentials, Map<String, ConversationsGroup> conversationsGroupMap, User myuser) {
        this.lastMes = lastMes;
        this.userCredentials = userCredentials;
        this.conversationsGroupMap = conversationsGroupMap;
        this.myUser = myuser;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_conversations, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ArrayList<UserCredentials> listUser = userCredentials.get(userCredentials.keySet().toArray()[position]);
        Log.e("listUser",""+listUser.size());
        if (conversationsGroupMap.get(conversationsGroupMap.keySet().toArray()[position]) != null) {
            holder.txtName.setText(conversationsGroupMap.get(conversationsGroupMap.keySet().toArray()[position]).getTitle());
            holder.ivUserChat.setImageResource(R.drawable.folder_group_icon);
        } else {
            if (listUser.get(0).getKey().equalsIgnoreCase(myUser.getFirebase_auth_user_id())) {
                if (listUser.size()>1){
                    holder.txtName.setText(listUser.get(1).getName());
                }
            } else {
                holder.txtName.setText(listUser.get(0).getName());
            }
            ToolUtils.setImage(userCredentials.get(userCredentials.keySet().toArray()[position]).get(0).getProfilePicLink(), holder.ivUserChat, imageLoader);

        }
        if (lastMes.get(lastMes.keySet().toArray()[position]).getType().equalsIgnoreCase("text")) {
            holder.txtMessage.setText(lastMes.get(lastMes.keySet().toArray()[position]).getContent());
        }else  if (lastMes.get(lastMes.keySet().toArray()[position]).getType().equalsIgnoreCase("photo")) {
            holder.txtMessage.setText(context.getString(R.string.image));
        }else  if (lastMes.get(lastMes.keySet().toArray()[position]).getType().equalsIgnoreCase("audio")) {
            holder.txtMessage.setText(context.getString(R.string.audio));
        }
      /*  if (listUser.size() == 2) {
            if (listUser.get(0).getKey().equalsIgnoreCase(myUser.getFirebase_auth_user_id())) {
                holder.txtName.setText(listUser.get(1).getName());
            } else {
                holder.txtName.setText(listUser.get(0).getName());
            }
        } else {
            //   holder.txtName.setText(conversationsGroupMap.get(conversationsGroupMap.keySet().toArray()[position]).getTitle());
        }*/

      /*  if (listUser.size() > 2) {
        } else {

        }*/
        holder.txtTime.setText(converteTimestamp(lastMes.get(lastMes.keySet().toArray()[position]).getTimestamp()) + "");
        holder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("key", lastMes.keySet().toArray()[position] + "");
                intent.putExtra("title", holder.txtName.getText().toString());
                if (conversationsGroupMap.get(conversationsGroupMap.keySet().toArray()[position]) != null){
                    intent.putExtra("OwnerID", conversationsGroupMap.get(conversationsGroupMap.keySet().toArray()[position]).getOwnerID());
                }
                intent.putExtra("listOfUser", listUser);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        return lastMes.size();
    }

    private CharSequence converteTimestamp(long mileSegundos) {
        //  Log.e("getDate",ToolUtils.getDate(mileSegundos,Constant.yyyy_MM_dd__HH_mm_ss));

        return DateUtils.getRelativeTimeSpanString(mileSegundos * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}