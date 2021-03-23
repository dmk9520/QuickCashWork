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
import com.example.findmyschool.Activity.Employee.Recruiter_Show_Activity;
import com.example.findmyschool.Activity.Recruiter.Resume_Show_Activity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.Model.Resume;
import com.example.findmyschool.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employee_Apply_Adapter extends RecyclerView.Adapter<Employee_Apply_Adapter.ViewHolder> {
    private final DatabaseReference databaseReference;
    Activity activity;
    ArrayList<Recruiter> resumes;
    LayoutInflater inflater;

    public Employee_Apply_Adapter(Activity employee_review_activity, ArrayList<Recruiter> resumes) {
        this.activity = employee_review_activity;
        this.resumes = resumes;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_resume, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.resume_name.setText(resumes.get(position).getName());
        holder.resume_interest.setText(resumes.get(position).getIndsturytype());
        Glide.with(activity).load(resumes.get(position).getProfile()).into(holder.resume_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Recruiter_Show_Activity.class).putExtra("data", resumes.get(holder.getAdapterPosition())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return resumes.size();
    }

    public void setData(ArrayList<Recruiter> resume_list) {
        this.resumes = resume_list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView resume_profile;
        TextView resume_name, resume_interest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resume_profile = (ShapeableImageView) itemView.findViewById(R.id.review_profile);
            resume_name = (TextView) itemView.findViewById(R.id.resume_name);
            resume_interest = (TextView) itemView.findViewById(R.id.resume_interest);

            float radius = activity.getResources().getDimension(R.dimen._20sdp);
            resume_profile.setShapeAppearanceModel(resume_profile.getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build());
        }
    }
}
