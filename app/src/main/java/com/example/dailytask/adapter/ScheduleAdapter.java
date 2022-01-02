package com.example.dailytask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytask.R;
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

        if (Integer.parseInt(result.getJam()) < 10){
            holder.reminder.setText("0"+result.getJam()+":"+result.getMenit());
        } else {
            holder.reminder.setText(result.getJam()+":"+result.getMenit());
        }

        holder.title.setText(result.getJudul());
        holder.event.setText(result.getAcara());
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
                result.getFri().equals("true") && result.getSat().equals("true") && result.getSun().equals("true")){
            holder.every.setText("everyday");
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
        TextView reminder, title, event, notes, every, monday, tuesday, wednesday, thursday, friday,
                saturday, sunday, date;
        LinearLayout every_day, dates;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder    = itemView.findViewById(R.id.reminder_time);
            title       = itemView.findViewById(R.id.title);
            event       = itemView.findViewById(R.id.event);
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
        }
    }
}
