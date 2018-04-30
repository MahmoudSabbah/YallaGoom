package com.yallagoom.entity.Chat;

import com.yallagoom.entity.UserCountry;

import java.io.Serializable;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class UserFirebase  {
  private  UserCredentials userCredentials;
  private  UserConversations userConversations;

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public UserConversations getUserConversations() {
        return userConversations;
    }

    public void setUserConversations(UserConversations userConversations) {
        this.userConversations = userConversations;
    }
}
