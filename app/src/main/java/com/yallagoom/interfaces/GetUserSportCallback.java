package com.yallagoom.interfaces;

import com.yallagoom.entity.SportObject;

import java.util.Map;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface GetUserSportCallback {
    void processFinish(SportObject[] allSport, Map<Integer, Integer> userSportId);

}
