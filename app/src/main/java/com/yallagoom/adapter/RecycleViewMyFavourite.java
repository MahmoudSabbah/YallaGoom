package com.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.activity.SelectCompetitinsAndClubsActivity;
import com.yallagoom.api.GetClubCompitetionListApiAsyncTask;
import com.yallagoom.entity.Matches.MatchCategory;
import com.yallagoom.entity.Matches.MatchCategoryItem;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewMyFavourite extends RecyclerView.Adapter<RecycleViewMyFavourite.MyViewHolder> {

    private final Activity mContext;
    private final ArrayList<String> club;
    private final ArrayList<String> comp;
    private final ArrayList<String> allData;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    public RecycleViewMyFavourite(Activity activity, ArrayList<String> comp, ArrayList<String> club, ArrayList<String> allData) {
        //    this.tickets_dates = tickets_dates;
        this.mContext = activity;
        this.club = club;
        this.comp = comp;
        this.allData = allData;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_my_favourite, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     /*   String[] list = arrayOfCompAndClub.get(position).split(":");
        holder.value.setText(list[1]);*/
     holder.checkbox_select.setChecked(true);
        if (position == 0) {
            holder.title.setText(R.string.select_comp);
            holder.title.setVisibility(View.VISIBLE);
        } else if (position == comp.size()) {
            holder.title.setText(R.string.select_club);
            holder.title.setVisibility(View.VISIBLE);
        }else {
            holder.title.setVisibility(View.GONE);

        }
        if (position <comp.size()) {
            holder.value.setText(comp.get(position).split(":")[1]);
        } else {
            holder.value.setText(club.get(position - comp.size()).split(":")[1]);

        }
        holder.checkbox_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<comp.size()){
                    Log.e("allData1",""+position+" - "+comp.get(position));
                    allData.remove(comp.get(position));
                    comp.remove(comp.get(position));
                    //   allData.remove(comp.get(position));
                }else {
                    Log.e("allData2",""+position+" - "+club.get(position - comp.size()));
                    allData.remove(club.get(position - comp.size()));
                    club.remove(club.get(position - comp.size()));
                   //
                }
               // Log.e("allData",""+position);
                Log.e("allData3",""+allData.size());
                notifyDataSetChanged();
                ToolUtils.setCompAndClub(context,allData);
            }
        });
       /* holder.checkbox_select.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (position<comp.size()){
                    comp.remove(comp.get(position));
                    allData.remove(comp.get(position));
                }else {
                    club.remove(club.get(position - comp.size()));
                    allData.remove(club.get(position - comp.size()));
                }
                notifyDataSetChanged();
                ToolUtils.setCompAndClub(context,allData);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return (club.size() + comp.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView value;
        private final RelativeLayout parent;
        private final View view;
        private final TextView title;
        private final SmoothCheckBox checkbox_select;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            value = (TextView) itemView.findViewById(R.id.value);
            title = (TextView) itemView.findViewById(R.id.title);
            checkbox_select = (SmoothCheckBox) itemView.findViewById(R.id.checkbox_select);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);
            view = (View) itemView.findViewById(R.id.view);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}