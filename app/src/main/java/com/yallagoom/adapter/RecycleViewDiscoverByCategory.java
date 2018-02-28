package com.yallagoom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.activity.DiscoverCategoryActivity;
import com.yallagoom.api.DiscoverCategoryAsyncTask;
import com.yallagoom.entity.Discover;
import com.yallagoom.interfaces.StringResultCallback;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewDiscoverByCategory extends RecyclerView.Adapter<RecycleViewDiscoverByCategory.MyViewHolder> {

    private final ArrayList<Discover.CategoryList> category_list;
    private final Discover.CountryData countryData;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView title;
        private final View _view;
        private final RelativeLayout top_layout;


        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            number = (TextView) view.findViewById(R.id.number);
            title = (TextView) view.findViewById(R.id.title);
            _view = (View) view.findViewById(R.id._view);
            top_layout = (RelativeLayout) view.findViewById(R.id.top_layout);

        }
    }


    public RecycleViewDiscoverByCategory(ArrayList<Discover.CategoryList> category_list, Discover.CountryData code_3) {
        this.category_list = category_list;
        this.countryData = code_3;

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
        holder.top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverCategoryAsyncTask discoverCategoryAsyncTask=new DiscoverCategoryAsyncTask(context, new StringResultCallback() {
                    @Override
                    public void processFinish(String result) {
                        Intent intent = new Intent(context, DiscoverCategoryActivity.class);
                        intent.putExtra("resultData",""+result);
                        intent.putExtra("countryDataEN",""+countryData.getName_en());
                        intent.putExtra("countryDataAR",""+countryData.getName_ar());
                        intent.putExtra("CategoryName",""+category_list.get(position).getCategoryName());
                        context.startActivity(intent);
                    }
                });
                discoverCategoryAsyncTask.execute(""+category_list.get(position).getId(), countryData.getCode_3());

            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}
