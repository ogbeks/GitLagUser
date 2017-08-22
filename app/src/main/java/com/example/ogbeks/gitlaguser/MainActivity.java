package com.example.ogbeks.gitlaguser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView userListView;
    LinearLayout progressLayout;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    GitUserAsyncTask task = new GitUserAsyncTask();
    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    /** URL to query the GitHub database for Java developer Users in lagos  information */
    private static final String GITHUB_REQUEST_URL ="https://api.github.com/search/users?q=location:lagos+language:java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        userListView = (ListView) findViewById(R.id.listview_users);
        userListView.setEmptyView(mEmptyStateTextView);
        progressLayout = (LinearLayout) findViewById(R.id.progressbar_view);
        // Kick off an {@link AsyncTask} to perform the network request
        task.execute(GITHUB_REQUEST_URL);
    }
    private void updateUri(final ArrayList<User> users){
        // Create an {@link UserAdapter}, whose data source is a list of
        // {@link User}s. The adapter knows how to create list item views for each item
        // in the list.
        UsersAdapter usersAdapter = new UsersAdapter(this, users);
/*
        // Get a reference to the ListView, and attach the adapter to the listView.
        userListView = (ListView) findViewById(R.id.listview_users);*/
        userListView.setAdapter(usersAdapter);
        //Create an OnItemClickListener when a userListView child is clicked
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //This get the current users on the listview
                User user = users.get(position);
                //this create and explicit intent which receive two params, the current context,
                // and the activityClass for the intent
                Intent userProfile = new Intent(MainActivity.this, UserProfileActivity.class);
                Log.v("MainActivity", user.toString());

                //the putExtra method allows data to be parse together with the intent,
                //Here we pass the three @params for the next view : username, user GitHub url and user ImageResource
                userProfile.putExtra("username", user.getUsername());
                userProfile.putExtra("userImageResourceId", user.getUserImageUri());
                userProfile.putExtra("userGitHubUrl", user.getUserGitHubUrl());

                //The UserProfile activity is instantiated
                startActivity(userProfile);
            }

        });

    }
    private class GitUserAsyncTask extends AsyncTask<String, Void, ArrayList<User>>
    {
        @Override
        protected void onPreExecute() {

            progressLayout.setVisibility(View.VISIBLE);
            userListView.setVisibility(View.GONE);

            super.onPreExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<User> gitUsers = QueryUtils.fetchGitUserData(urls[0]);

            // Return the {@link Event} object as the result for the {@link TsunamiAsyncTask}
            return gitUsers;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            if(users == null){
                mEmptyStateTextView.setText(R.string.no_users);
            }
            progressLayout.setVisibility(View.GONE);
            userListView.setVisibility(View.VISIBLE);
            updateUri(users);
        }
    }

}
