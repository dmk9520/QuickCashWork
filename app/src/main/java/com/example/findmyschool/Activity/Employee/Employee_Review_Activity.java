package com.example.findmyschool.Activity.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findmyschool.Activity.Recruiter.RecruiterMainActivity;
import com.example.findmyschool.Activity.SplashActivity;
import com.example.findmyschool.Adapter.Employee_Review_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.Model.Recruiter;
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

public class Employee_Review_Activity extends AppCompatActivity implements View.OnClickListener {

    private Employees employee;
    private String[] review_list;
    private RecyclerView review_recycler;
    private ImageView back;
    private DatabaseReference databaseReference;
    ArrayList<Review> reviews = new ArrayList<>();
    private Employee_Review_Adapter employee_review_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_review);
        employee = Application.getEmployee();

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
                reviews.clear();
                if (employee.getReview() != null) {
                    review_list = employee.getReview().split(",");
                    if (dataSnapshot.hasChild("Review")) {
                        for (DataSnapshot postSnapshot : dataSnapshot.child("Review").getChildren()) {
                            Review review = postSnapshot.getValue(Review.class);
                            if (Arrays.asList(review_list).contains(review.getId())) {
                                reviews.add(review);
                            }
                        }
                        employee_review_adapter.setData(reviews);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Employee_Review_Activity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        employee_review_adapter = new Employee_Review_Adapter(Employee_Review_Activity.this, reviews);
        review_recycler.setLayoutManager(new LinearLayoutManager(Employee_Review_Activity.this, LinearLayoutManager.VERTICAL, false));
        review_recycler.setAdapter(employee_review_adapter);
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