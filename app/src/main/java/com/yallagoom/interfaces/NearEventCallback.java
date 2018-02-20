package com.yallagoom.interfaces;

import com.yallagoom.entity.Event;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface NearEventCallback {
    void processFinish(Event nearEvent);

}
