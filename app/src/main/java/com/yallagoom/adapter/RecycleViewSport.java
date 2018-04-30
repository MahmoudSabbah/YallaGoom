package com.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.warkiz.widget.IndicatorSeekBar;
import com.yallagoom.R;
import com.yallagoom.entity.AllSport;
import com.yallagoom.entity.SportObject;
import com.yallagoom.entity.SportSave;
import com.yallagoom.widget.SmoothCheckBox.SmoothCheckBox;
import com.yallagoom.widget.animator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewSport extends RecyclerView.Adapter<RecycleViewSport.MyViewHolder> {

    private ArrayList<SportObject> dataSport;
    public Context context;
    // private List<Driver> drivers;
    public int lastPosition = -1;
    private List<SportSave> hashMaps;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final LinearLayout rate_myself;
        private final SmoothCheckBox checkbox;
        private final TextView your_rate;
        private final TextView text_note2;
        private final IndicatorSeekBar seekbar;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            rate_myself = (LinearLayout) view.findViewById(R.id.rate_myself);
            checkbox = (SmoothCheckBox) view.findViewById(R.id.checkbox);
            your_rate = (TextView) view.findViewById(R.id.your_rate);
            text_note2 = (TextView) view.findViewById(R.id.text_note2);
            seekbar = (IndicatorSeekBar) view.findViewById(R.id.seekbar);


        }
    }


    public RecycleViewSport(ArrayList<SportObject> data) {
        this.dataSport = data;
        hashMaps = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sport_fram1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.text_note2.setText(dataSport.get(position).getName_en());
        holder.seekbar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                Log.e("progress", "" + progress);
                Log.e("progressFloat", "" + progressFloat);
                holder.your_rate.setText("Your Rate: (" + progress + "/10)");
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        holder.checkbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {

                if (isChecked) {
                   // HashMap<String, SportSave> integerSportSaveHashMap = new HashMap<>();
                    SportSave sportSave = new SportSave();
                    sportSave.setSport_id(dataSport.get(position).getSport_id());
                    sportSave.setRate(holder.seekbar.getProgress());
                    //integerSportSaveHashMap.put("data", sportSave);
                    hashMaps.add(sportSave);
                    holder.rate_myself.setVisibility(View.VISIBLE);
                    ViewAnimator
                            .animate(holder.rate_myself)
                            .duration(1000)
                            .bounceIn()
                            .start();
                } else {
                    for (int i=0;i<hashMaps.size();i++){
                        if (hashMaps.get(i).getSport_id()==dataSport.get(position).getSport_id()){
                            Log.e("hashMapshashMaps",""+i);
                            hashMaps.remove(i);
                        }
                    }
                    new CountDownTimer(1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            holder.rate_myself.setVisibility(View.GONE);
                        }

                    }.start();
                    ViewAnimator
                            .animate(holder.rate_myself)
                            .duration(1000)
                            .bounceOut()
                            .start();

                }
            }
        });

    }

    public List< SportSave> getList() {
        return hashMaps;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return dataSport.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}