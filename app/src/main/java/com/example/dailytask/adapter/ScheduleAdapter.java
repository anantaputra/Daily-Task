package com.example.dailytask.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytask.R;
import com.example.dailytask.editData.EditScheduleActivity;
import com.example.dailytask.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private List<Schedule> results = new ArrayList<>();

    public ScheduleAdapter(List<Schedule> results){
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule result = results.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = holder.reminder.getText().toString();
                String hour = result.getJam();
                String minute = result.getMenit();
                String title = result.getAcara();
                String note = result.getCatatan();
                String mond = result.getMon();
                String tues = result.getTue();
                String wedn = result.getWed();
                String thur = result.getThu();
                String frid = result.getFri();
                String satu = result.getSat();
                String sund = result.getSun();
                String tgl = result.getTanggal();
                String key = result.getKey();

                Intent i = new Intent(view.getContext(), EditScheduleActivity.class);
                i.putExtra("key", key);
                i.putExtra("time", time);
                i.putExtra("jam", hour);
                i.putExtra("menit", minute);
                i.putExtra("title", title);
                i.putExtra("note", note);
                i.putExtra("mond", mond);
                i.putExtra("tues", tues);
                i.putExtra("wedn", wedn);
                i.putExtra("thur", thur);
                i.putExtra("frid", frid);
                i.putExtra("satu", satu);
                i.putExtra("sund", sund);
                i.putExtra("tgl", tgl);
                view.getContext().startActivity(i);
            }
        });

        if (Integer.parseInt(result.getJam()) < 10){
            if (Integer.parseInt(result.getMenit()) < 10){
                holder.reminder.setText("0"+result.getJam()+":"+"0"+result.getMenit());
            } else {
                holder.reminder.setText("0"+result.getJam()+":"+result.getMenit());
            }
        } else {
            if (Integer.parseInt(result.getMenit()) < 10){
                holder.reminder.setText(result.getJam()+":"+"0"+result.getMenit());
            } else {
                holder.reminder.setText(result.getJam()+":"+result.getMenit());
            }
        }

        holder.title.setText(result.getAcara());
        holder.notes.setText(result.getCatatan());
        holder.every.setVisibility(View.GONE);
        holder.monday.setVisibility(View.GONE);
        holder.tuesday.setVisibility(View.GONE);
        holder.wednesday.setVisibility(View.GONE);
        holder.thursday.setVisibility(View.GONE);
        holder.friday.setVisibility(View.GONE);
        holder.saturday.setVisibility(View.GONE);
        holder.sunday.setVisibility(View.GONE);

        if (result.getMon().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.monday.setVisibility(View.VISIBLE);
        } if (result.getTue().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.tuesday.setVisibility(View.VISIBLE);
        } if (result.getWed().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.wednesday.setVisibility(View.VISIBLE);
        } if (result.getThu().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.thursday.setVisibility(View.VISIBLE);
        } if (result.getFri().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.friday.setVisibility(View.VISIBLE);
        } if (result.getSat().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.saturday.setVisibility(View.VISIBLE);
        } if (result.getSun().equals("true")){
            holder.every.setVisibility(View.VISIBLE);
            holder.sunday.setVisibility(View.VISIBLE);
        } if (result.getMon().equals("true") && result.getTue().equals("true") &&
                result.getWed().equals("true") && result.getThu().equals("true") &&
                result.getFri().equals("true") && result.getSat().equals("true") &&
                result.getSun().equals("true")){
            holder.every.setText("everyday");
            holder.monday.setVisibility(View.GONE);
            holder.tuesday.setVisibility(View.GONE);
            holder.wednesday.setVisibility(View.GONE);
            holder.thursday.setVisibility(View.GONE);
            holder.friday.setVisibility(View.GONE);
            holder.saturday.setVisibility(View.GONE);
            holder.sunday.setVisibility(View.GONE);
        }

        if (result.getTanggal().equals("")){
            holder.dates.setVisibility(View.GONE);
        } else {
            holder.every_day.setVisibility(View.GONE);
            holder.dates.setVisibility(View.VISIBLE);
            holder.date.setText(result.getTanggal());
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView reminder, title, notes, every, monday, tuesday, wednesday, thursday, friday,
                saturday, sunday, date;
        LinearLayout every_day, dates;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder    = itemView.findViewById(R.id.reminder_time);
            title       = itemView.findViewById(R.id.title);
            notes       = itemView.findViewById(R.id.notes);
            every       = itemView.findViewById(R.id.every);
            monday      = itemView.findViewById(R.id.monday);
            tuesday     = itemView.findViewById(R.id.tuesday);
            wednesday   = itemView.findViewById(R.id.wednesday);
            thursday    = itemView.findViewById(R.id.thursday);
            friday      = itemView.findViewById(R.id.friday);
            saturday    = itemView.findViewById(R.id.saturday);
            sunday      = itemView.findViewById(R.id.sunday);
            date        = itemView.findViewById(R.id.for_date);
            every_day   = itemView.findViewById(R.id.every_day);
            dates       = itemView.findViewById(R.id.dates);
            cardView    = itemView.findViewById(R.id.cardview);
        }
    }
}
