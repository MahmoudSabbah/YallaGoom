package com.oxygen.yallagoom.entity;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.oxygen.yallagoom.entity.Matches.LeagueMatches;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/11/2018.
 */

public class MyDiffCallback extends DiffUtil.Callback{

    ArrayList<LeagueMatches> oldLeagueMatchesList;
    ArrayList<LeagueMatches> newLeagueMatchesList;

    public MyDiffCallback( ArrayList<LeagueMatches> newLeagueMatchesList,   ArrayList<LeagueMatches> oldLeagueMatchesList) {
        this.newLeagueMatchesList = newLeagueMatchesList;
        this.oldLeagueMatchesList = oldLeagueMatchesList;
    }

    @Override
    public int getOldListSize() {
        return oldLeagueMatchesList.size();
    }

    @Override
    public int getNewListSize() {
        return newLeagueMatchesList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return true;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}