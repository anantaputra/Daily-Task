package com.example.dailytask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    private ImageView imgSchedule;
    private ImageView imgActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       @SuppressLint("WrongViewCast")
       ImageView imgSchedule = findViewById(R.id.img_icSchedule);
       ImageView imgActivity = findViewById(R.id.img_icActivity);

        imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Perintah Intent Explicit pindah halaman
                startActivity(new Intent(MainActivity2.this, MainActivity3.class));
            }
        });

       imgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Perintah Intent Explicit pindah halaman
                startActivity(new Intent(MainActivity2.this, MainActivity4.class));
            }
        });

    }
}

