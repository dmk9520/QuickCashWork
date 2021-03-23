package com.example.findmyschool.Activity.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyschool.Adapter.Employee_Job_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
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

public class Job_Filter_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private RecyclerView filter_recycler;
    private Employees employee;
    private int type;
    ArrayList<PostJobs> postJobs = new ArrayList<>();
    String[] post_id;
    private DatabaseReference databaseReference;
    private TextView filter_type;
    private Employee_Job_Adapter employee_job_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_filter);
        employee = Application.getEmployee();
        type = getIntent().getIntExtra("type", 0);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        if (type == 0) {
            filter_type.setText("My Favourite Jobs");
        } else {
            filter_type.setText("My Recommended Jobs");
        }
        initListener();
        initAdapter();
        getData();

    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postJobs.clear();
                employee = Application.getEmployee();


                if (dataSnapshot.hasChild("Jobs")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Jobs").getChildren()) {
                        PostJobs postJob = postSnapshot.getValue(PostJobs.class);
                        if (type == 0) {
                            if (employee.getFavjob() != null) {
                                post_id = employee.getFavjob().split(",");
                                if (Arrays.asList(post_id).contains(postJob.getId())) {
                                    postJobs.add(postJob);
                                }
                            }
                        } else {
                            if (employee.getInterested().equals(postJob.getCompanytype()) && employee.getCity().toLowerCase().equals(postJob.getJobcity().toLowerCase())) {
                                postJobs.add(postJob);
                            }
                        }
                    }
                    employee_job_adapter.setData(postJobs);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initAdapter() {
        employee_job_adapter = new Employee_Job_Adapter(Job_Filter_Activity.this, postJobs);
        filter_recycler.setLayoutManager(new LinearLayoutManager(Job_Filter_Activity.this, LinearLayoutManager.VERTICAL, false));
        filter_recycler.setAdapter(employee_job_adapter);
    }

    private void initListener() {
        back.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        filter_recycler = (RecyclerView) findViewById(R.id.filter_recycler);
        filter_type = (TextView) findViewById(R.id.filter_type);
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