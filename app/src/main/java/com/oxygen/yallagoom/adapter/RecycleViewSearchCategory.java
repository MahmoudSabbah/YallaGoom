package com.oxygen.yallagoom.adapter;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.oxygen.yallagoom.R;

public class RecycleViewSearchCategory extends RecyclerView.Adapter<RecycleViewSearchCategory.MyViewHolder> implements Filterable {

    private final String[] category_list_data;
    public Context context;

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView name;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.name);

        }
    }


    public RecycleViewSearchCategory(String[] category_list_data) {
        this.category_list_data = category_list_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(category_list_data[position]);
    }


    @Override
    public int getItemCount() {
        // Log.e("pointsPath", ""+path.size());
        return category_list_data.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}