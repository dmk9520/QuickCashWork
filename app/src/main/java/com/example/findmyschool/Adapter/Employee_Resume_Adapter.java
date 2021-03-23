package com.example.findmyschool.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Recruiter.Employee_Show_Profile;
import com.example.findmyschool.Activity.Recruiter.Resume_Show_Activity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Resume;
import com.example.findmyschool.Model.Review;
import com.example.findmyschool.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employee_Resume_Adapter extends RecyclerView.Adapter<Employee_Resume_Adapter.ViewHolder> {
    private final DatabaseReference databaseReference;
    Activity activity;
    ArrayList<Resume> resumes;
    LayoutInflater inflater;

    public Employee_Resume_Adapter(Activity employee_review_activity, ArrayList<Resume> resumes) {
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
        holder.resume_name.setText(resumes.get(position).getFromname());
        holder.resume_interest.setText(resumes.get(position).getFrominterest());
        Glide.with(activity).load(resumes.get(position).getFromprofile()).into(holder.resume_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Employees")) {
                            for (DataSnapshot postSnapshot : dataSnapshot.child("Employees").getChildren()) {
                                Employees employees = postSnapshot.getValue(Employees.class);
                                if (employees.getId().equals(resumes.get(holder.getAdapterPosition()).getFromid())) {
                                    activity.startActivity(new Intent(activity, Resume_Show_Activity.class).putExtra("data", employees));
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return resumes.size();
    }

    public void setData(ArrayList<Resume> resume_list) {
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
