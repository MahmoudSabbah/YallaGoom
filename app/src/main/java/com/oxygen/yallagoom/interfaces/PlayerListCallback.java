package com.oxygen.yallagoom.interfaces;

import com.oxygen.yallagoom.entity.event.Player;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface PlayerListCallback {
    void processFinish(ArrayList<Player.PlayerList> playerLists);

}
