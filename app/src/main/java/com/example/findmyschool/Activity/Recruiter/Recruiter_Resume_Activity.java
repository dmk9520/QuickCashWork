package com.example.findmyschool.Activity.Recruiter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.Employee_Resume_Adapter;
import com.example.findmyschool.Adapter.Employee_Review_Adapter;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.Model.Resume;
import com.example.findmyschool.Model.Review;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Recruiter_Resume_Activity extends AppCompatActivity implements View.OnClickListener {

    private String[] resume_list;
    private RecyclerView review_recycler;
    private ImageView back;
    private DatabaseReference databaseReference;
    ArrayList<Resume> resumes = new ArrayList<>();
    private Employee_Resume_Adapter employee_resume_adapter;
    private Recruiter recriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruite_resume);
        recriter = Application.getRecruiter();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initListener();
        initAdapter();
        getData();

    }

    private void getData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resumes.clear();
                if (recriter.getResumePost() != null) {
                    if (!TextUtils.isEmpty(recriter.getResumePost())) {
                        resume_list = recriter.getResumePost().split(",");
                        if (dataSnapshot.hasChild("Resume")) {
                            for (DataSnapshot postSnapshot : dataSnapshot.child("Resume").getChildren()) {
                                Resume resume = postSnapshot.getValue(Resume.class);
                                if (Arrays.asList(resume_list).contains(resume.getId())) {
                                    resumes.add(resume);
                                }
                            }
                            Collections.reverse(resumes);
                            employee_resume_adapter.setData(resumes);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Recruiter_Resume_Activity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        employee_resume_adapter = new Employee_Resume_Adapter(Recruiter_Resume_Activity.this, resumes);
        review_recycler.setLayoutManager(new LinearLayoutManager(Recruiter_Resume_Activity.this, LinearLayoutManager.VERTICAL, false));
        review_recycler.setAdapter(employee_resume_adapter);
    }

    private void initListener() {
        back.setOnClickListener(this);
    }

    private void initView() {
        review_recycler = (RecyclerView) findViewById(R.id.review_recycler);
        back = (ImageView) findViewById(R.id.back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}