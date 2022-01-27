package Frament;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mainps.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentMainActivity extends AppCompatActivity {

    BottomNavigationView btn_Nav;
    BookmarkFragment bookmarkFragment;
    creator_main_Fragment creatormainFragment;
    creator_result_Fragment creator_result_fragment;
    creator_ranking_fragment creator_ranking_fragment;



    int go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_fragment_main);

        btn_Nav=findViewById(R.id.btn_Nav_artist);

        creator_result_fragment = new creator_result_Fragment();
        bookmarkFragment = new BookmarkFragment();
        creatormainFragment =new creator_main_Fragment();
        creator_ranking_fragment =new creator_ranking_fragment();

        go = getIntent().getIntExtra("aaaaaa",0);
        Log.e("asdf", String.valueOf(go));

        if (go == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, creator_result_fragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, creatormainFragment).commit();
        }
        btn_Nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.item_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, creatormainFragment).commit();
                }else if (item.getItemId()==R.id.item_bm) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, bookmarkFragment).commit();
                }else if (item.getItemId()==R.id.item_ranking){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, creator_ranking_fragment).commit();
                }

                return true;
            }
        });


    }
}
