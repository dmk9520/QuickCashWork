package com.example.findmyschool.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.findmyschool.R;
import com.example.findmyschool.Activity.Employee.EmployeeLoginActivity;
import com.example.findmyschool.Activity.Recruiter.RecruiterLoginActivity;

public class MainActivity extends AppCompatActivity {

    private CardView recruiter_login,employee_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }

    private void initClick() {
        recruiter_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecruiterLoginActivity.class));
            }
        });
        employee_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmployeeLoginActivity.class));
            }
        });
    }

    private void initView() {
        recruiter_login=(CardView)findViewById(R.id.cv_recruiter_login);
        employee_login=(CardView)findViewById(R.id.cv_employee_login);
    }

}