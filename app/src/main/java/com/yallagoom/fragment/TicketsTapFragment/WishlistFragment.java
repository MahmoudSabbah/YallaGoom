package com.yallagoom.fragment.TicketsTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewWishListFragment;
import com.yallagoom.controller.RealmController;

import io.realm.Realm;


public class WishlistFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private Realm mRealm;
    private Realm realm;
    private RecyclerView wishlist_list;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        realm = Realm.getDefaultInstance();
        wishlist_list = (RecyclerView) view.findViewById(R.id.wishlist_list);

        RealmController realmController = new RealmController(realm);
        Log.e("getTicketDetails2", "" + realmController.getTicketInfo().size());
        RecycleViewWishListFragment recycleViewWishListFragment = new RecycleViewWishListFragment(realmController.getTicketInfo()
        );
        wishlist_list.setAdapter(recycleViewWishListFragment);
        return view;

    }


}
