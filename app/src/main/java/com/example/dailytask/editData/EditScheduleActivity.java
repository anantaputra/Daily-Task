package com.example.dailytask.editData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dailytask.MainActivity;
import com.example.dailytask.R;
import com.example.dailytask.helper.DatePickerHelper;
import com.example.dailytask.model.Schedule;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class EditScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialTimePicker picker;
    private String note, event, hour, minute, time, date, sunday, monday, tuesday, wednesday,
            thursday, friday, saturday, key, userID;
    private EditText et_note, et_event, et_tgl, et_time;
    private MaterialButton mo, tu, we, th, fr, sa, su;
    private LinearLayout pickDate, pickDay;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button btn;
    private Switch aSwitch;
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
        setContentView(R.layout.activity_edit_schedule);

        //set Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.orange));

        //set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);

        et_note = findViewById(R.id.et_ctt_up_schedule);
        et_event = findViewById(R.id.et_judul_up_schedule);
        et_tgl = findViewById(R.id.et_tgl_up_schedule);
        et_time = findViewById(R.id.pick_time_up_schedule);
        pickDate = findViewById(R.id.pick_date_up_schedule);
        pickDay = findViewById(R.id.pick_day_up_schedule);
        mo          = findViewById(R.id.mon);
        tu          = findViewById(R.id.tue);
        we          = findViewById(R.id.wed);
        th          = findViewById(R.id.thu);
        fr          = findViewById(R.id.fri);
        sa          = findViewById(R.id.sat);
        su          = findViewById(R.id.sun);
        btn         = findViewById(R.id.update_btn_schedule);
        aSwitch     = findViewById(R.id.switch_repeat_up_schedule);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID);

        key = getIntent().getStringExtra("key");
        time = getIntent().getStringExtra("time");
        note = getIntent().getStringExtra("note");
        event = getIntent().getStringExtra("title");
        monday = getIntent().getStringExtra("mond");
        tuesday = getIntent().getStringExtra("tues");
        wednesday = getIntent().getStringExtra("wedn");
        thursday = getIntent().getStringExtra("thur");
        friday = getIntent().getStringExtra("frid");
        saturday = getIntent().getStringExtra("satu");
        sunday = getIntent().getStringExtra("sund");
        date = getIntent().getStringExtra("tgl");

        //set ke edt text
        et_event.setText(event);
        et_note.setText(note);
        et_time.setText(time);
        if (monday.equals("true")){
            mo.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            mon = true;
        } if (tuesday.equals("true")){
            tu.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            tue = true;
        } if (wednesday.equals("true")){
            we.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            wed = true;
        } if (thursday.equals("true")){
            th.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            thu = true;
        } if (friday.equals("true")){
            fr.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            fri = true;
        } if (saturday.equals("true")){
            sa.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            sat = true;
        } if (sunday.equals("true")){
            su.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            aSwitch.setChecked(true);
            sun = true;
        }

        if (date.equals("")){
            pickDate.setVisibility(View.GONE);
        } else {
            pickDay.setVisibility(View.GONE);
            pickDate.setVisibility(View.VISIBLE);
            et_tgl.setText(date);
        }

        mo.setOnClickListener(this);
        tu.setOnClickListener(this);
        we.setOnClickListener(this);
        th.setOnClickListener(this);
        fr.setOnClickListener(this);
        sa.setOnClickListener(this);
        su.setOnClickListener(this);
        et_time.setOnClickListener(this);
        et_tgl.setOnClickListener(this);
        btn.setOnClickListener(this);

        //check switch value
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pickDay.setVisibility(View.VISIBLE);
                    pickDate.setVisibility(View.GONE);
                } else {
                    pickDay.setVisibility(View.GONE);
                    pickDate.setVisibility(View.VISIBLE);

                    mon = false;
                    tue = false;
                    wed = false;
                    thu = false;
                    fri = false;
                    sat = false;
                    sun = false;
                }
            }
        });
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
        } else if (i == R.id.et_tgl_up_schedule){
            pickDates();
        } else if (i == R.id.update_btn_schedule){
            updateSchedule();
        }
    }

    private void pickDates() {
        DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.show(getSupportFragmentManager(), "data");
        datePickerHelper.setOnDateClickListener(new DatePickerHelper.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = ""+bulan+"/"+hari+"/"+tahun;
                et_tgl.setText(text);
            }
        });
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

    private void updateSchedule() {
        note    = et_note.getText().toString();
        event   = et_event.getText().toString();
        if (et_time.getText().toString().equals(time)){
            hour = getIntent().getStringExtra("jam");
            minute = getIntent().getStringExtra("menit");
        } else {
            hour    = String.valueOf(picker.getHour());
            minute  = String.valueOf(picker.getMinute());
        }
        date    = et_tgl.getText().toString();
        key     = getIntent().getStringExtra("key");

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

        UpdatetheSchedule(note, event, hour, minute, date, monday, tuesday, wednesday,
                thursday, friday, saturday, sunday);
        Toast.makeText(this, "Jadwal berhasil diubah", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void UpdatetheSchedule(String note, String event, String hour, String minute,
                                   String date, String monday, String tuesday, String wednesday,
                                   String thursday, String friday, String saturday, String sunday) {
        Schedule schedule = new Schedule( note, event, hour, minute, date, monday, tuesday,
                wednesday, thursday, friday, saturday, sunday);

        myRef.child("schedule").child(key).setValue(schedule);
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