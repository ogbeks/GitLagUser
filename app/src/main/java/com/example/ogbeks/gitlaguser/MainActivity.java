package com.example.ogbeks.gitlaguser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    LinearLayout layout;
    final ArrayList<User> users = new ArrayList<User>();
    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    /** URL to query the GitHub database for Java developer Users in lagos  information */
    private static final String GITHUB_REQUEST_URL ="https://api.github.com/search/users?q=location:lagos+language:java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview_users);
        layout = (LinearLayout) findViewById(R.id.progressbar_view);
        // Kick off an {@link AsyncTask} to perform the network request
        GitUserAsyncTask task = new GitUserAsyncTask();
        task.execute();

    }
    private void updateUri(){
        // Create an {@link UserAdapter}, whose data source is a list of
        // {@link User}s. The adapter knows how to create list item views for each item
        // in the list.
        UsersAdapter usersAdapter = new UsersAdapter(this, users);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView userListView = (ListView) findViewById(R.id.listview_users);
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
    private class GitUserAsyncTask extends AsyncTask<URL, Void, ArrayList<User>>
    {
        @Override
        protected void onPreExecute() {

            layout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

            super.onPreExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(URL... params) {
            // Create URL object
            URL url = createUrl(GITHUB_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.e(LOG_TAG,"Url link invalid",e);
            }
            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<User> gitUsers = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result for the {@link TsunamiAsyncTask}
            return gitUsers;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(final ArrayList<User> users) {
            if(users == null){
                return;
            }
            layout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            // Create an {@link UserAdapter}, whose data source is a list of
            // {@link User}s. The adapter knows how to create list item views for each item
            // in the list.
            UsersAdapter usersAdapter = new UsersAdapter(MainActivity.this, users);
            // Get a reference to the ListView, and attach the adapter to the listView.
            ListView userListView = (ListView) findViewById(R.id.listview_users);
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

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            // If the Url is null, then return early
            if(url == null)
            {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);

                urlConnection.connect();
                //If the request was successful(response code 200);
                //then read the input stream and parse the response
                if(urlConnection.getResponseCode()==200){
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                    Log.i("jsonResponse", jsonResponse);
                }
                else{
                    Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e(LOG_TAG, "Problem retrieving the GitHub JSON results",e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            Log.i("extractfromJson", jsonResponse);
            return jsonResponse;

        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            //this create the string builder which is mutable
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
        /**
         * Return an {@link User} object by parsing out information
         * about the first earthquake from the input earthquakeJSON string.
         */
        private ArrayList<User> extractFeatureFromJson(String gitHubUserJson) {
            //If the JSON string is empty or null, then return early
            Log.v("githubusersJson", gitHubUserJson);
            if(TextUtils.isEmpty(gitHubUserJson)){
                return null;
            }
            try {
                JSONObject baseJsonResponse = new JSONObject(gitHubUserJson);
                JSONArray jsonArray = baseJsonResponse.getJSONArray("items");
                ArrayList<User> gitUsers = new ArrayList<User>();
                // If there are results in the features array
                if (jsonArray.length() > 0) {
                    // Extract out the first feature (which is an earthquake)
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //Extract out the username, user url, and image url
                        String username = jsonObject.getString("login");
                        String userGitHubUrl = jsonObject.getString("html_url");
                        String userImageUrl = jsonObject.getString("avatar_url");
                        Log.v("login", username);
                        Log.v("html_url", userGitHubUrl);

                        gitUsers.add(new User(username,userImageUrl,userGitHubUrl));
                    }
                    Log.v("Users", gitUsers.get(1).toString());
                    // Create a new {@link Event} object
                    return gitUsers;

                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the gitHub user JSON results", e);
            }
            return null;
        }

    }

}
