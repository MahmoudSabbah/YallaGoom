package com.yallagoom.entity.Matches.NewApi;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 3/26/2018.
 */

public class FinalResultData_Data {
    private String start_date;
    private ArrayList<FinalResultData_Data_Data> data;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public ArrayList<FinalResultData_Data_Data> getData() {
        return data;
    }

    public void setData(ArrayList<FinalResultData_Data_Data> data) {
        this.data = data;
    }
}
