package com.oxygen.yallagoom.fragment.giftTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.gift.RecycleViewFavourite;
import com.oxygen.yallagoom.controller.RealmController;
import com.oxygen.yallagoom.entity.gift.FavoriteGifts;
import com.oxygen.yallagoom.interfaces.DefaultCallback;

import io.realm.Realm;


public class FavouriteGiftsFragment extends Fragment implements DefaultCallback {


    private TextView header_title;
    private TextView no_msg;
    private Realm mRealm;
    private Realm realm;
    private RecyclerView favourite_list;
    private RecycleViewFavourite recycleViewAllGifts;
    private FavoriteGifts[] dataList;
    private RealmController realmController;

    public FavouriteGiftsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gifts_favourite, container, false);
        realm = Realm.getDefaultInstance();
        favourite_list = (RecyclerView) view.findViewById(R.id.favourite_list);
        realmController = new RealmController(realm);

        //Gift[] resultArray = realmController.getFavoriteGifts().toArray(new Gift[realmController.getFavoriteGifts().size()]);
        dataList = realmController.getFavoriteGifts();
        recycleViewAllGifts = new RecycleViewFavourite(dataList, realmController, this);
        favourite_list.setAdapter(recycleViewAllGifts);
        return view;

    }


    @Override
    public void processFinish() {
        dataList = realmController.getFavoriteGifts();
        recycleViewAllGifts = new RecycleViewFavourite(dataList, realmController, this);
        favourite_list.setAdapter(recycleViewAllGifts);
    }
}
