package com.example.ogbeks.gitlaguser;

/**
 * Created by ogbeks on 8/14/2017.
 */

public class User {
    private String username;
    private int userImageUri;
    private String userGithubUrl;
    private int imageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public User(String name, int imgUri, String gitHubUrl) {
        username = name;
        userImageUri = imgUri;
        userGithubUrl = gitHubUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUserGithubUrl(String userGithubUrl) {
        this.userGithubUrl = userGithubUrl;
    }

    public void setUserImageUri(int userImageUri) {
        this.userImageUri = userImageUri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGithubUrl() {
        return userGithubUrl;
    }

    public int getUserImageUri() {
        return userImageUri;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
