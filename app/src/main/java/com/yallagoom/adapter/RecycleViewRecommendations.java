package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.entity.Discover;
import com.yallagoom.entity.Event;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewRecommendations extends RecyclerView.Adapter<RecycleViewRecommendations.MyViewHolder> {

    private final ArrayList<Discover.Recommended.Data> data;
    private final ImageLoader imageLoader;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView recom_title;
        private final TextView like_value;
        private final TextView time;
        private final TextView cost;
        private final SelectableRoundedImageView recomm_image;
        private final View view_;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            recom_title = (TextView) view.findViewById(R.id.recom_title);
            like_value = (TextView) view.findViewById(R.id.like_value);
            time = (TextView) view.findViewById(R.id.time);
            cost = (TextView) view.findViewById(R.id.cost);
            view_ = (View) view.findViewById(R.id.view);
            recomm_image = (SelectableRoundedImageView) view.findViewById(R.id.recomm_image);

        }
    }


    public RecycleViewRecommendations(ArrayList<Discover.Recommended.Data> data) {
        this.data = data;
        imageLoader= ImageLoader.getInstance();

    }

    @Override
    public RecycleViewRecommendations.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recommendations, parent, false);

        return new RecycleViewRecommendations.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewRecommendations.MyViewHolder holder, final int position) {
        if (position == data.size() - 1) {
            holder.view_.setVisibility(View.GONE);
        }
        if (data.get(position).getImg_url()!=null){
            ToolUtils.setImage(Constant.imageUrl+data.get(position).getImg_url(),holder.recomm_image,imageLoader);

        }
        holder.recom_title.setText(data.get(position).getTitle());
        if (data.get(position).getTime()!=null){
            holder.time.setText(ToolUtils.convert24TimeTo12(data.get(position).getTime()));
        }
        holder.cost.setText("$ "+data.get(position).getPrice());

    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
