package com.yallagoom.entity.Matches;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/19/2018.
 */

public class ClubCompetitionList {
    private ArrayList<CompetitionSettings> compitetions;
    private ArrayList<ClubsSettings> clubs;

    public ArrayList<CompetitionSettings> getCompitetions() {
        return compitetions;
    }

    public void setCompitetions(ArrayList<CompetitionSettings> compitetions) {
        this.compitetions = compitetions;
    }

    public ArrayList<ClubsSettings> getClubs() {
        return clubs;
    }

    public void setClubs(ArrayList<ClubsSettings> clubs) {
        this.clubs = clubs;
    }
}
