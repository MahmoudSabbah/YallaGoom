package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.api.AddFriendAsyncTask;
import com.oxygen.yallagoom.interfaces.AddFriendCallback;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleViewAddFriendsMobile extends RecyclerView.Adapter<RecycleViewAddFriendsMobile.MyViewHolder> implements SectionIndexer {

    private final List<String> mobiles;
    private final List<String> id;
    private List<HashMap<String, String>> daStringList;
    public Context context;
    private ArrayList<Integer> mSectionPositions;
  // private List<HashMap<String,String>> listAddFriend;

    public RecycleViewAddFriendsMobile(List<HashMap<String, String>> daStringList, List<String> mobiles, List<String> id) {
        this.daStringList = daStringList;
        this.mobiles = mobiles;
        this.id = id;
      //  listAddFriend=new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_friend_contact, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (mobiles.contains(daStringList.get(position).get("phoneNumber"))) {
            holder.add_friend.setSelected(true);
            holder.add_friend.setText(context.getString(R.string.add_friend));
       /*     HashMap<String,String> stringStringHashMap=new HashMap<>();
            stringStringHashMap.put("name",daStringList.get(position).get("name"));
            stringStringHashMap.put("phoneNumber",daStringList.get(position).get("phoneNumber"));*/

        } else {
            holder.add_friend.setSelected(false);
            holder.add_friend.setText(context.getString(R.string.invite_to_app));
        }
        holder.contact_name.setText(daStringList.get(position).get("name"));
        holder.add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.add_friend.isSelected()){
                    AddFriendAsyncTask addFriendAsyncTask=new AddFriendAsyncTask(context, new AddFriendCallback() {
                        @Override
                        public void processFinish() {

                        }
                    });
                    addFriendAsyncTask.execute(id.get(position));
                }else {
                    ToolUtils.sendSMS(context,daStringList.get(position).get("phoneNumber"),"YallaGoom");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return daStringList.size();
    }

    public void updateList(List<HashMap<String, String>> listData) {
        this.daStringList = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView contact_name;
        private final TextView add_friend;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            contact_name = (TextView) itemView.findViewById(R.id.contact_name);
            add_friend = (TextView) itemView.findViewById(R.id.add_friend);

        }
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = daStringList.size(); i < size; i++) {
            String section = String.valueOf(daStringList.get(i).get("name").charAt(0)).toUpperCase();
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

    public int getItemViewType(int position) {
        return position;
    }

}