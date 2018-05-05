package com.oxygen.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Matches.ClubsSettings;
import com.oxygen.yallagoom.entity.Matches.CompetitionSettings;
import com.oxygen.yallagoom.utils.ToolUtils;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewCompetitionsSettingsSelect extends RecyclerView.Adapter<RecycleViewCompetitionsSettingsSelect.MyViewHolder> implements SectionIndexer {


    private final Activity mContext;
    private final ArrayList<ClubsSettings> clubs;
    private final ArrayList<CompetitionSettings> compitetions;
    private final int type;
    private final ArrayList<String> dataStore;
    private  ArrayList<ClubsSettings> daStringListClubs;
    private  ArrayList<CompetitionSettings> daStringListCompitetions;
   // private ArrayList<MatchCategoryItem> daStringList;
    public Context context;
    private ArrayList<Integer> mSectionPositions;    @Override
    public Object[] getSections() {

        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        if (type==0){
            for (int i = 0, size = daStringListCompitetions.size(); i < size; i++) {
                String section = String.valueOf(daStringListCompitetions.get(i).getName().charAt(0)).toUpperCase();
                if (!sections.contains(section)) {
                    sections.add(section);
                    mSectionPositions.add(i);
                }
            }
        }else {
            for (int i = 0, size = daStringListClubs.size(); i < size; i++) {
                String section = String.valueOf(daStringListClubs.get(i).getName().charAt(0)).toUpperCase();
                if (!sections.contains(section)) {
                    sections.add(section);
                    mSectionPositions.add(i);
                }
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
    public RecycleViewCompetitionsSettingsSelect(Activity activity, ArrayList<ClubsSettings> clubs, ArrayList<CompetitionSettings> compitetions, int type, ArrayList<String> dataStore) {
        this.clubs = clubs;
        this.compitetions = compitetions;
        this.dataStore = dataStore;
        this.type = type;
        this.mContext = activity;
        if (type==0){
            daStringListCompitetions=compitetions;
        }else {
            daStringListClubs=clubs;

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comp_settings_select, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (type == 0) {
            holder.value.setText(compitetions.get(position).getName());
            holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    if (isChecked) {
                        if (!dataStore.contains(compitetions.get(position).getCompetition_id() + "-competitions:"+compitetions.get(position).getName())) {
                            dataStore.add(compitetions.get(position).getCompetition_id() + "-competitions:"+compitetions.get(position).getName());
                            Log.e("competitions",compitetions.get(position).getCompetition_id() + "-competitions:"+compitetions.get(position).getName());
                        }
                    } else {
                        Log.e("competitions_remove",compitetions.get(position).getCompetition_id() + "-competition:"+compitetions.get(position).getName());
                        dataStore.remove(compitetions.get(position).getCompetition_id() + "-competitions:"+compitetions.get(position).getName());
                    }
                    ToolUtils.setCompAndClub(context,dataStore);
                }
            });
            if (dataStore.contains(compitetions.get(position).getCompetition_id() + "-competitions:"+compitetions.get(position).getName()))
               holder.checkbox_select.setChecked(true);
        } else {
            holder.value.setText(clubs.get(position).getName());
            holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    if (isChecked) {
                        if (!dataStore.contains(clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName())) {
                            dataStore.add(clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName());
                            Log.e("clubs",clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName());

                        }

                    } else {
                        Log.e("clubs_remove",clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName());
                        dataStore.remove(clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName());
                    }
                    ToolUtils.setCompAndClub(context,dataStore);
                }
            });
            if (dataStore.contains(clubs.get(position).getParticipants() + "-clubs:"+clubs.get(position).getName()))
                holder.checkbox_select.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0) {
            return compitetions.size();
        } else {
            return clubs.size();

        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView value;
        private final RelativeLayout parent;
        private final SmoothCheckBox checkbox_select;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            value = (TextView) itemView.findViewById(R.id.value);
            checkbox_select = (SmoothCheckBox) itemView.findViewById(R.id.checkbox_select);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}