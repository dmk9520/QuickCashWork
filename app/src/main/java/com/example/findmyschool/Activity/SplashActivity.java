package com.example.findmyschool.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findmyschool.Activity.Employee.EmployeeInterestActivity;
import com.example.findmyschool.Activity.Employee.EmployeeMainActivity;
import com.example.findmyschool.Activity.Recruiter.RecruiterMainActivity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Application.getLogin()) {
                    if (Application.getLoginType() == 0) {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Recruiter recruiter = dataSnapshot.child("Recruiter").child(Application.getRecruiter().getId()).getValue(Recruiter.class);
                                Application.setRecruiter(recruiter);
                                startActivity(new Intent(SplashActivity.this, RecruiterMainActivity.class));
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(SplashActivity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else if (Application.getLoginType() == 1) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Employees employees = dataSnapshot.child("Employees").child(Application.getEmployee().getId()).getValue(Employees.class);
                                Application.setEmployee(employees);
                                startActivity(new Intent(SplashActivity.this, EmployeeMainActivity.class));
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(SplashActivity.this, "Something went wrong...!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 1500);

    }
}