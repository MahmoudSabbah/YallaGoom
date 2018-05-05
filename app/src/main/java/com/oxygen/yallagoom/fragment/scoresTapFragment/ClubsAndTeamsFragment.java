package com.oxygen.yallagoom.fragment.scoresTapFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.adapter.RecycleViewClubsAndTeams;
import com.oxygen.yallagoom.api.GetClubAndTeamsDetailsApiAsyncTask;
import com.oxygen.yallagoom.entity.Matches.ClubsAndTeams;
import com.oxygen.yallagoom.interfaces.StringResultCallback;
import com.oxygen.yallagoom.widget.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;


public class ClubsAndTeamsFragment extends Fragment {
    private RecyclerView channels_list;
    private IndexFastScrollRecyclerView clubs_list;
    private RecycleViewClubsAndTeams recycleViewClubsAndTeams;

    public ClubsAndTeamsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clubs_teams, container, false);
        clubs_list = (IndexFastScrollRecyclerView) view.findViewById(R.id.clubs_list);
        clubs_list.setIndexBarTransparentValue(0);
        clubs_list.setIndexBarTextColor("#6b6a6a");

        getData();
        return view;

    }

    private void getData() {
        GetClubAndTeamsDetailsApiAsyncTask getClubAndTeamsDetailsApiAsyncTask = new GetClubAndTeamsDetailsApiAsyncTask(ClubsAndTeamsFragment.this.getActivity(), new StringResultCallback() {
            @Override
            public void processFinish(String result, KProgressHUD progress) {
                ClubsAndTeams[] clubsAndTeams = new Gson().fromJson(result, ClubsAndTeams[].class);
                recycleViewClubsAndTeams = new RecycleViewClubsAndTeams(ClubsAndTeamsFragment.this.getActivity(), clubsAndTeams);
                clubs_list.setAdapter(recycleViewClubsAndTeams);
            }
        });
        getClubAndTeamsDetailsApiAsyncTask.execute("-1");
    }


}
