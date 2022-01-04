package com.example.dailytask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailytask.account.LoginActivity;
import com.example.dailytask.account.ProfileActivity;
import com.example.dailytask.adapter.OnGoingActivityAdapter;
import com.example.dailytask.adapter.OnGoingScheduleAdapter;
import com.example.dailytask.adapter.ScheduleAdapter;
import com.example.dailytask.addData.AddActivityActivity;
import com.example.dailytask.addData.AddScheduleActivity;
import com.example.dailytask.model.Activity;
import com.example.dailytask.model.Schedule;
import com.example.dailytask.show.ShowActivities;
import com.example.dailytask.show.ShowSchedules;
import com.google.android.material.appbar.MaterialToolbar;
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

    private ImageView imgSchedule, imgActivity, image, gambar;
    private TextView schedule, activity;
    private FloatingActionButton add;
    private AlertDialog.Builder dialog;
    private OnGoingScheduleAdapter adapterSchedule;
    private OnGoingActivityAdapter activityAdapter;
    private RecyclerView recyclerViewSchedule, recyclerViewActivity;
    private ArrayList<Schedule> results;
    private ArrayList<Activity> Activityresults;
    private String userID = "";
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));
        toolbar.inflateMenu(R.menu.menu);

        //ambil komponen berdasar id
        imgSchedule = findViewById(R.id.img_icSchedule);
        imgActivity = findViewById(R.id.img_icActivity);
        schedule    = findViewById(R.id.schedule_show);
        activity    = findViewById(R.id.activity_show);
        add         = findViewById(R.id.add);
        image       = findViewById(R.id.img_image);
        gambar      = findViewById(R.id.gambar);

        //hide schedule and activity category
        schedule.setVisibility(View.GONE);
        activity.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        gambar.setVisibility(View.GONE);

        //get current username
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null){
            Intent i = new Intent(MainActivity.this, OpeningActivity.class);
            startActivity(i);
            return;
        }

        userID = user.getUid();


        //nambahin method onClick, biar tombolnya bisa diklik
        imgActivity.setOnClickListener(this);
        imgSchedule.setOnClickListener(this);
        add.setOnClickListener(this);

        //setup recyclerview schedule
        rvSchedule();

        //setup recyclerview activity
        rvActivity();

        //item menu click listener
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.user_profile){
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    i.putExtra("userID", userID);
                    startActivity(i);
                } else if (item.getItemId() == R.id.logout){
                    SignOut();
                }
                return false;
            }
        });
    }

    private void rvActivity() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users")
                .child(userID).child("activity");

        recyclerViewActivity = findViewById(R.id.rv_activity);

        recyclerViewActivity.setHasFixedSize(true);
        recyclerViewActivity.setLayoutManager(new LinearLayoutManager(this));

        Activityresults = new ArrayList<>();
        activityAdapter = new OnGoingActivityAdapter(Activityresults);
        recyclerViewActivity.setAdapter(activityAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Activity data = dataSnapshot.getValue(Activity.class);

                    //get today
                    Calendar calendar = Calendar.getInstance();
                    DateFormat format = new SimpleDateFormat("EE");
                    String day = format.format(calendar.getTime());
                    String sunday = "";
                    String monday = "";
                    String tuesday = "";
                    String wednesday = "";
                    String thursday = "";
                    String friday = "";
                    String saturday = "";

                    if (data.getSun().equals("true")){
                        sunday = "Sun";
                    } if (data.getMon().equals("true")){
                        monday = "Mon";
                    } if (data.getTue().equals("true")){
                        tuesday = "Tue";
                    } if (data.getWed().equals("true")){
                        wednesday = "Wed";
                    } if (data.getThu().equals("true")){
                        thursday = "Thu";
                    } if (data.getFri().equals("true")){
                        friday = "Fri";
                    } if (data.getSat().equals("true")){
                        saturday = "Sat";
                    } if (data.getSun().equals("true")){
                        sunday = "Sun";
                    }

                    if (day.equals(sunday) || day.equals(monday) || day.equals(tuesday) ||
                            day.equals(wednesday) || day.equals(thursday) || day.equals(friday) ||
                            day.equals(saturday)){
                        image.setVisibility(View.GONE);
                        gambar.setVisibility(View.GONE);
                        activity.setVisibility(View.VISIBLE);
                        Log.d("cocok", "pas");
                        Activityresults.add(data);
                    }
                    activityAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rvSchedule() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users")
                .child(userID).child("schedule");

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
                    String monday = "";
                    String tuesday = "";
                    String wednesday = "";
                    String thursday = "";
                    String friday = "";
                    String saturday = "";

                    if (data.getSun().equals("true")){
                        sunday = "Sun";
                    } if (data.getMon().equals("true")){
                        monday = "Mon";
                    } if (data.getTue().equals("true")){
                        tuesday = "Tue";
                    } if (data.getWed().equals("true")){
                        wednesday = "Wed";
                    } if (data.getThu().equals("true")){
                        thursday = "Thu";
                    } if (data.getFri().equals("true")){
                        friday = "Fri";
                    } if (data.getSat().equals("true")){
                        saturday = "Sat";
                    } if (data.getSun().equals("true")){
                        sunday = "Sun";
                    }

                    if (data.getTanggal().equals(date) || day.equals(sunday) || day.equals(monday)
                            || day.equals(tuesday) || day.equals(wednesday) || day.equals(thursday)
                            || day.equals(friday) || day.equals(saturday)){
                        image.setVisibility(View.GONE);
                        gambar.setVisibility(View.GONE);
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
        } if (i == R.id.add){
            showDialogChoices();
        }
    }

    private void SignOut() {
        mAuth.signOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
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
                Intent intent = new Intent(MainActivity.this,
                        AddScheduleActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);

            }
        });

        dialog.setPositiveButton("Activity", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                intent ke halaman add activity
                Intent intent = new Intent(MainActivity.this,
                        AddActivityActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (user != null){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(1);
        }
    }
}