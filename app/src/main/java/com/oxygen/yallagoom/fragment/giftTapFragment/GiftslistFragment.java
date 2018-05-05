package com.oxygen.yallagoom.fragment.giftTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.gift.RecycleViewAllGifts;
import com.oxygen.yallagoom.api.gift.GetGiftsAsyncTask;
import com.oxygen.yallagoom.controller.RealmController;
import com.oxygen.yallagoom.entity.gift.Gift;
import com.oxygen.yallagoom.interfaces.StringResultCallback;

import io.realm.Realm;


public class GiftslistFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private Realm mRealm;
    private Realm realm;
    private RecyclerView gifts_list;
    private SmartRefreshLayout refreshLayout;
    private Gift[] giftsList;
    private RecycleViewAllGifts recycleViewAllGifts;
    private RealmController realmController;
    private RelativeLayout no_data_layout;
    private ImageView image;

    public GiftslistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gift_list, container, false);
        realm = Realm.getDefaultInstance();
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        gifts_list = (RecyclerView) view.findViewById(R.id.gifts_list);
        no_data_layout = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(R.drawable.gift_de);
         realmController = new RealmController(realm);

        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getGiftData();

            }
        });

        return view;

    }

    private void getGiftData() {
        GetGiftsAsyncTask getGiftsAsyncTask = new GetGiftsAsyncTask(GiftslistFragment.this.getActivity(), new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                giftsList = new Gson().fromJson(result, Gift[].class);
                if (giftsList.length==0){
                    no_data_layout.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }else {
                    no_data_layout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                }
                recycleViewAllGifts = new RecycleViewAllGifts(giftsList,realmController);
                gifts_list.setAdapter(recycleViewAllGifts);
                refreshLayout.finishRefresh();
            }
        });
        getGiftsAsyncTask.execute();
    }


}
