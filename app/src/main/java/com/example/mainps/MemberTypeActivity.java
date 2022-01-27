package com.example.mainps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MemberTypeActivity extends AppCompatActivity {

    ImageButton btn_General,btn_artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_type);

        btn_General=findViewById(R.id.btn_General);
        btn_artist=findViewById(R.id.btn_artist);

        btn_General.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),joinActivity.class);
                intent.putExtra("URL",1);
                startActivity(intent);
            }
        });
        btn_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),joinActivity.class);
                startActivity(intent);
            }
        });
    }



}