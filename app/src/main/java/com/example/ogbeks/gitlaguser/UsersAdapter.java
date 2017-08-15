package com.example.ogbeks.gitlaguser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ogbeks on 8/14/2017.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_github_java_developer, parent, false);
        }
        User user = getItem(position);
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.git_username_list);
        usernameTextView.setText(user.getUsername());

        ImageView userImageView = (ImageView) listItemView.findViewById(R.id.git_user_img);
        if (user.hasImage()) {
            userImageView.setImageResource(user.getUserImageUri());
            userImageView.setVisibility(View.VISIBLE);
        } else {
            userImageView.setVisibility(View.GONE);
        }


        return listItemView;
    }
}
