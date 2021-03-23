package com.example.findmyschool.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.Employee_Job_Activity;
import com.example.findmyschool.Activity.Recruiter.Employees_Show_Activity;
import com.example.findmyschool.Activity.Recruiter.RecruiterJobPostActivity;
import com.example.findmyschool.Activity.Recruiter.RecruiterMainActivity;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Recruiter_Job_Adapter extends RecyclerView.Adapter<Recruiter_Job_Adapter.ViewHolder> {
    Activity activity;
    ArrayList<PostJobs> postJobs;
    LayoutInflater inflater;
    DatabaseReference databaseReference;

    public Recruiter_Job_Adapter(Activity activity, ArrayList<PostJobs> postJobs, DatabaseReference databaseReference) {
        this.activity = activity;
        this.postJobs = postJobs;
        this.databaseReference = databaseReference;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recruiter_post_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.job_title.setText(postJobs.get(position).getJobtitle());
        holder.job_location.setText(postJobs.get(position).getJoblocality() + "," + postJobs.get(position).getJobcity());
        holder.job_salary.setText(postJobs.get(position).getSalary());
        holder.job_education.setText(postJobs.get(position).getQualification());
        holder.job_experience.setText(postJobs.get(position).getWorkexperience());
        holder.job_english.setText(postJobs.get(position).getLanguage());
        holder.job_number.setText(postJobs.get(position).getNumber());
        holder.job_switch.setOnCheckedChangeListener(null);
        holder.job_switch.setChecked(Boolean.parseBoolean(postJobs.get(position).getFind()));
        holder.job_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, RecruiterJobPostActivity.class).putExtra("type", 1).putExtra("data", postJobs.get(holder.getAdapterPosition())));
            }
        });
        holder.show_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Employees_Show_Activity.class).putExtra("data", postJobs.get(position).getCompanytype()));
            }
        });
        holder.job_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child("Jobs").child(postJobs.get(position).getId()).child("find").setValue("true");
                } else {
                    new AlertDialog.Builder(activity)
                            .setTitle("Hide Job")
                            .setMessage("Are you sure you want to hide this job for employee?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("Jobs").child(postJobs.get(position).getId()).child("find").setValue("false");
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.job_switch.setChecked(true);
                                }
                            }).setCancelable(false)
                            .show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postJobs.size();
    }

    public void setList(ArrayList<PostJobs> jobs) {
        this.postJobs = jobs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Switch job_switch;
        TextView job_title, job_edit, job_location, job_salary, job_education, job_experience, job_english, job_number;
        LinearLayout show_employee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_employee = (LinearLayout) itemView.findViewById(R.id.show_employee);
            job_title = (TextView) itemView.findViewById(R.id.job_title);
            job_edit = (TextView) itemView.findViewById(R.id.job_edit);
            job_location = (TextView) itemView.findViewById(R.id.job_location);
            job_salary = (TextView) itemView.findViewById(R.id.job_salary);
            job_education = (TextView) itemView.findViewById(R.id.job_education);
            job_experience = (TextView) itemView.findViewById(R.id.job_experience);
            job_english = (TextView) itemView.findViewById(R.id.job_english);
            job_number = (TextView) itemView.findViewById(R.id.job_number);
            job_switch = (Switch) itemView.findViewById(R.id.job_switch);
        }
    }
}
