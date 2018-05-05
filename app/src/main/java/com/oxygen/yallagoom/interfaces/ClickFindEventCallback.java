package com.oxygen.yallagoom.interfaces;


import com.oxygen.yallagoom.entity.Event;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface ClickFindEventCallback {
    void processFinish(ArrayList<Event.DataEvent> selectEvents);

}
