package com.oxygen.yallagoom.interfaces;

import com.oxygen.yallagoom.entity.event.Event;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MyEventCallback {
    void processFinish(Event.DataEvent event);

}
