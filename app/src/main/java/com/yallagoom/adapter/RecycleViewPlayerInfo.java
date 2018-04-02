package com.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.PlayerDetailsMainActivity;
import com.yallagoom.api.GetPlayersApiAsyncTask;
import com.yallagoom.entity.Matches.Player.PlayerList;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewPlayerInfo extends RecyclerView.Adapter<RecycleViewPlayerInfo.MyViewHolder> implements SectionIndexer {


    private final Activity mContext;
    private final PlayerList[] clubsAndTeams;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    public RecycleViewPlayerInfo(Activity activity, PlayerList[] clubsAndTeams) {
        this.mContext = activity;
        this.clubsAndTeams = clubsAndTeams;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_player_info, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.acronym.setText(clubsAndTeams[position].getAcronym());
        holder.name.setText(clubsAndTeams[position].getName());
        holder.type.setText(clubsAndTeams[position].getGender());
        if (clubsAndTeams[position].getBirthdate().equalsIgnoreCase("")) {
            holder.age.setVisibility(View.GONE);
        } else {
            holder.age.setText(ToolUtils.getAge(clubsAndTeams[position].getBirthdate())+" "+mContext.getString(R.string.years_old));
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPlayersApiAsyncTask getPlayersApiAsyncTask = new GetPlayersApiAsyncTask(mContext, clubsAndTeams[position].getId(), new StringResultCallback() {
                    @Override
                    public void processFinish(String result, KProgressHUD progress) {
                        Intent intent = new Intent(mContext, PlayerDetailsMainActivity.class);
                        intent.putExtra("Player_details", result);
                        mContext.startActivity(intent);
                    }
                });
                getPlayersApiAsyncTask.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubsAndTeams.length;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);

        for (int i = 0, size = (clubsAndTeams.length); i < size; i++) {
            String section = String.valueOf(clubsAndTeams[i].getName().charAt(0)).toUpperCase();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView acronym;
        private final View view;
        private final TextView name;
        private final TextView type;
        private final TextView age;
        private final LinearLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            acronym = (TextView) itemView.findViewById(R.id.acronym);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            age = (TextView) itemView.findViewById(R.id.age);
            parent = (LinearLayout) itemView.findViewById(R.id.parent);
            view = (View) itemView.findViewById(R.id.view);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}