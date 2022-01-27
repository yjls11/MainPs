package com.example.mainps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import Frament.FragmentMainActivity;


public class LoadingActivity extends AppCompatActivity {

    private  static  int SPLASH_SCREEN = 3500;

    private ImageView img_loading;
    private Animation anim;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        initView();


    }

    private void initView() {
        img_loading = (ImageView) findViewById(R.id.img_loading);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        img_loading.setAnimation(anim);


        new Handler().postDelayed(new Runnable() {
//            creator_result_Activity cr = new creator_result_Activity();
            @Override
            public void run() {
                intent =new Intent(getApplicationContext(), FragmentMainActivity.class);
                intent.putExtra("aaaaaa",1);
                startActivity(intent);
//                finish();

//
            }

//        },10000);}
    },SPLASH_SCREEN); }
}
