package com.yallagoom.entity;

/**
 * Created by Mahmoud Sabbah on 4/12/2018.
 */

public class FirebaseUser {
    private String email;
    private String name;
    private String profilePicLink;

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
