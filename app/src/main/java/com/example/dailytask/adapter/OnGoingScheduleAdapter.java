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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OnGoingScheduleAdapter extends RecyclerView.Adapter<OnGoingScheduleAdapter.ViewHolder>{
    private List<Schedule> results = new ArrayList<>();

    public OnGoingScheduleAdapter(List<Schedule> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public OnGoingScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new OnGoingScheduleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingScheduleAdapter.ViewHolder holder, int position) {
        Schedule result = results.get(position);

        if (Integer.parseInt(result.getJam()) < 10){
            holder.reminder.setText("0"+result.getJam()+":"+result.getMenit());
        } else {
            holder.reminder.setText(result.getJam()+":"+result.getMenit());
        }

        holder.title.setText(result.getJudul());
        holder.event.setText(result.getAcara());
        holder.notes.setText(result.getCatatan());
        holder.every_day.setVisibility(View.GONE);
        holder.dates.setVisibility(View.GONE);
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
