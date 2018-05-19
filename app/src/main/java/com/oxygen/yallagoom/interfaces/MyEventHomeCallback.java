package com.oxygen.yallagoom.interfaces;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.oxygen.yallagoom.entity.event.Event;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MyEventHomeCallback {
    void processFinish(Event myEvent, Event upcomingEvent , KProgressHUD kProgressHUD);

}
