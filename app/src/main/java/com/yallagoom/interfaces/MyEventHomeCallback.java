package com.yallagoom.interfaces;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.entity.Event;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface MyEventHomeCallback {
    void processFinish(Event myEvent, Event upcomingEvent , KProgressHUD kProgressHUD);

}
