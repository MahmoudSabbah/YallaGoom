package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.SportObject;
import com.oxygen.yallagoom.entity.SportSave;
import com.oxygen.yallagoom.widget.RangeSeekBar;
import com.oxygen.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleViewSettingsHomeClickHobbies extends RecyclerView.Adapter<RecycleViewSettingsHomeClickHobbies.MyViewHolder> {

    private final SportObject[] allSport;
    private final Map<Integer, Integer> userSportId;
    public Context context;
    public List<SportSave> listUserSport;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView sport_name;
        private final TextView rate;
        private final SmoothCheckBox checkbox;
        private final RangeSeekBar rangeSeekbar2;
        private final RelativeLayout rate_lay;


        //  private final RelativeLayout p_layout;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            checkbox = (SmoothCheckBox) view.findViewById(R.id.checkbox);
            sport_name = (TextView) view.findViewById(R.id.sport_name);
            rate = (TextView) view.findViewById(R.id.rate);
            rate_lay = (RelativeLayout) view.findViewById(R.id.rate_lay);
            rangeSeekbar2 = (RangeSeekBar) view.findViewById(R.id.seekbar);


        }
    }


    public RecycleViewSettingsHomeClickHobbies(SportObject[] allSport, Map<Integer, Integer> userSportId) {
        this.allSport = allSport;
        this.userSportId = userSportId;
        listUserSport = new ArrayList<>();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_settings_home_click_hobbies, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SportSave sportSave = new SportSave();
        if (userSportId.containsKey(allSport[position].getId())) {
            sportSave.setSport_id(allSport[position].getId());
            sportSave.setRate(userSportId.get(allSport[position].getId()));
            holder.rate_lay.setVisibility(View.VISIBLE);
            holder.rate.setVisibility(View.VISIBLE);
            listUserSport.add(sportSave);
            holder.rate.setText(context.getString(R.string.rate_your_self) + " (" + userSportId.get(allSport[position].getId()) + "/10)");
            holder.rangeSeekbar2.setValue(userSportId.get(allSport[position].getId()));
            holder.checkbox.setChecked(true);
        }
        holder.sport_name.setText(allSport[position].getName_en());
        holder.checkbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                int value = (int) holder.rangeSeekbar2.getCurrentRange()[0];
                sportSave.setSport_id(allSport[position].getId());
                sportSave.setRate(value);
                if (isChecked) {
                    holder.rate_lay.setVisibility(View.VISIBLE);
                    holder.rate.setVisibility(View.VISIBLE);
                    listUserSport.add(sportSave);
                } else {
                    for (int i = 0; i < listUserSport.size(); i++) {
                        if (listUserSport.get(i).getSport_id() == allSport[position].getId()) {
                            listUserSport.remove(i);
                            holder.rangeSeekbar2.setValue(1);
                        }
                    }
                    holder.rate_lay.setVisibility(View.GONE);
                    holder.rate.setVisibility(View.GONE);
                }
            }
        });
        holder.rangeSeekbar2.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                int value = (int) holder.rangeSeekbar2.getCurrentRange()[0];
                holder.rate.setText(context.getString(R.string.rate_your_self) + " (" + value + "/10)");
                sportSave.setRate(value);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return allSport.length;
    }
}