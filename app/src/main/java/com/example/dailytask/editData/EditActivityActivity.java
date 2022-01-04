package com.example.dailytask.editData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dailytask.MainActivity;
import com.example.dailytask.R;
import com.example.dailytask.model.Activity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivityActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialTimePicker picker;
    private String height, weight, workout, hour, minute, time, sunday, monday, tuesday, wednesday,
            thursday, friday, saturday, key, userID;
    private EditText et_tinggi, et_berat, et_time;
    private MaterialButton mo, tu, we, th, fr, sa, su;
    private Spinner spin_jenis;
    private Button update;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference myRef;

    private Boolean mon = false;
    private Boolean tue = false;
    private Boolean wed = false;
    private Boolean thu = false;
    private Boolean fri = false;
    private Boolean sat = false;
    private Boolean sun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));

        //set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

        et_tinggi   = findViewById(R.id.ed_textTinggi_update);
        et_berat    = findViewById(R.id.ed_textBerat_update);
        et_time     = findViewById(R.id.et_time_activity_update);
        spin_jenis  = findViewById(R.id.listTargetOlahraga_update);
        mo          = findViewById(R.id.mon);
        tu          = findViewById(R.id.tue);
        we          = findViewById(R.id.wed);
        th          = findViewById(R.id.thu);
        fr          = findViewById(R.id.fri);
        sa          = findViewById(R.id.sat);
        su          = findViewById(R.id.sun);
        update      = findViewById(R.id.btn_simpan);

        //get Intent
        key = getIntent().getStringExtra("key");
        height = getIntent().getStringExtra("height");
        weight = getIntent().getStringExtra("weight");
        workout = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        monday = getIntent().getStringExtra("mond");
        tuesday = getIntent().getStringExtra("tues");
        wednesday = getIntent().getStringExtra("wedn");
        thursday = getIntent().getStringExtra("thur");
        friday = getIntent().getStringExtra("frid");
        saturday = getIntent().getStringExtra("satu");
        sunday = getIntent().getStringExtra("sund");

        et_tinggi.setText(height);
        et_berat.setText(weight);
        et_time.setText(time);
        if (monday.equals("true")){
            mo.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            mon = true;
        } if (tuesday.equals("true")){
            tu.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            tue = true;
        } if (wednesday.equals("true")){
            we.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            wed = true;
        } if (thursday.equals("true")){
            th.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            thu = true;
        } if (friday.equals("true")){
            fr.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            fri = true;
        } if (saturday.equals("true")){
            sa.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            sat = true;
        } if (sunday.equals("true")){
            su.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            sun = true;
        }

        mo.setOnClickListener(this);
        tu.setOnClickListener(this);
        we.setOnClickListener(this);
        th.setOnClickListener(this);
        fr.setOnClickListener(this);
        sa.setOnClickListener(this);
        su.setOnClickListener(this);
        et_time.setOnClickListener(this);
        update.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.mon){
            btnMon();
        } else if (i == R.id.tue){
            btnTue();
        } else if (i == R.id.wed){
            btnWed();
        } else if (i == R.id.thu){
            btnThu();
        } else if (i == R.id.fri){
            btnFri();
        } else if (i == R.id.sat){
            btnSat();
        } else if (i == R.id.sun){
            btnSun();
        } else if (i == R.id.pick_time_up_schedule){
            pickTime();
        } else if (i == R.id.update_btn_schedule){
            updateActivity();
        }
    }

    private void updateActivity() {
        height    = et_tinggi.getText().toString();
        weight   = et_berat.getText().toString();
        workout = spin_jenis.getSelectedItem().toString();
        if (et_time.getText().toString().equals(time)){
            hour = getIntent().getStringExtra("hour");
            minute = getIntent().getStringExtra("minute");
        } else {
            hour    = String.valueOf(picker.getHour());
            minute  = String.valueOf(picker.getMinute());
        }

        if (mon){
            monday = "true";
        } else {
            monday = "false";
        }
        if (tue){
            tuesday = "true";
        } else {
            tuesday = "false";
        }
        if (wed){
            wednesday = "true";
        } else {
            wednesday = "false";
        }
        if (thu){
            thursday = "true";
        } else {
            thursday = "false";
        }
        if (fri){
            friday = "true";
        } else {
            friday = "false";
        }
        if (sat){
            saturday = "true";
        } else {
            saturday = "false";
        }
        if (sun){
            sunday = "true";
        } else {
            sunday = "false";
        }

        UpdatetheActivity(height, weight, workout, hour, minute, monday, tuesday, wednesday,
                thursday, friday, saturday, sunday);
        Toast.makeText(this, "Aktivitas berhasil diubah", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    private void UpdatetheActivity(String height, String weight, String workout, String hour,
                                   String minute, String monday, String tuesday, String wednesday,
                                   String thursday, String friday, String saturday, String sunday) {
        Activity activity = new Activity(height, weight, workout, hour, minute, monday, tuesday,
                wednesday, thursday, friday, saturday, sunday);

        myRef.child("activity").child(key).setValue(activity);
    }

    private void pickTime() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(Integer.parseInt(getIntent().getStringExtra("jam")))
                .setMinute(Integer.parseInt(getIntent().getStringExtra("menit")))
                .setTitleText("Select Time")
                .build();
        picker.show(getSupportFragmentManager(), "Daily Task");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getHour() < 10){
                    if (picker.getMinute() < 10){
                        et_time.setText("0"+picker.getHour()+" : "+"0"+picker.getMinute());
                    } else {
                        et_time.setText("0"+picker.getHour()+" : "+picker.getMinute());
                    }
                } else {
                    if (picker.getMinute() < 10){
                        et_time.setText(picker.getHour()+" : " + "0"+picker.getMinute());
                    } else {
                        et_time.setText(picker.getHour()+" : " + picker.getMinute());
                    }
                }
            }
        });
    }

    private void btnSun() {
        sun = !sun;
        if (sun){
            su.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            su.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnSat() {
        sat = !sat;
        if (sat){
            sa.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            sa.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnFri() {
        fri = !fri;
        if (fri){
            fr.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            fr.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnThu() {
        thu = !thu;
        if (thu){
            th.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            th.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnWed() {
        wed = !wed;
        if (wed){
            we.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            we.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnTue() {
        tue = !tue;
        if (tue){
            tu.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            tu.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    private void btnMon() {
        mon = !mon;
        if (mon){
            mo.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        } else {
            mo.setBackgroundColor(ContextCompat.getColor(this, R.color.abu));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}