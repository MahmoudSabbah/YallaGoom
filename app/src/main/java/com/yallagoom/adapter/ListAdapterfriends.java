package com.yallagoom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.entity.Category;
import com.yallagoom.widget.PinnedSectionListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class ListAdapterfriends extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    public static final int SECTION = 1;


    private final LayoutInflater inflater;
    private final List<String> data;

    public ListAdapterfriends(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == SECTION;
    }

    private class ViewHolder {
        TextView name;
        TextView check;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_category, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.check = (TextView) convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
