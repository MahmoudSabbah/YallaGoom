package com.oxygen.yallagoom.adapter;


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
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.ClubsDetailsActivity;
import com.oxygen.yallagoom.api.GetClubAndTeamsDetailsApiAsyncTask;
import com.oxygen.yallagoom.entity.Matches.ClubsAndTeams;
import com.oxygen.yallagoom.interfaces.StringResultCallback;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewClubsAndTeams extends RecyclerView.Adapter<RecycleViewClubsAndTeams.MyViewHolder> implements SectionIndexer {


    private final Activity mContext;
    private final ClubsAndTeams[] clubsAndTeams;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    public RecycleViewClubsAndTeams(Activity activity, ClubsAndTeams[] clubsAndTeams) {
        this.mContext = activity;
        this.clubsAndTeams = clubsAndTeams;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teams, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.acronym.setText(clubsAndTeams[position].getAcronym());
        holder.name.setText(clubsAndTeams[position].getName());
        holder.type.setText(clubsAndTeams[position].getGender());
        if (clubsAndTeams[position].getFounded().equalsIgnoreCase("")){
            holder.founded.setVisibility(View.GONE);
        }else {
            holder.founded.setText(mContext.getString(R.string.founded_since)+" "+clubsAndTeams[position].getFounded());
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetClubAndTeamsDetailsApiAsyncTask getClubAndTeamsDetailsApiAsyncTask=new GetClubAndTeamsDetailsApiAsyncTask(mContext, new StringResultCallback() {
                    @Override
                    public void processFinish(String result, KProgressHUD progress) {
                        Intent intent=new Intent(mContext, ClubsDetailsActivity.class);
                        intent.putExtra("Club_details",result);
                        intent.putExtra("Club_name",clubsAndTeams[position].getName());
                        mContext.startActivity(intent);
                    }
                });
                getClubAndTeamsDetailsApiAsyncTask.execute(clubsAndTeams[position].getId()+"");

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
            String section= String.valueOf(clubsAndTeams[i].getName().charAt(0)).toUpperCase();
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
        private final TextView founded;
        private final LinearLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            acronym = (TextView) itemView.findViewById(R.id.acronym);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            founded = (TextView) itemView.findViewById(R.id.founded);
            parent = (LinearLayout) itemView.findViewById(R.id.parent);
            view = (View) itemView.findViewById(R.id.view);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}