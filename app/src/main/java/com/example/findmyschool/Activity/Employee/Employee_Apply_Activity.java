package com.example.findmyschool.Activity.Employee;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.Employee_Apply_Adapter;
import com.example.findmyschool.Adapter.Employee_Resume_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.Model.Resume;
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

public class Employee_Apply_Activity extends AppCompatActivity implements View.OnClickListener {

    private String[] resume_list;
    private RecyclerView review_recycler;
    private ImageView back;
    private DatabaseReference databaseReference;
    ArrayList<Recruiter> recruiters = new ArrayList<>();
    private Employees employees;
    private Employee_Apply_Adapter employee_apply_adapter;
    private TextView company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruite_resume);
        employees = Application.getEmployee();

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
                recruiters.clear();
                if (employees.getResumesend() != null) {
                    if (!TextUtils.isEmpty(employees.getResumesend())) {
                        resume_list = employees.getResumesend().split(",");
                        if (dataSnapshot.hasChild("Recruiter")) {
                            for (DataSnapshot postSnapshot : dataSnapshot.child("Recruiter").getChildren()) {
                                Recruiter resume = postSnapshot.getValue(Recruiter.class);
                                if (Arrays.asList(resume_list).contains(resume.getId())) {
                                    recruiters.add(resume);
                                }
                            }
                            employee_apply_adapter.setData(recruiters);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Employee_Apply_Activity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        employee_apply_adapter = new Employee_Apply_Adapter(Employee_Apply_Activity.this, recruiters);
        review_recycler.setLayoutManager(new LinearLayoutManager(Employee_Apply_Activity.this, LinearLayoutManager.VERTICAL, false));
        review_recycler.setAdapter(employee_apply_adapter);
    }

    private void initListener() {
        back.setOnClickListener(this);
    }

    private void initView() {
        review_recycler = (RecyclerView) findViewById(R.id.review_recycler);
        back = (ImageView) findViewById(R.id.back);
        company_name = (TextView) findViewById(R.id.company_name);
        company_name.setText("Apply Resume");
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