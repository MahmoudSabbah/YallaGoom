package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.yallagoom.R;
import com.yallagoom.entity.Discover;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDiscoverByCategory extends RecyclerView.Adapter<RecycleViewDiscoverByCategory.MyViewHolder> {

    private final ArrayList<Discover.CategoryList> category_list;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView title;
        private final View _view;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            number = (TextView) view.findViewById(R.id.number);
            title = (TextView) view.findViewById(R.id.title);
            _view = (View) view.findViewById(R.id._view);

        }
    }


    public RecycleViewDiscoverByCategory(ArrayList<Discover.CategoryList> category_list) {
        this.category_list = category_list;

    }

    @Override
    public RecycleViewDiscoverByCategory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_discover_by_category, parent, false);

        return new RecycleViewDiscoverByCategory.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewDiscoverByCategory.MyViewHolder holder, final int position) {
        holder.title.setText(category_list.get(position).getCategoryName());
        if (position == category_list.size() - 1) {
            holder._view.setVisibility(View.GONE);
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}
