package com.example.findmyschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Model.Education;
import com.example.findmyschool.Model.Job;
import com.example.findmyschool.R;

import java.util.ArrayList;
import java.util.List;


public class Show_Exp_Adapter extends RecyclerView.Adapter<Show_Exp_Adapter.ViewHolder> {

    Context context;
    List<Job> jobs = new ArrayList<>();
    LayoutInflater inflater;

    public Show_Exp_Adapter(Context context, List<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
        inflater = LayoutInflater.from(context);
    }

    public void setEducation(ArrayList<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_show_exp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.exp_name.setText(jobs.get(position).getCname());
        holder.exp_designation.setText(jobs.get(position).getDesignation());
        holder.exp_date.setText("From: " + jobs.get(position).getSdate() + "\nTo: " + jobs.get(position).getEdate());
        holder.exp_no.setText(String.valueOf(position + 1) + ")");
    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView exp_designation;
        private final TextView exp_date;
        private final TextView exp_name;
        private final TextView exp_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exp_designation = (TextView) itemView.findViewById(R.id.exp_designation);
            exp_date = (TextView) itemView.findViewById(R.id.exp_date);
            exp_name = (TextView) itemView.findViewById(R.id.exp_name);
            exp_no = (TextView) itemView.findViewById(R.id.exp_no);
        }
    }

}
