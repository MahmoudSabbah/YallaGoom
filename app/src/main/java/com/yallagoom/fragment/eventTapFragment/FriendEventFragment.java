package com.yallagoom.fragment.eventTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewFriendsListEvent;
import com.yallagoom.api.SearchFriendsAsyncTask;
import com.yallagoom.entity.Player;
import com.yallagoom.interfaces.PlayerListCallback;
import com.yallagoom.interfaces.SearchFriendsCallback;

import java.util.ArrayList;



public class FriendEventFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private boolean auto = true;
    private RecyclerView alert_list;
    RecyclerView mRecyclerView;

    private int page = 1;
    private Player selectPlayerLists;
    private SmartRefreshLayout refreshLayout;
    private EditText search_bt;

    public FriendEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_event, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fast_scroller_recycler);
        search_bt = (EditText) view.findViewById(R.id.search_bt);
       // mRecyclerView.setIndexBarColor("#199899");
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        search_bt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("charSequence",""+charSequence+"");
                if (!(charSequence+"").equalsIgnoreCase("")){
                    refreshItems("filters", charSequence+"", "", -1, -1, -1, "", -1);
                }else {
                    refreshItems("default", "", "", -1, -1, -1, "", -1);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        refreshLayout.autoRefresh();
        refreshItems("default", "", "", -1, -1, -1, "", -1);

        return view;

    }

    private void refreshItems(String type, String player_name, String gender,
                              int country_id, int minage, int maxage, String rate, int sport_id) {
        SearchFriendsAsyncTask recycleViewFindPlayer = new SearchFriendsAsyncTask(FriendEventFragment.this.getActivity(), type, player_name,
                gender, country_id, minage, maxage, rate, sport_id, new SearchFriendsCallback() {
            @Override
            public void finishProcess(Player player) {
                selectPlayerLists = player;
                RecycleViewFriendsListEvent recycleViewFindPlayer = new RecycleViewFriendsListEvent(player, new PlayerListCallback() {
                    @Override
                    public void processFinish(ArrayList<Player.PlayerList> playerLists) {

                    }
                });
                mRecyclerView.setAdapter(recycleViewFindPlayer);
                onItemsLoadComplete();
            }
        });
        recycleViewFindPlayer.execute();


    }

    private void onItemsLoadComplete() {
        refreshLayout.finishRefresh();
    }

}
