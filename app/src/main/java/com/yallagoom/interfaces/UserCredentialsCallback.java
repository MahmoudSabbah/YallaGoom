package com.yallagoom.interfaces;

import com.yallagoom.entity.Chat.UserCredentials;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface UserCredentialsCallback {
    void processFinish(ArrayList<UserCredentials> userCredentials);

}
