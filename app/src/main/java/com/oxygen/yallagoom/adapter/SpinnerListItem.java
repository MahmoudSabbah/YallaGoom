package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oxygen.yallagoom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/16/2018.
 */

public class SpinnerListItem extends BaseAdapter {
    private List<String> alertList;
    private LayoutInflater mInflater;

    public SpinnerListItem(Context context) {
        alertList = new ArrayList<>();
        alertList.add("January");
        alertList.add("Feburary");
        alertList.add("March");
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alertList.size();
    }

    @Override
    public Object getItem(int i) {
        return alertList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_spinner_, null);
            holder = new ViewHolder();
            holder.spinnerValue = (TextView) view.findViewById(R.id.spinner_text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.spinnerValue.setText(alertList.get(i));
        return view;
    }

    static class ViewHolder {
        TextView spinnerValue; //spinner name
    }
}
