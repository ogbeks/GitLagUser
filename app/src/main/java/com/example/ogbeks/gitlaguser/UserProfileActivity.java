package com.example.ogbeks.gitlaguser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private String username;
    private String userGitHubUrl;
    private int userImageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

       username= getIntent().getStringExtra("username");
       userGitHubUrl = getIntent().getStringExtra("userGitHubUrl");
       userImageResourceId = getIntent().getIntExtra("userImageResourceId", -1);

        Log.v("UserProfileActivity", "username: "+username);
        TextView usernameTextView = (TextView) findViewById(R.id.username_profile);
        usernameTextView.setText(username);

        TextView userGitHubUrlTextView =(TextView) findViewById(R.id.user_github_url_profile);
        userGitHubUrlTextView.setText(userGitHubUrl);

        ImageView userImageView = (ImageView) findViewById(R.id.user_profile_img);
        userImageView.setImageResource(userImageResourceId);

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
    public void shareUserProfile(View v){
        Intent shareProfileIntent = new Intent(Intent.ACTION_SEND);
        shareProfileIntent.setType("text/plain");
        shareProfileIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer @"+username +", "+ userGitHubUrl+ ".");

        if(shareProfileIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareProfileIntent);
        }

    }
}
