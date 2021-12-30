package com.example.dailytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ImageView imgSchedule;
    private ImageView imgActivity;
    private FloatingActionButton addSchedule;
    AlertDialog.Builder dialog;
    String userID = "";
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       imgSchedule = findViewById(R.id.img_icSchedule);
       imgActivity = findViewById(R.id.img_icActivity);
       addSchedule = findViewById(R.id.add_schedule);

       //get current username
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

        myRef.child(userID).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
                username = value;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, AddScheduleActivity.class));
//                -->dipindah ke btn addschedule

                // Perintah Intent Explicit pindah halaman daftar schedule
                Intent i = new Intent(v.getContext(), ShowSchedules.class);
                i.putExtra("userID", userID);
                startActivity(i);
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

//               dialog pilih kategori
               dialogCategory();

           }
       });

    }

    private void dialogCategory() {

        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Daily Task");
        dialog.setMessage("Select Category");

        dialog.setNegativeButton("Schedule", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                intent ke halaman add schedule
                Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

        dialog.setPositiveButton("Activity", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                intent ke halaman add activity
                Intent intent = new Intent(MainActivity.this, AddActivities.class);
                startActivity(intent);
            }
        });

        dialog.show();

    }
}

