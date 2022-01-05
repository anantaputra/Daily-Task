package com.example.dailytask.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailytask.R;
import com.example.dailytask.editData.EditActivityActivity;
import com.example.dailytask.model.Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<Activity> results = new ArrayList<>();

    public ActivityAdapter(List<Activity> results){
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Activity result = results.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = holder.time.getText().toString();
                String title = result.getJudul();
                String hour = result.getJam();
                String minute = result.getMenit();
                String height = result.getTinggi();
                String weight = result.getBerat();
                String key = result.getKey();
                String mond = result.getMon();
                String tues = result.getTue();
                String wedn = result.getWed();
                String thur = result.getThu();
                String frid = result.getFri();
                String satu = result.getSat();
                String sund = result.getSun();

                Intent i = new Intent(view.getContext(), EditActivityActivity.class);
                i.putExtra("key", key);
                i.putExtra("title", title);
                i.putExtra("hour", hour);
                i.putExtra("minute", minute);
                i.putExtra("height", height);
                i.putExtra("weight", weight);
                i.putExtra("time", time);
                i.putExtra("mond", mond);
                i.putExtra("tues", tues);
                i.putExtra("wedn", wedn);
                i.putExtra("thur", thur);
                i.putExtra("frid", frid);
                i.putExtra("satu", satu);
                i.putExtra("sund", sund);
                view.getContext().startActivity(i);
            }
        });

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

        holder.title.setText(result.getJudul());
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

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView time, title, every, monday, tuesday, wednesday, thursday, friday,
                saturday, sunday;
        LinearLayout every_day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time    = itemView.findViewById(R.id.reminder_time_activity);
            title       = itemView.findViewById(R.id.title_activity);
            every       = itemView.findViewById(R.id.every_activity);
            monday      = itemView.findViewById(R.id.monday_activity);
            tuesday     = itemView.findViewById(R.id.tuesday_activity);
            wednesday   = itemView.findViewById(R.id.wednesday_activity);
            thursday    = itemView.findViewById(R.id.thursday_activity);
            friday      = itemView.findViewById(R.id.friday_activity);
            saturday    = itemView.findViewById(R.id.saturday_activity);
            sunday      = itemView.findViewById(R.id.sunday_activity);
            every_day   = itemView.findViewById(R.id.every_day_activity);
            cardView    = itemView.findViewById(R.id.cardview_activity);
        }
    }
}
