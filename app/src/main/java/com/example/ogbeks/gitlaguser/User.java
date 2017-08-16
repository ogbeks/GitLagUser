package com.example.ogbeks.gitlaguser;

/**
 * Created by ogbeks on 8/14/2017.
 */

/**
 * {@link User} represents a single Android platform release.
 * Each object has 3 properties: username, user GitHub Url, and user Image Resource Id.
 */
public class User {
    // Name of the User
    private String username;

    //the user image resource id
    private int userImageUri;

    //the user GitHub profile url
    private String userGitHubUrl;

    /*
* Create a new User object.
*
* @param name is the name of the GitHub user (e.g. unicodedeveloper)
* @param imgUri is the resource id that corresponding to the user
* @param gitHubUrl is the GitHub url that corresponds to the i=user
* */
    public User(String name, int imgUri, String gitHubUrl) {
        username = name;
        userImageUri = imgUri;
        userGitHubUrl = gitHubUrl;
    }

    public User(String name, int imgUri) {
        username = name;
        userImageUri = imgUri;
    }

    /**
     * Get the name of the user
     */
    public String getUsername() {
        return username;
    }
    /**
     * Get the user GitHub url link
     */

    public String getUserGitHubUrl() {
        return userGitHubUrl;
    }

    /**
     * Get the image resource ID
     */
    public int getUserImageUri() {
        return userImageUri;
    }

    /**
     * This is a string value of the User class
     * This helps in the log of user data
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userImageUri=" + userImageUri +
                ", userGitHubUrl='" + userGitHubUrl + '\'' +
                '}';
    }
}
