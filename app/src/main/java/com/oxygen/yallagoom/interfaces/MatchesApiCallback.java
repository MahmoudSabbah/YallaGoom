package com.oxygen.yallagoom.interfaces;

import com.oxygen.yallagoom.entity.Matches.NewApi.FinalResultData_Data;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MatchesApiCallback {
    void processFinish(ArrayList<FinalResultData_Data> leagueMatchesList);

}
