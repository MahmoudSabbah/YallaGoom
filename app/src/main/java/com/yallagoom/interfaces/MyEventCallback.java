package com.yallagoom.interfaces;

import com.yallagoom.entity.Event;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MyEventCallback {
    void processFinish(Event.DataEvent event);

}
