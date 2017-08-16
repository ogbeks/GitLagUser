package com.example.ogbeks.gitlaguser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a fake list of github users;
        final ArrayList<User> users = new ArrayList<User>();
        users.add(new User("Ogbeks", R.drawable.my_pic, "http://www.github.com/ogbeks"));
        users.add(new User("UnicodeDeveloper", R.drawable.dummy_user_pic_web, "http://www.github.com/unicodedeveloper"));
        users.add(new User("sauban", R.drawable.dummy_user_pic_web, "http://www.github.com/sauban"));
        users.add(new User("mubanise", R.drawable.dummy_user_pic_web, "http://www.github.com/mubanise"));

        // Create an {@link UserAdapter}, whose data source is a list of
        // {@link User}s. The adapter knows how to create list item views for each item
        // in the list.
        UsersAdapter usersAdapter = new UsersAdapter(this, users);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView userListView = (ListView) findViewById(R.id.listview_users);
        userListView.setAdapter(usersAdapter);


        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);

                Intent userProfile = new Intent(MainActivity.this, UserProfileActivity.class);

                Log.v("MainActivity", user.toString());
                userProfile.putExtra("username", user.getUsername());
                userProfile.putExtra("userImageResourceId", user.getUserImageUri());
                userProfile.putExtra("userGitHubUrl", user.getUserGitHubUrl());

                startActivity(userProfile);
            }

        });
    }

}
