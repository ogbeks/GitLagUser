package com.example.ogbeks.gitlaguser;

import android.text.TextUtils;
import android.util.Log;

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

/**
 * Created by ogbeks on 8/20/2017.
 */

public final class QueryUtils {
    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Query the GitHub dataset and return an {@link ArrayList<User>} object to represent all the java developer in Lagos.
     */
    public static ArrayList<User> fetchGitUserData(String requestUrl) {

        //Create a URL link from the string param
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link ArrayList<User>} object
        ArrayList<User> users = extractFeatureFromJson(jsonResponse);

        // Return the {@link ArrayList<User>}
        return users;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
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
            urlConnection.setReadTimeout(30000 /* milliseconds */);
            urlConnection.setConnectTimeout(30000 /* milliseconds */);

            urlConnection.connect();
            //If the request was successful(response code 200);
            //then read the input stream and parse the response
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
              //  Log.i("jsonResponse", jsonResponse);
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
       // Log.i("extractfromJson", jsonResponse);
        return jsonResponse;

    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
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
    private static ArrayList<User> extractFeatureFromJson(String gitHubUserJson) {
        //If the JSON string is empty or null, then return early
       // Log.v("githubusersJson", gitHubUserJson);
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
                    gitUsers.add(new User(username,userImageUrl,userGitHubUrl));
                }
                // Create a new {@link Event} object
                return gitUsers;

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the gitHub user JSON results", e);
        }
        return null;
    }

}


