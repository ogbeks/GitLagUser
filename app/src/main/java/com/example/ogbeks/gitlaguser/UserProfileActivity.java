package com.example.ogbeks.gitlaguser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private String username;
    private String userGitHubUrl;
    private String userImageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        //Get the data parse with the intent that load this activity for the user.
        //note the String name matches the name on the MainActivity where it was called
       username= getIntent().getStringExtra("username");
       userGitHubUrl = getIntent().getStringExtra("userGitHubUrl");
        //This required the name and a default integer
       userImageResourceId = getIntent().getStringExtra("userImageResourceId");

        Log.v("UserProfileActivity", "username: "+username);
        // Find the TextView in the user_profile.xml layout with the ID username_profile
        TextView usernameTextView = (TextView) findViewById(R.id.username_profile);
        // Get the version name from the current User object and
        // set this text on the name TextView
        usernameTextView.setText(username);

        // Find the TextView in the user_profile.xml layout with the ID user_github_url_profile
        TextView userGitHubUrlTextView =(TextView) findViewById(R.id.user_github_url_profile);
        // Get the version GitHub url from the current User object and
        // set this text on the name TextView
        userGitHubUrlTextView.setText(userGitHubUrl);

        // Find the ImageView in the user_profile.xml layout with the ID user_profile_img
        ImageView userImageView = (ImageView) findViewById(R.id.user_profile_img);
        // Get the image resource ID from the current User object and
        // set the image to ImageView
        Picasso.with(UserProfileActivity.this)
                .load(userImageResourceId)
                .into(userImageView);

        //This is a ClickListener for the user GitHub url textView that will create an instance of
        // a browser intent to open a browser to view the user profile
        userGitHubUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!userGitHubUrl.startsWith("http://")) && (!userGitHubUrl.startsWith("https://"))){
                    userGitHubUrl="http://"+ userGitHubUrl;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(userGitHubUrl));
                startActivity(browserIntent);
            }
        });
    }
    
    //This is a method for the OnClick Button on the user_profile.xml
    //This create an Intent that share the users profile
    public void shareUserProfile(View v){
        Intent shareProfileIntent = new Intent(Intent.ACTION_SEND);
        shareProfileIntent.setType("text/plain");
        shareProfileIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"+username +", "+ userGitHubUrl+ ".");

        if(shareProfileIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareProfileIntent);
        }

    }
}
