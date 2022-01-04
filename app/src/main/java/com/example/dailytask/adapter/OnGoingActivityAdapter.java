package com.example.dailytask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytask.R;
import com.example.dailytask.model.Activity;
import com.example.dailytask.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class OnGoingActivityAdapter extends RecyclerView.Adapter<OnGoingActivityAdapter.ViewHolder>{
    private List<Activity> results = new ArrayList<>();

    public OnGoingActivityAdapter(List<Activity> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public OnGoingActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new OnGoingActivityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingActivityAdapter.ViewHolder holder, int position) {
        Activity result = results.get(position);

        holder.title.setText(result.getJudul());
        if (Integer.parseInt(result.getJam()) < 10){
            if (Integer.parseInt(result.getMenit()) < 10){
                holder.time.setText("0"+result.getJam()+":"+"0"+result.getMenit());
            } else {
                holder.time.setText("0"+result.getJam()+":"+result.getMenit());
            }
        } else {
            if (Integer.parseInt(result.getMenit()) < 10){
                holder.time.setText(result.getJam()+":"+"0"+result.getMenit());
            } else {
                holder.time.setText(result.getJam()+":"+result.getMenit());
            }
        }
        holder.every_day.setVisibility(View.GONE);
        holder.check.setImageResource(R.drawable.ic_baseline_check_circle_24);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check;
        TextView title, time, every, monday, tuesday, wednesday, thursday, friday,
                saturday, sunday;
        LinearLayout every_day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.delete_btn_activity);
            time = itemView.findViewById(R.id.reminder_time_activity);
            title = itemView.findViewById(R.id.title_activity);
            every = itemView.findViewById(R.id.every_activity);
            monday = itemView.findViewById(R.id.monday_activity);
            tuesday     = itemView.findViewById(R.id.tuesday_activity);
            wednesday   = itemView.findViewById(R.id.wednesday_activity);
            thursday    = itemView.findViewById(R.id.thursday_activity);
            friday      = itemView.findViewById(R.id.friday_activity);
            saturday    = itemView.findViewById(R.id.saturday_activity);
            sunday      = itemView.findViewById(R.id.sunday_activity);
            every_day = itemView.findViewById(R.id.every_day_activity);
        }
    }
}
