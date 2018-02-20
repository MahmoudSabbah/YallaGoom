package com.yallagoom.interfaces;


import com.yallagoom.entity.Event;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface ClickFindEventCallback {
    void processFinish(ArrayList<Event.DataEvent> selectEvents);

}
