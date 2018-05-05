package com.oxygen.yallagoom.entity.Matches.NewApi;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/26/2018.
 */

public class FinalResultData_Data_Data {
    private String competition;
    private ArrayList<FinalResultData_Data_Data_Data> data;

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public ArrayList<FinalResultData_Data_Data_Data> getData() {
        return data;
    }

    public void setData(ArrayList<FinalResultData_Data_Data_Data> data) {
        this.data = data;
    }
}
