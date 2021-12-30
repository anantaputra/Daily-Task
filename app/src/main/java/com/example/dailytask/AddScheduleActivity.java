package com.example.dailytask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText et_title, et_note, et_event, et_tgl;
    MaterialButton mo, tu, we, th, fr, sa, su;
    Button buat;
    String title, note, event, date, sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    DatabaseReference myRef;

    Boolean mon = false;
    Boolean tue = false;
    Boolean wed = false;
    Boolean thu = false;
    Boolean fri = false;
    Boolean sat = false;
    Boolean sun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        et_title = findViewById(R.id.et_judul);
        et_note = findViewById(R.id.et_ctt);
        et_event = findViewById(R.id.et_acara);
        et_tgl = findViewById(R.id.et_tgl);
        mo = findViewById(R.id.mon);
        tu = findViewById(R.id.tue);
        we = findViewById(R.id.wed);
        th = findViewById(R.id.thu);
        fr = findViewById(R.id.fri);
        sa = findViewById(R.id.sat);
        su = findViewById(R.id.sun);
        buat = findViewById(R.id.btn_buat);

        //get data from intent
        String userID = getIntent().getStringExtra("userID");
        String username = getIntent().getStringExtra("username");

        myRef = FirebaseDatabase.getInstance().getReference("users").child(userID);

        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mon = !mon;
                if (mon){
                    mo.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    mo.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        tu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tue = !tue;
                if (tue){
                    tu.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    tu.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        we.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wed = !wed;
                if (wed){
                    we.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    we.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thu = !thu;
                if (thu){
                    th.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    th.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fri = !fri;
                if (fri){
                    fr.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    fr.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sat = !sat;
                if (sat){
                    sa.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    sa.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sun = !sun;
                if (sun){
                    su.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.orange));
                } else {
                    su.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.abu));
                }
            }
        });

        et_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });

        buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from editText
                title = et_title.getText().toString();
                note = et_note.getText().toString();
                event = et_event.getText().toString();
                date = et_tgl.getText().toString();

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

                writeNewSchedule(title, note, event, date, monday, tuesday, wednesday, thursday,
                        friday, saturday, sunday);
                Toast.makeText(view.getContext(), "Jadwal berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);

            }
        });

    }

    private void writeNewSchedule(String judul, String catatan, String acara, String tanggal,
                                  String mon, String tue, String wed, String thu,
                                  String fri, String sat, String sun) {

        Schedule schedule = new Schedule(judul, catatan, acara, tanggal, mon, tue, wed, thu, fri,
                sat, sun);

        String uniqueId = UUID.randomUUID().toString();

        myRef.child("schedule").child(uniqueId).setValue(schedule);

    }

    private void datepicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
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
}