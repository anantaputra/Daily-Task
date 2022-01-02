package com.example.dailytask.addData;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialTimePicker picker;
    private EditText et_title, et_note, et_event, et_tgl, et_time;
    private MaterialButton mo, tu, we, th, fr, sa, su;
    private Button buat;
    private String title, note, event, hour, minute, date, sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    private LinearLayout pickDays, pickDate;
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
        setContentView(R.layout.activity_add_schedule);

        et_title    = findViewById(R.id.et_judul);
        et_note     = findViewById(R.id.et_ctt);
        et_event    = findViewById(R.id.et_acara);
        et_time     = findViewById(R.id.pick_time);
        aSwitch     = findViewById(R.id.switch_repeat);
        pickDays    = findViewById(R.id.pick_day);
        pickDate    = findViewById(R.id.pick_date);
        et_tgl      = findViewById(R.id.et_tgl);
        mo          = findViewById(R.id.mon);
        tu          = findViewById(R.id.tue);
        we          = findViewById(R.id.wed);
        th          = findViewById(R.id.thu);
        fr          = findViewById(R.id.fri);
        sa          = findViewById(R.id.sat);
        su          = findViewById(R.id.sun);
        buat        = findViewById(R.id.btn_buat);

        //get data from intent
        String userID = getIntent().getStringExtra("userID");

        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID);

        //hide pick day
        pickDays.setVisibility(View.GONE);

        //check switch value
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pickDays.setVisibility(View.VISIBLE);
                    pickDate.setVisibility(View.GONE);
                } else {
                    pickDays.setVisibility(View.GONE);
                    pickDate.setVisibility(View.VISIBLE);
                }
            }
        });

        //set click listener
        mo.setOnClickListener(this);
        tu.setOnClickListener(this);
        we.setOnClickListener(this);
        th.setOnClickListener(this);
        fr.setOnClickListener(this);
        sa.setOnClickListener(this);
        su.setOnClickListener(this);
        et_time.setOnClickListener(this);
        et_tgl.setOnClickListener(this);
        buat.setOnClickListener(this);

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
        } else if (i == R.id.pick_time){
            pickTime();
        } else if (i == R.id.et_tgl){
            pickDate();
        } else if (i == R.id.btn_buat){
            InsertSchedule();
        }
    }

    private void pickDate() {
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
                .setHour(Calendar.HOUR_OF_DAY+12)
                .setMinute(0)
                .setTitleText("Select Time")
                .build();
        picker.show(getSupportFragmentManager(), "Daily Task");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getHour() > 12){
                    et_time.setText(String.format("%02d",picker.getHour())+" : "+
                            String.format("%02d",picker.getMinute()));
                } else {
                    et_time.setText(picker.getHour()+" : " + picker.getMinute());
                }
            }
        });
    }

    private void InsertSchedule() {
        //get data from editText
        title   = et_title.getText().toString();
        note    = et_note.getText().toString();
        event   = et_event.getText().toString();
        hour    = String.valueOf(picker.getHour());
        minute  = String.valueOf(picker.getMinute());
        date    = et_tgl.getText().toString();

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

        writeNewSchedule(title, note, event, hour, minute, date, monday, tuesday, wednesday, thursday,
                friday, saturday, sunday);
        Toast.makeText(this, "Jadwal berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void writeNewSchedule(String title, String note, String event, String hour, String minute,
                                  String date, String monday, String tuesday, String wednesday,
                                  String thursday, String friday, String saturday, String sunday) {
        Schedule schedule = new Schedule(title, note, event, hour, minute, date, monday, tuesday, wednesday,
                thursday, friday, saturday, sunday);

        String uniqueId = UUID.randomUUID().toString();

        myRef.child("schedule").child(uniqueId).setValue(schedule);
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
}