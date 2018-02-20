package com.yallagoom.interfaces;

import com.yallagoom.entity.Player;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface PlayerListCallback {
    void processFinish(ArrayList<Player.PlayerList> playerLists);

}
