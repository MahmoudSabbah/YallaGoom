package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.TicketsDetailsActivity;
import com.oxygen.yallagoom.api.ticket.LikeTicketAsyncTask;
import com.oxygen.yallagoom.api.ticket.TicketDetailsAsyncTask;
import com.oxygen.yallagoom.app.MainApplication;
import com.oxygen.yallagoom.entity.TicketClasses.LikesContribution;
import com.oxygen.yallagoom.entity.TicketClasses.TicketInfo;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.interfaces.TicketDeatailsCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class RecycleViewRecommendations extends RecyclerView.Adapter<RecycleViewRecommendations.MyViewHolder> {

    private final ArrayList<TicketInfo> data;
    private final ImageLoader imageLoader;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView recom_title;
        private final TextView like_value;
        private final TextView sub_title;
        private final TextView cost;
        private final SelectableRoundedImageView recomm_image;
      //  private final View view_;
        private final LinearLayout parent;
        private final TextView heart;
        private final RelativeLayout like_lay;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            parent = (LinearLayout) view.findViewById(R.id.parent);
            recom_title = (TextView) view.findViewById(R.id.recom_title);
            like_value = (TextView) view.findViewById(R.id.like_value);
            sub_title = (TextView) view.findViewById(R.id.sub_title);
            cost = (TextView) view.findViewById(R.id.cost);
            heart = (TextView) view.findViewById(R.id.heart);
       //     view_ = (View) view.findViewById(R.id.view);
            recomm_image = (SelectableRoundedImageView) view.findViewById(R.id.recomm_image);
            like_lay = (RelativeLayout) view.findViewById(R.id.like);
        }
    }


    public RecycleViewRecommendations(ArrayList<TicketInfo> data) {
        this.data = data;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public RecycleViewRecommendations.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recommendations, parent, false);

        return new RecycleViewRecommendations.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleViewRecommendations.MyViewHolder holder, final int position) {
      /*  if (position == data.size() - 1) {
            holder.view_.setVisibility(View.GONE);
        }*/
        if (data.get(position).getImg_url() != null) {
            ToolUtils.setImage(Constant.imageUrl + data.get(position).getImg_url(), holder.recomm_image, imageLoader);

        }
        if (data.get(position).getTickets_likes_count().size() > 0) {
            holder.like_value.setText(data.get(position).getTickets_likes_count().get(0).getLikes_count() + " " + context.getString(R.string.like));
        } else {
            holder.like_value.setText("0 " + context.getString(R.string.like));
        }
        if (MainApplication.verification_check) {
            if (data.get(position).getMy_likes_contribution().size() > 0) {
                holder.heart.setTextColor(ContextCompat.getColor(context, R.color.color_df488a));
            } else {
                holder.heart.setTextColor(ContextCompat.getColor(context, R.color.gray_holo_light));

            }
        } else {
            holder.like_value.setVisibility(View.INVISIBLE);
            holder.heart.setVisibility(View.INVISIBLE);
        }
        holder.recom_title.setText(data.get(position).getTitle());
      //  holder.sub_title.setText(data.get(position).getSub_title());
      /*  if (data.get(position).getTime() != null) {
            holder.time.setText(ToolUtils.convert24TimeTo12(data.get(position).getTime()));
        }*/
        //  holder.cost.setText("$ " + data.get(position).getPrice());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketDetailsAsyncTask ticketDetailsAsyncTask = new TicketDetailsAsyncTask(context, false, new TicketDeatailsCallback() {
                    @Override
                    public void processFinish(String result) {
                        Intent intent = new Intent(context, TicketsDetailsActivity.class);
                        intent.putExtra("TicketsDetails", "" + result);
                        context.startActivity(intent);
                    }
                });
                ticketDetailsAsyncTask.execute(data.get(position).getId() + "");
            }
        });
        holder.like_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getMy_likes_contribution().size() > 0) {
                    LikeTicketAsyncTask likeTicketAsyncTask = new LikeTicketAsyncTask(context, new StringResultCallback() {
                        @Override
                        public void processFinish(String result, KProgressHUD kProgressHUD) {
                            holder.heart.setTextColor(ContextCompat.getColor(context, R.color.gray_holo_light));
                            data.get(position).getMy_likes_contribution().clear();
                            data.get(position).getTickets_likes_count().get(0).setLikes_count(data.get(position).getTickets_likes_count().get(0).getLikes_count() - 1);

                            holder.like_value.setText((data.get(position).getTickets_likes_count().get(0).getLikes_count()) + " " + context.getString(R.string.like));

                        }
                    });
                    likeTicketAsyncTask.execute(data.get(position).getId() + "", "dis");
                } else {
                    LikeTicketAsyncTask likeTicketAsyncTask = new LikeTicketAsyncTask(context, new StringResultCallback() {
                        @Override
                        public void processFinish(String result, KProgressHUD kProgressHUD) {
                            holder.heart.setTextColor(ContextCompat.getColor(context, R.color.color_df488a));
                            LikesContribution likesContribution = new LikesContribution();
                            likesContribution.setTicket_id(Integer.parseInt(result));
                            data.get(position).getMy_likes_contribution().add(likesContribution);
                            data.get(position).getTickets_likes_count().get(0).setLikes_count(data.get(position).getTickets_likes_count().get(0).getLikes_count() + 1);
                            holder.like_value.setText((data.get(position).getTickets_likes_count().get(0).getLikes_count()) + " " + context.getString(R.string.like));

                        }
                    });
                    likeTicketAsyncTask.execute(data.get(position).getId() + "", "like");
                }
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
