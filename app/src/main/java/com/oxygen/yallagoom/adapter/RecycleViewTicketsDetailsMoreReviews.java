package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.TicketClasses.ReviewListDetails;

import io.realm.RealmList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewTicketsDetailsMoreReviews extends RecyclerView.Adapter<RecycleViewTicketsDetailsMoreReviews.MyViewHolder> {

    private final RealmList<ReviewListDetails> data;
    /*
        private final ArrayList<TicketDetails.ReviewList.Data> data;
    */
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final TextView reviewer_name;
        private final SimpleRatingBar rate_bar1_comment;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            rate_bar1_comment = (SimpleRatingBar) view.findViewById(R.id.rate_bar1_comment);
            description = (TextView) view.findViewById(R.id.description);
            reviewer_name = (TextView) view.findViewById(R.id.reviewer_name);
        }
    }

    public RecycleViewTicketsDetailsMoreReviews(RealmList<ReviewListDetails> data) {
     this.data = data;

    }

    @Override
    public RecycleViewTicketsDetailsMoreReviews.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);

        return new RecycleViewTicketsDetailsMoreReviews.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewTicketsDetailsMoreReviews.MyViewHolder holder, final int position) {
        holder.rate_bar1_comment.setRating((float) data.get(position).getRate());
        holder.description.setText(data.get(position).getTxt());
        holder.reviewer_name.setText(data.get(position).getUser().getFirst_name()+" "+data.get(position).getUser().getLast_name()+" from "+
                data.get(position).getUser().getGet_country_data().getName_en());
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
