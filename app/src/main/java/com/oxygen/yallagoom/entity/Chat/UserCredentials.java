package com.oxygen.yallagoom.entity.Chat;

import java.io.Serializable;

/**
 * Created by Mahmoud Sabbah on 4/15/2018.
 */

public class UserCredentials implements Serializable{
    private String email;
    private String name;
    private String profilePicLink;
    private String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }
}
