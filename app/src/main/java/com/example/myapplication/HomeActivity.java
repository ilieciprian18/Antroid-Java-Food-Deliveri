package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

public class HomeActivity extends YouTubeBaseActivity {

    private ImageView imageMcDonalds;
    private TextView textMcDonalds;

    YouTubePlayerView youTubePlayerView;

    String order = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        System.out.println(order);
        order = getIntent().getStringExtra("orderHome");
        System.out.println("Create order " + order);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //profile selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),UserProfileFinal.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(),ContactPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //youtube player

        youTubePlayerView = findViewById(R.id.youtubePlayer);
        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //dam load la video
                youTubePlayer.loadVideo("735FS4hsog0");
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"Failed to display add",Toast.LENGTH_SHORT).show();
            }
        };

       youTubePlayerView.initialize("AIzaSyD6lSgw3KoTphta202L-ve17Q81rf_JIUw",listener);



    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("START");
        System.out.println(order);
        order = getIntent().getStringExtra("orderHome");
        System.out.println("Start order " + order);
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("RESUME");
        System.out.println(order);
        order = getIntent().getStringExtra("orderHome");
        System.out.println("Resume order " + order);
    }

    public void imageClickMcDonalds(View view) {
        Intent i = new Intent(HomeActivity.this, McDonalds.class);
        startActivity(i);
    }

    public void imageClickKFC(View view) {
        Intent i = new Intent(HomeActivity.this, KFC.class);
        startActivity(i);
    }

    public void imageClickLaBunica(View view) {
        Intent i = new Intent(HomeActivity.this, LaBunica.class);
        startActivity(i);
    }
}














