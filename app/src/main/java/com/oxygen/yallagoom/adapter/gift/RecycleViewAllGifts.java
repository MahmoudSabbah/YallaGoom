package com.oxygen.yallagoom.adapter.gift;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.controller.RealmController;
import com.oxygen.yallagoom.entity.gift.FavoriteGifts;
import com.oxygen.yallagoom.entity.gift.Gift;
import com.oxygen.yallagoom.entity.gift.MyCart;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;
;

public class RecycleViewAllGifts extends RecyclerView.Adapter<RecycleViewAllGifts.MyViewHolder> {

    private final Gift[] giftsList;
    private final ImageLoader imageLoader;
    private final RealmController realmController;
    public Context context;

    public RecycleViewAllGifts(Gift[] giftsList, RealmController realmController) {
        this.giftsList = giftsList;
        this.realmController = realmController;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gifts, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (giftsList[position].getImage() != null) {
            Log.e("imageUrl", "" + Constant.imageUrl + giftsList[position].getImage());
            ToolUtils.setImage(Constant.imageUrl + giftsList[position].getImage(), holder.gift_image, imageLoader);
        }
        holder.gift_title.setText(giftsList[position].getTitle());
        holder.gifts_price.setText(giftsList[position].getPrice() + " $");
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(holder.count.getText().toString());
                value = value + 1;
                holder.count.setText((value) + "");
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(holder.count.getText().toString());
                if (value > 1) {
                    value = value - 1;
                    holder.count.setText((value) + "");
                }

            }
        });
        if (realmController.checkFavoriteGifts(giftsList[position].getId())) {
            holder.like_true.setVisibility(View.VISIBLE);
            holder.like_false.setVisibility(View.GONE);
        } else {
            holder.like_true.setVisibility(View.GONE);
            holder.like_false.setVisibility(View.VISIBLE);
        }
        holder.heart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (realmController.checkFavoriteGifts(giftsList[position].getId())) {
                    holder.like_true.setVisibility(View.GONE);
                    holder.like_false.setVisibility(View.VISIBLE);
                    realmController.removeFavoriteGifts(giftsList[position].getId());
                } else {
                    FavoriteGifts favoriteGifts = new FavoriteGifts(giftsList[position].getId(), giftsList[position].getTitle(),
                            giftsList[position].getDescription(), giftsList[position].getPrice(),
                            giftsList[position].getImage(), giftsList[position].getCreated_at());
                    holder.like_true.setVisibility(View.VISIBLE);
                    holder.like_false.setVisibility(View.GONE);
                    realmController.getRealm().beginTransaction();
                    realmController.getRealm().copyToRealmOrUpdate(favoriteGifts);
                    realmController.getRealm().commitTransaction();
                }
            }
        });
        if (realmController.checkMyCart(giftsList[position].getId())) {
            holder.add_to_card.setText(context.getString(R.string.added_to_cart));
        } else {
            holder.add_to_card.setText(context.getString(R.string.add_cart));
        }
        holder.add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (realmController.checkMyCart(giftsList[position].getId())) {
                    holder.add_to_card.setText(context.getString(R.string.add_cart));
                    realmController.removeMyCart(giftsList[position].getId());
                } else {
                    holder.add_to_card.setText(context.getString(R.string.added_to_cart));
                    MyCart myCart = new MyCart(giftsList[position].getId(), giftsList[position].getTitle(),
                            giftsList[position].getDescription(), giftsList[position].getPrice(),
                            giftsList[position].getImage(), giftsList[position].getCreated_at());
                    realmController.getRealm().beginTransaction();
                    realmController.getRealm().copyToRealmOrUpdate(myCart);
                    realmController.getRealm().commitTransaction();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return giftsList.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView gift_title;
        private final TextView gifts_price;
        private final TextView like_false;
        private final TextView like_true;
        private final TextView add_to_card;
        private final SelectableRoundedImageView gift_image;
        private final TextView count;
        private final FloatingActionButton increase;
        private final FloatingActionButton decrease;
        private final RelativeLayout heart_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            gift_image = (SelectableRoundedImageView) itemView.findViewById(R.id.gift_image);
            gift_title = (TextView) itemView.findViewById(R.id.gift_title);
            gifts_price = (TextView) itemView.findViewById(R.id.gifts_price);
            like_false = (TextView) itemView.findViewById(R.id.like_false);
            like_true = (TextView) itemView.findViewById(R.id.like_true);
            add_to_card = (TextView) itemView.findViewById(R.id.add_to_card);
            count = (TextView) itemView.findViewById(R.id.number);
            increase = (FloatingActionButton) itemView.findViewById(R.id.increase);
            decrease = (FloatingActionButton) itemView.findViewById(R.id.decrease);
            heart_layout = (RelativeLayout) itemView.findViewById(R.id.heart_layout);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}