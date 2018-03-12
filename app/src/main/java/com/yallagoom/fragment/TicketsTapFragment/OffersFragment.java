package com.yallagoom.fragment.TicketsTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewOfferFragment;
import com.yallagoom.api.OfferFragmentDataAsyncTask;
import com.yallagoom.entity.TicketClasses.TicketFullData;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.utils.Constant;


public class OffersFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private RecyclerView offers_list;
    private SmartRefreshLayout refreshLayout;
    private TicketFullData resultData;
    private RecycleViewOfferFragment recycleViewOfferFragment;

    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        offers_list = (RecyclerView) view.findViewById(R.id.offers_list);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData();
            }
        });

        return view;

    }

    private void getData() {
        OfferFragmentDataAsyncTask offerFragmentDataAsyncTask = new OfferFragmentDataAsyncTask(OffersFragment.this.getContext(), new StringResultCallback() {
            @Override
            public void processFinish(String result) {
                resultData = new Gson().fromJson(result, TicketFullData.class);
                recycleViewOfferFragment = new RecycleViewOfferFragment(resultData.getData());
                offers_list.setAdapter(recycleViewOfferFragment);
                refreshLayout.finishRefresh();
            }
        });
        offerFragmentDataAsyncTask.execute(Constant.alpha3Country);
    }

}
