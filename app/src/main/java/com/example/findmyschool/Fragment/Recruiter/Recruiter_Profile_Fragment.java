package com.example.findmyschool.Fragment.Recruiter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.findmyschool.Activity.Employee.Employee_profile_Activity;
import com.example.findmyschool.Activity.Recruiter.Recruiter_Profile_Activity;
import com.example.findmyschool.Activity.Recruiter.Recruiter_Resume_Activity;
import com.example.findmyschool.Activity.Recruiter.Recruiter_Review_Activity;
import com.example.findmyschool.Activity.Recruiter.Recruiter_Setting_Activity;
import com.example.findmyschool.R;

public class Recruiter_Profile_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout profile;
    private LinearLayout setting;
    private LinearLayout review;
    private LinearLayout resume;

    public Recruiter_Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recruiter_profile, container, false);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        profile.setOnClickListener(this);
        setting.setOnClickListener(this);
        review.setOnClickListener(this);
        resume.setOnClickListener(this);
    }

    private void initView() {
        profile = (LinearLayout) view.findViewById(R.id.profile);
        setting = (LinearLayout) view.findViewById(R.id.setting);
        review = (LinearLayout) view.findViewById(R.id.review);
        resume = (LinearLayout) view.findViewById(R.id.resume);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                startActivity(new Intent(getActivity(), Recruiter_Profile_Activity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), Recruiter_Setting_Activity.class));
                break;
            case R.id.review:
                startActivity(new Intent(getActivity(), Recruiter_Review_Activity.class));
                break;
            case R.id.resume:
                startActivity(new Intent(getActivity(), Recruiter_Resume_Activity.class));
                break;
        }
    }
}