package com.example.dailytask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ImageView imgSchedule;
    private ImageView imgActivity;
    private FloatingActionButton addSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       imgSchedule = findViewById(R.id.img_icSchedule);
       imgActivity = findViewById(R.id.img_icActivity);
       addSchedule = findViewById(R.id.add_schedule);

        imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, AddScheduleActivity.class));
//                -->dipindah ke btn addschedule

                // Perintah Intent Explicit pindah halaman daftar schedule
                startActivity(new Intent(v.getContext(), ShowSchedules.class));
            }
        });

       imgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Perintah Intent Explicit pindah halaman
                startActivity(new Intent(MainActivity.this, ShowActivities.class));
            }
        });

       addSchedule.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

//               Intent ke activity tambah
               Intent i = new Intent(view.getContext(), AddScheduleActivity.class);
               startActivity(i);
           }
       });

    }
}

