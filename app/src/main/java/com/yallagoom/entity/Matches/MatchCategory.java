package com.yallagoom.entity.Matches;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/19/2018.
 */

public class MatchCategory {
    private ArrayList<MatchCategoryItem> international;
    private ArrayList<MatchCategoryItem> countries;

    public ArrayList<MatchCategoryItem> getInternational() {
        return international;
    }

    public void setInternational(ArrayList<MatchCategoryItem> international) {
        this.international = international;
    }

    public ArrayList<MatchCategoryItem> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<MatchCategoryItem> countries) {
        this.countries = countries;
    }
}
