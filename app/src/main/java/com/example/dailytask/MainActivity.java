package com.example.dailytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dailytask.adapter.OnGoingScheduleAdapter;
import com.example.dailytask.adapter.ScheduleAdapter;
import com.example.dailytask.addData.AddActivityActivity;
import com.example.dailytask.addData.AddScheduleActivity;
import com.example.dailytask.model.Schedule;
import com.example.dailytask.show.ShowActivities;
import com.example.dailytask.show.ShowSchedules;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgSchedule, imgActivity;
    private TextView schedule, activity;
    private FloatingActionButton add;
    private AlertDialog.Builder dialog;
    private OnGoingScheduleAdapter adapterSchedule;
    private RecyclerView recyclerViewSchedule, recyclerViewActivity;
    private ArrayList<Schedule> results;
    private String userID = "";
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ambil komponen berdasar id
        imgSchedule = findViewById(R.id.img_icSchedule);
        imgActivity = findViewById(R.id.img_icActivity);
        schedule    = findViewById(R.id.schedule_show);
        activity    = findViewById(R.id.activity_show);
        add         = findViewById(R.id.add);

        //hide schedule and activity category
        schedule.setVisibility(View.GONE);
        activity.setVisibility(View.GONE);

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

        //nambahin method onClick, biar tombolnya bisa diklik
        imgActivity.setOnClickListener(this);
        imgSchedule.setOnClickListener(this);
        add.setOnClickListener(this);

        //setup recyclerview schedule
        rvSchedule();
    }

    private void rvSchedule() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userID).child("schedule");

        recyclerViewSchedule = findViewById(R.id.rv_schedule);

        recyclerViewSchedule.setHasFixedSize(true);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(this));

        results = new ArrayList<>();
        adapterSchedule = new OnGoingScheduleAdapter(results);
        recyclerViewSchedule.setAdapter(adapterSchedule);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Schedule data = dataSnapshot.getValue(Schedule.class);

                    //get today
                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                    DateFormat format = new SimpleDateFormat("EE");
                    String date = dateFormat.format(calendar.getTime());
                    String day = format.format(calendar.getTime());
                    Log.d("tgl", ""+data.getTanggal());
                    Log.d("date", date);
                    String sunday = "";

                    if (data.getSun().equals("true")){
                        sunday = "Sun";
                    }

                    if (data.getTanggal().equals(date) || day.equals(sunday)){
                        schedule.setVisibility(View.VISIBLE);
                        Log.d("cocok", "pas");
                        results.add(data);
                    }
                    adapterSchedule.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.img_icActivity){
            showActivitiesList();
        } if (i == R.id.img_icSchedule){
            showSchedulesList();
        } else {
            showDialogChoices();
        }
    }

    private void showActivitiesList() {
        //pindah ke halaman show activities dengan membawa data userID
        Intent i = new Intent(this, ShowActivities.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }

    private void showSchedulesList() {
        //pindah ke halaman show schedules dengan membawa data userID
        Intent i = new Intent(this, ShowSchedules.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }

    private void showDialogChoices() {
        //tampilkan dialog untuk memilih kategori activity atau schedule
        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Daily Task");
        dialog.setMessage("Select Category");

        dialog.setNegativeButton("Schedule", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                intent ke halaman add schedule
                Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });

        dialog.setPositiveButton("Activity", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                intent ke halaman add activity
                Intent intent = new Intent(MainActivity.this, AddActivityActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        dialog.show();
    }

}