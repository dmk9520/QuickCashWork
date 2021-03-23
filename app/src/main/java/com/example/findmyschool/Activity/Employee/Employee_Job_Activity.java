package com.example.findmyschool.Activity.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyschool.Adapter.Postjob_Viewpager_Adapter;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employee_Job_Activity extends AppCompatActivity implements View.OnClickListener {

    private PostJobs post_job;
    private TabLayout company_tab;
    private ViewPager company_viewpager;
    private TextView company_name;
    private ImageView post_job_back;
    private DatabaseReference databaseReference;
    private Recruiter recruiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_job);
        post_job = (PostJobs) getIntent().getSerializableExtra("post_job");
        initView();
        initListener();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Recruiter")) {
                    recruiter = (Recruiter) snapshot.child("Recruiter").child(post_job.getRecruiterid()).getValue(Recruiter.class);
                    if (recruiter != null) {
                        initAdapter();
                    } else {
                        Toast.makeText(Employee_Job_Activity.this, "No data found...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initAdapter() {
        Postjob_Viewpager_Adapter postjob_viewpager_adapter = new Postjob_Viewpager_Adapter(getSupportFragmentManager(), 2, post_job, recruiter);
        company_viewpager.setAdapter(postjob_viewpager_adapter);
        company_viewpager.setOffscreenPageLimit(2);

        company_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(company_tab));

        company_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                company_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initListener() {
        post_job_back.setOnClickListener(this);
    }

    private void initView() {
        company_tab = (TabLayout) findViewById(R.id.company_tab);
        company_viewpager = (ViewPager) findViewById(R.id.company_viewpager);
        company_name = (TextView) findViewById(R.id.company_name);
        post_job_back = (ImageView) findViewById(R.id.post_job_back);

        company_name.setText(post_job.getCompanyname());

        company_tab.addTab(company_tab.newTab().setText("  Job Details  "));
        company_tab.addTab(company_tab.newTab().setText("Company Profile"));
        company_tab.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_job_back:
                finish();
                break;
        }
    }
}