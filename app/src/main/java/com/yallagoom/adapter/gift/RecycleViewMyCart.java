package com.yallagoom.adapter.gift;


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
import com.yallagoom.R;
import com.yallagoom.controller.RealmController;
import com.yallagoom.entity.gift.FavoriteGifts;
import com.yallagoom.entity.gift.MyCart;
import com.yallagoom.interfaces.DefaultCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

;

public class RecycleViewMyCart extends RecyclerView.Adapter<RecycleViewMyCart.MyViewHolder> {

    private final MyCart[] myCarts;
    private final ImageLoader imageLoader;
    private final RealmController realmController;
    private final DefaultCallback defaultCallback;
    public Context context;

    public RecycleViewMyCart(MyCart[] myCarts, RealmController realmController, DefaultCallback defaultCallback) {
        this.myCarts = myCarts;
        this.realmController = realmController;
        this.defaultCallback = defaultCallback;
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
        if (myCarts[position].getImage() != null) {
            Log.e("imageUrl", "" + Constant.imageUrl + myCarts[position].getImage());
            ToolUtils.setImage(Constant.imageUrl + myCarts[position].getImage(), holder.gift_image, imageLoader);
        }
        holder.gift_title.setText(myCarts[position].getTitle());
        holder.gifts_price.setText(myCarts[position].getPrice() + " $");
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
        holder.heart_layout.setVisibility(View.GONE);

        holder.remove_cart.setText(context.getString(R.string.remove_cart));

        holder.remove_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmController.removeMyCart(myCarts[position].getId());
                defaultCallback.processFinish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCarts.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView gift_title;
        private final TextView gifts_price;
        private final TextView like_false;
        private final TextView like_true;
        private final TextView remove_cart;
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
            remove_cart = (TextView) itemView.findViewById(R.id.add_to_card);
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