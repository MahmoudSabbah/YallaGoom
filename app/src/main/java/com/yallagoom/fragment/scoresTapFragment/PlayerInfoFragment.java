package com.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.adapter.RecycleViewClubsAndTeams;
import com.yallagoom.adapter.RecycleViewPlayerInfo;
import com.yallagoom.api.GetPlayersApiAsyncTask;
import com.yallagoom.entity.Matches.Player.PlayerList;
import com.yallagoom.interfaces.StringResultCallback;
import com.yallagoom.widget.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;


public class PlayerInfoFragment extends Fragment {
    private RecyclerView channels_list;
    private IndexFastScrollRecyclerView player_list;
    private RecycleViewClubsAndTeams recycleViewClubsAndTeams;
    private RecycleViewPlayerInfo recycleViewPlayerInfo;

    public PlayerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_info, container, false);
        player_list = (IndexFastScrollRecyclerView) view.findViewById(R.id.player_list);
        player_list.setIndexBarTransparentValue(0);
        player_list.setIndexBarTextColor("#6b6a6a");

        getData();
        return view;

    }

    private void getData() {
        GetPlayersApiAsyncTask getPlayersApiAsyncTask = new GetPlayersApiAsyncTask(PlayerInfoFragment.this.getActivity(),-1, new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                PlayerList[] playerInfo = new Gson().fromJson(result, PlayerList[].class);
                recycleViewPlayerInfo = new RecycleViewPlayerInfo(PlayerInfoFragment.this.getActivity(), playerInfo);
                player_list.setAdapter(recycleViewPlayerInfo);
            }
        });
        getPlayersApiAsyncTask.execute();
    }


}
