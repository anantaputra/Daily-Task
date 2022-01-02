package com.example.dailytask.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.dailytask.R;
import com.example.dailytask.adapter.ScheduleAdapter;
import com.example.dailytask.model.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowSchedules extends AppCompatActivity {

    private DatabaseReference myRef;
    static ScheduleAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Schedule> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedules);

        recyclerView = findViewById(R.id.rv);

        String userID = getIntent().getStringExtra("userID");

        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID).child("schedule");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        results = new ArrayList<>();
        adapter = new ScheduleAdapter(results);
        recyclerView.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Schedule schedule = dataSnapshot.getValue(Schedule.class);
                    results.add(schedule);
                    Log.d("data", ""+schedule.getJudul());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}