package com.example.findmyschool.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.Employee_Job_Activity;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

import java.util.ArrayList;

public class Employee_Job_Adapter extends RecyclerView.Adapter<Employee_Job_Adapter.ViewHolder> {
    Activity activity;
    ArrayList<PostJobs> postJobs;
    LayoutInflater inflater;

    public Employee_Job_Adapter(Activity activity, ArrayList<PostJobs> postJobs) {
        this.activity = activity;
        this.postJobs = postJobs;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_post_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.job_title.setText(postJobs.get(position).getJobtitle());
        holder.company_type.setText(postJobs.get(position).getCompanytype());
        holder.job_company.setText(postJobs.get(position).getCompanyname());
        holder.job_eduction.setText(postJobs.get(position).getQualification());
        holder.job_experience.setText(postJobs.get(position).getWorkexperience());
        holder.job_salary.setText(postJobs.get(position).getSalary() + " | " + postJobs.get(position).getJobcity());
        Glide.with(activity).load(postJobs.get(position).getProfile()).into(holder.post_job_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Employee_Job_Activity.class).putExtra("post_job", postJobs.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return postJobs.size();
    }

    public void setData(ArrayList<PostJobs> jobs) {
        this.postJobs = jobs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView job_title, job_company, job_eduction, job_experience, job_salary, company_type;
        ShapeableImageView post_job_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            job_title = (TextView) itemView.findViewById(R.id.job_title);
            job_company = (TextView) itemView.findViewById(R.id.job_company);
            job_eduction = (TextView) itemView.findViewById(R.id.job_eduction);
            job_experience = (TextView) itemView.findViewById(R.id.job_experience);
            job_salary = (TextView) itemView.findViewById(R.id.job_salary);
            company_type = (TextView) itemView.findViewById(R.id.company_type);
            post_job_profile = (ShapeableImageView) itemView.findViewById(R.id.post_job_profile);
            float radius = activity.getResources().getDimension(R.dimen._15sdp);
            post_job_profile.setShapeAppearanceModel(post_job_profile.getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build());
        }
    }
}
