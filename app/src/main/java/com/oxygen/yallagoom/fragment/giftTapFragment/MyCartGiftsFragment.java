package com.oxygen.yallagoom.fragment.giftTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.gift.RecycleViewMyCart;
import com.oxygen.yallagoom.controller.RealmController;
import com.oxygen.yallagoom.entity.gift.MyCart;
import com.oxygen.yallagoom.interfaces.DefaultCallback;

import io.realm.Realm;


public class MyCartGiftsFragment extends Fragment implements DefaultCallback {


    private TextView header_title;
    private TextView no_msg;
    private Realm mRealm;
    private Realm realm;
    private RecyclerView my_cart_list;
    private RealmController realmController;
    private MyCart[] dataList;
    private RecycleViewMyCart recycleViewMyCart;

    public MyCartGiftsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gifts_my_cart, container, false);
        realm = Realm.getDefaultInstance();
        my_cart_list = (RecyclerView) view.findViewById(R.id.my_cart_list);
        realmController = new RealmController(realm);
        dataList = realmController.getMyCarts();
        recycleViewMyCart = new RecycleViewMyCart(dataList, realmController, this);
        my_cart_list.setAdapter(recycleViewMyCart);
        return view;

    }


    @Override
    public void processFinish() {
        dataList = realmController.getMyCarts();
        recycleViewMyCart = new RecycleViewMyCart(dataList, realmController, this);
        my_cart_list.setAdapter(recycleViewMyCart);
    }
}
