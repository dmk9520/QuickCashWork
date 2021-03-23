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

import com.example.findmyschool.Adapter.Employee_Review_Adapter;
import com.example.findmyschool.Model.Employees;
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

public class Recruiter_Review_Activity extends AppCompatActivity implements View.OnClickListener {

    private String[] review_list;
    private RecyclerView review_recycler;
    private ImageView back;
    private DatabaseReference databaseReference;
    ArrayList<Review> reviews = new ArrayList<>();
    private Employee_Review_Adapter employee_review_adapter;
    private Recruiter recriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_review);
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
                reviews.clear();
                if (recriter.getReview() != null) {
                    if (!TextUtils.isEmpty(recriter.getReview())) {
                        review_list = recriter.getReview().split(",");
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Recruiter_Review_Activity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        employee_review_adapter = new Employee_Review_Adapter(Recruiter_Review_Activity.this, reviews);
        review_recycler.setLayoutManager(new LinearLayoutManager(Recruiter_Review_Activity.this, LinearLayoutManager.VERTICAL, false));
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