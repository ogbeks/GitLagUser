package com.example.ogbeks.gitlaguser;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ogbeks on 8/27/2017.
 */

public class UserLoader extends AsyncTaskLoader<ArrayList<User>> {

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link UserLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<User> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of users.
        ArrayList<User> users = QueryUtils.fetchGitUserData(mUrl);
        return users;
    }

}
