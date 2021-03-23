package com.example.findmyschool.Fragment.Employee;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.findmyschool.Activity.Employee.Employee_Apply_Activity;
import com.example.findmyschool.Activity.Employee.Employee_Resume_Activity;
import com.example.findmyschool.Activity.Employee.Employee_Review_Activity;
import com.example.findmyschool.Activity.Employee.Employee_Setting_Activity;
import com.example.findmyschool.Activity.Employee.Employee_profile_Activity;
import com.example.findmyschool.Activity.Employee.Job_Filter_Activity;
import com.example.findmyschool.BuildConfig;
import com.example.findmyschool.R;

public class Employee_Profile_Fragment extends Fragment implements View.OnClickListener {


    private View view;
    private LinearLayout profile;
    private LinearLayout resume;
    private LinearLayout fav_job;
    private LinearLayout rec_job;
    private LinearLayout setting;
    private LinearLayout review;
    private LinearLayout resume_send;

    public Employee_Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employee_profile, container, false);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        profile.setOnClickListener(this);
        resume.setOnClickListener(this);
        fav_job.setOnClickListener(this);
        rec_job.setOnClickListener(this);
        resume_send.setOnClickListener(this);

        setting.setOnClickListener(this);
        review.setOnClickListener(this);
    }

    private void initView() {
        profile = (LinearLayout) view.findViewById(R.id.profile);
        resume = (LinearLayout) view.findViewById(R.id.resume);
        fav_job = (LinearLayout) view.findViewById(R.id.fav_job);
        rec_job = (LinearLayout) view.findViewById(R.id.rec_job);
        resume_send = (LinearLayout) view.findViewById(R.id.resume_send);

        setting = (LinearLayout) view.findViewById(R.id.setting);
        review = (LinearLayout) view.findViewById(R.id.review);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                startActivity(new Intent(getActivity(), Employee_profile_Activity.class));
                break;
            case R.id.resume:
                startActivity(new Intent(getActivity(), Employee_Resume_Activity.class));
                break;
            case R.id.fav_job:
                startActivity(new Intent(getActivity(), Job_Filter_Activity.class).putExtra("type", 0));
                break;
            case R.id.rec_job:
                startActivity(new Intent(getActivity(), Job_Filter_Activity.class).putExtra("type", 1));
                break;

            case R.id.setting:
                startActivity(new Intent(getActivity(), Employee_Setting_Activity.class));
                break;
            case R.id.review:
                startActivity(new Intent(getActivity(), Employee_Review_Activity.class));
                break;
            case R.id.resume_send:
                startActivity(new Intent(getActivity(), Employee_Apply_Activity.class));
                break;
        }
    }
}