package com.yallagoom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yallagoom.R;
import com.yallagoom.entity.TicketClasses.TicketDetails;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import io.realm.RealmResults;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewWishListFragment extends RecyclerView.Adapter<RecycleViewWishListFragment.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final RealmResults<TicketDetails> data;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView recom_title;
        private final TextView like_value;
        private final TextView time;
        private final TextView cost_after;
        private final TextView cost_befor;
        private final SelectableRoundedImageView recomm_image;
        private final View view_;
        private final LinearLayout parent;
        private final View view_before;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            recom_title = (TextView) view.findViewById(R.id.recom_title);
            like_value = (TextView) view.findViewById(R.id.like_value);
            time = (TextView) view.findViewById(R.id.time);
            cost_after = (TextView) view.findViewById(R.id.cost_after);
            cost_befor = (TextView) view.findViewById(R.id.cost_befor);
            view_ = (View) view.findViewById(R.id.view);
            view_before = (View) view.findViewById(R.id.view_before);
            parent = (LinearLayout) view.findViewById(R.id.parent);
            recomm_image = (SelectableRoundedImageView) view.findViewById(R.id.recomm_image);

        }
    }


    public RecycleViewWishListFragment(RealmResults<TicketDetails> data) {
        this.data = data;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecycleViewWishListFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_offers, parent, false);

        return new RecycleViewWishListFragment.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewWishListFragment.MyViewHolder holder, final int position) {
        if (position == data.size() - 1) {
            holder.view_.setVisibility(View.GONE);
        }
        if (data.get(position).getTicket_info().getImg_url() != null) {
            ToolUtils.setImage(Constant.imageUrl + data.get(position).getTicket_info().getImg_url(), holder.recomm_image, imageLoader);

        }
        holder.recom_title.setText(data.get(position).getTicket_info().getTitle());
        if (data.get(position).getTicket_info().getTime() != null) {
            holder.time.setText(ToolUtils.convert24TimeTo12(data.get(position).getTicket_info().getTime()));
        }
        if (data.get(position).getTicket_info().getPrice_after_discount() == 0) {
            holder.cost_after.setVisibility(View.GONE);
            holder.view_before.setVisibility(View.GONE);
        } else {
            holder.cost_after.setText("$ " + data.get(position).getTicket_info().getPrice_after_discount());
        }
        holder.cost_befor.setText("$ " + data.get(position).getTicket_info().getPrice() + "");

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  TicketDetailsAsyncTask ticketDetailsAsyncTask = new TicketDetailsAsyncTask(context, false, new TicketDeatailsCallback() {
                    @Override
                    public void processFinish(String result) {
                        Intent intent = new Intent(context, TicketsDetailsActivity.class);
                        intent.putExtra("TicketsDetails", "" + result);
                        context.startActivity(intent);
                    }
                });
                ticketDetailsAsyncTask.execute(data.get(position).getId() + "");*/
            }
        });
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
