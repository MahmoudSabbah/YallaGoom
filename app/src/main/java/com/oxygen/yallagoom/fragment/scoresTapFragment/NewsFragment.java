package com.oxygen.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewNews;
import com.oxygen.yallagoom.api.NewsAsyncTask;
import com.oxygen.yallagoom.entity.News.News;
import com.oxygen.yallagoom.entity.News.News_data_feeds;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    private RecyclerView news_list;
    private SmartRefreshLayout refreshLayout;
    private RecycleViewNews recycleViewNews;
    private News resultClass;
    private int countStore = 0;
    private ArrayList<News_data_feeds> listOfData;
    private ArrayList<String> channel;
    private ArrayList<String> storeChannels;
    private boolean checkStoreChannel = false;
    private int countAll = 0;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.autoRefresh();
        news_list = (RecyclerView) view.findViewById(R.id.news_list);
        storeChannels = ToolUtils.getArrayOfChannels(NewsFragment.this.getActivity());
        if (storeChannels.size() > 0) {
            checkStoreChannel = true;
        } else {
            checkStoreChannel = false;
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(0);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(1);
            }
        });
        return view;

    }

    private void getData(final int flag) {

        final NewsAsyncTask newsAsyncTask = new NewsAsyncTask(NewsFragment.this.getContext(), new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                if (flag == 0) {
                    resultClass = new Gson().fromJson(result, News.class);
                    listOfData = resultClass.getData_feeds();
                    channel = new ArrayList<>();
                    for (int i = 0; i < listOfData.size(); i++) {
                        channel.add(resultClass.getChannel());
                    }
                    recycleViewNews = new RecycleViewNews(listOfData, channel);
                    news_list.setAdapter(recycleViewNews);
                    refreshLayout.finishRefresh();
                    if (checkStoreChannel) {
                        countStore++;
                    } else {
                        countAll++;
                    }

                } else {
                    resultClass = new Gson().fromJson(result, News.class);
                    listOfData.addAll(resultClass.getData_feeds());
                    for (int i = 0; i < resultClass.getData_feeds().size(); i++) {
                        channel.add(resultClass.getChannel());
                    }
                    recycleViewNews.notifyDataSetChanged();
                    refreshLayout.finishLoadmore();
                    if (checkStoreChannel) {
                        countStore++;
                    } else {
                        countAll++;
                    }
                }
            }
        });
        Log.e("checkStoreChannel",""+checkStoreChannel);


        if (checkStoreChannel) {
            if (countStore < storeChannels.size()) {
                newsAsyncTask.execute(storeChannels.get(countStore));
            } else {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }
        } else {
            if (countAll < Constant.ChannelsList.length) {
                newsAsyncTask.execute(Constant.ChannelsList[countAll].getId() + "");
            } else {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }
        }
    }

}
