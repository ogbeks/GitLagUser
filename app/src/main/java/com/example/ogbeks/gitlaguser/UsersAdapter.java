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
 *
*
* {@link UsersAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link User} objects.
* */
public class UsersAdapter extends ArrayAdapter<User> {

    /**
     * This is a custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param users A List of user objects to display in a list
     */
    public UsersAdapter(Context context, ArrayList<User> users) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for a TextView and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, users);
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.users_list, parent, false);
        }
        // Get the {@link User} object located at this position in the list
        User user = getItem(position);

        // Find the TextView in the users_list.xml layout with the ID git_username_list
        TextView usernameTextView = (TextView) listItemView.findViewById(R.id.git_username_list);
        // Get the version name from the current User object and
        // set this text on the name TextView
        usernameTextView.setText(user.getUsername());

        // Find the ImageView in the users_list.xml layout with the ID git_user_img
        ImageView userImageView = (ImageView) listItemView.findViewById(R.id.git_user_img_list);
        // Get the image resource ID from the current User object and
        // set the image to iconView
            userImageView.setImageResource(user.getUserImageUri());

        // Return the whole list item layout (containing a TextView and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
