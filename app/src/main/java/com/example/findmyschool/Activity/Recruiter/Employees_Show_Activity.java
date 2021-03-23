package com.example.findmyschool.Activity.Recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.findmyschool.Adapter.Employee_List_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employees_Show_Activity extends AppCompatActivity implements View.OnClickListener {

    private String type;
    private DatabaseReference databaseReference;
    private RecyclerView employee_recycler;
    private ProgressBar progress;
    ArrayList<Employees> all_employee = new ArrayList<>();
    ArrayList<Employees> interested_employee = new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_show);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        type = getIntent().getStringExtra("data");
        initView();
        initListener();
        getData();
    }

    private void initListener() {
        back.setOnClickListener(this);
    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee_recycler.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                interested_employee.clear();
                all_employee.clear();
                if (dataSnapshot.hasChild("Employees")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Employees").getChildren()) {
                        Employees employees = postSnapshot.getValue(Employees.class);
                        all_employee.add(employees);
                        if (employees.getInterested().toLowerCase().contains(type.toLowerCase())) {
                            interested_employee.add(employees);
                        }
                    }
                    if (interested_employee.size() > 0) {
                        progress.setVisibility(View.GONE);
                        employee_recycler.setVisibility(View.VISIBLE);
                    } else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(Employees_Show_Activity.this, "Employees not founds...!", Toast.LENGTH_SHORT).show();
                    }
                    initAdapter();
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(Employees_Show_Activity.this, "Employees not founds...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initAdapter() {
        Employee_List_Adapter employee_list_adapter = new Employee_List_Adapter(Employees_Show_Activity.this, interested_employee);
        employee_recycler.setLayoutManager(new LinearLayoutManager(Employees_Show_Activity.this, LinearLayoutManager.VERTICAL, false));
        employee_recycler.setAdapter(employee_list_adapter);
    }

    private void initView() {
        employee_recycler = (RecyclerView) findViewById(R.id.employee_recycler);
        progress = (ProgressBar) findViewById(R.id.progress);
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