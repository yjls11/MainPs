package Frament;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mainps.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fragment_artist_main_Activity extends AppCompatActivity {


    BottomNavigationView btn_Nav_artist;
    artist_upload_Fragment artist_upload_fragment;
    Fragment_artist_Space fragment_artist_space;
    creator_ranking_fragment creator_ranking_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_artist_main_);

        btn_Nav_artist = findViewById(R.id.btn_Nav_artist);
        artist_upload_fragment = new artist_upload_Fragment();
        fragment_artist_space =new Fragment_artist_Space();
        creator_ranking_fragment =new creator_ranking_fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.artist_frame, artist_upload_fragment).commit();

        btn_Nav_artist.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.artist_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.artist_frame, artist_upload_fragment).commit();
                }else if (item.getItemId()==R.id.artist_Storage){
                    getSupportFragmentManager().beginTransaction().replace(R.id.artist_frame, fragment_artist_space).commit();
                }else if (item.getItemId()==R.id.artist_rank) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.artist_frame, creator_ranking_fragment).commit();
                }
                return true;
            }
        });
    }
}