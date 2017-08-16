package com.example.ogbeks.gitlaguser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a fake list of github users;
        final ArrayList<User> users = new ArrayList<User>();
        users.add(new User("Ogbeks", R.drawable.my_pic));
        users.add(new User("UnicodeDeveloper", R.drawable.dummy_user_pic_web));
        users.add(new User("sauban", R.drawable.dummy_user_pic_web));
        users.add(new User("mubanise", R.drawable.dummy_user_pic_web));

        // Create an {@link UserAdapter}, whose data source is a list of
        // {@link User}s. The adapter knows how to create list item views for each item
        // in the list.
        UsersAdapter usersAdapter = new UsersAdapter(this, users);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView userListView = (ListView) findViewById(R.id.listview_users);
        userListView.setAdapter(usersAdapter);
    }

}
