package com.example.mainps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashMainActivity extends AppCompatActivity {

    private  static  int SPLASH_SCREEN = 3000;

    Animation splashtop,splashbottom;
    ImageView logo_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_main);

        splashtop= AnimationUtils.loadAnimation(this,R.anim.splash_top);
        splashbottom= AnimationUtils.loadAnimation(this,R.anim.splash_bottom);

        logo_img=findViewById(R.id.logo_img);

        logo_img.setAnimation(splashtop);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashMainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}