package com.yallagoom.interfaces;

import com.yallagoom.entity.Country;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface GetPlaceCallback {
    void processFinish(List<HashMap<String, String>> data_place);

}
