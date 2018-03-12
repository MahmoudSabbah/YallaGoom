package com.yallagoom.interfaces;

import com.yallagoom.entity.Matches.LeagueMatchesList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MatchesApiCallback {
    void processFinish(LeagueMatchesList leagueMatchesList);

}
