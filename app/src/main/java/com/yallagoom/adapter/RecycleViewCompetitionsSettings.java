package com.yallagoom.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleViewCompetitionsSettings extends RecyclerView.Adapter<RecycleViewCompetitionsSettings.MyViewHolder> implements SectionIndexer {


    private final Activity mContext;
    private final int type;
    private final MatchCategory matchCategory;
    private ArrayList<MatchCategoryItem> daStringList2;
    // private  ArrayList<MatchCategoryItem> daStringList1;
    public Context context;
    private ArrayList<Integer> mSectionPositions;

    public RecycleViewCompetitionsSettings(Activity activity, MatchCategory matchCategory, int type) {
        //    this.tickets_dates = tickets_dates;
        this.mContext = activity;
        this.type = type;
        this.matchCategory = matchCategory;
        //  daStringList1=matchCategory.getInternational();
        daStringList2 = matchCategory.getCountries();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comp_settings, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == 0) {
            holder.title.setText(context.getString(R.string.international));
        } else if (position == matchCategory.getInternational().size()) {
            holder.title.setText(context.getString(R.string.clubs));
        } else {
            holder.title.setVisibility(View.GONE);
        }
        if (position < matchCategory.getInternational().size()) {
            holder.value.setText(matchCategory.getInternational().get(position).getArea_name());
        } else {
            holder.value.setText(matchCategory.getCountries().get(position - matchCategory.getInternational().size()).getArea_name());

        }
      /*  if (position <matchCategory.getInternational().size()) {
            holder.value.setText(international.get(position).getArea_name());
            if (position == international.size() - 1) {
                holder.view.setVisibility(View.GONE);
            }
        } else {
            holder.value.setText(countries.get(position).getArea_name());
            if (position == countries.size() - 1) {
                holder.view.setVisibility(View.GONE);
            }
        }*/
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetClubCompitetionListApiAsyncTask getClubCompitetionListApiAsyncTask = new GetClubCompitetionListApiAsyncTask(context, new StringResultCallback() {
                    @Override
                    public void processFinish(String result, KProgressHUD progress) {
                        Intent intent = new Intent(context, SelectCompetitinsAndClubsActivity.class);
                        intent.putExtra("ResultClubCompitetion", "" + result);
                        if (position < matchCategory.getInternational().size()) {
                            intent.putExtra("title", "" + matchCategory.getInternational().get(position).getArea_name());
                            intent.putExtra("type", "international");
                        } else {
                            intent.putExtra("title", "" + matchCategory.getCountries().get(position - matchCategory.getInternational().size()).getArea_name());
                            intent.putExtra("type", "country");
                        }

                        context.startActivity(intent);
                    }
                });
                if (position < matchCategory.getInternational().size()) {
                    getClubCompitetionListApiAsyncTask.execute(matchCategory.getInternational().get(position).getArea_id() + "");
                } else {
                    getClubCompitetionListApiAsyncTask.execute(matchCategory.getCountries().get(position - matchCategory.getInternational().size()).getArea_id() + "");

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return (matchCategory.getCountries().size() + matchCategory.getInternational().size());
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
       /* for (int i = 0, size = (daStringList1.size()); i < size; i++) {
            String section = String.valueOf(daStringList1.get(i).getArea_name().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }*/
        for (int i = 0, size = (daStringList2.size()); i < size; i++) {
            String section2 = String.valueOf(daStringList2.get(i).getArea_name().charAt(0)).toUpperCase();
            if (!sections.contains(section2)) {
                sections.add(section2);
                mSectionPositions.add(i+(matchCategory.getInternational().size()));
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
        private final TextView value;
        private final RelativeLayout parent;
        private final View view;
        private final TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            value = (TextView) itemView.findViewById(R.id.value);
            title = (TextView) itemView.findViewById(R.id.title);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);
            view = (View) itemView.findViewById(R.id.view);


        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}