package com.yallagoom.entity.Matches;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/11/2018.
 */

public class LeagueMatches {

    private String leagueName;
   // private ArrayList<MatchesList> matchesLists=new ArrayList<>() ;
    private MatchesList[] matchesLists2=new MatchesList[0];

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }


    public MatchesList[] getMatchesLists2() {
        return matchesLists2;
    }

    public void setMatchesLists2(MatchesList[] matchesLists2) {
        this.matchesLists2 = matchesLists2;
    }
}