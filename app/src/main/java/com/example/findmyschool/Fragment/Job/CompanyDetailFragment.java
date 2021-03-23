package com.example.findmyschool.Fragment.Job;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.findmyschool.Common.OnNextPreviousClick;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;

import static com.example.findmyschool.Activity.Recruiter.RecruiterJobPostActivity.job;

public class CompanyDetailFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText et_company_name;
    private EditText et_person_name;
    private EditText et_email;
    private EditText et_phonenumber;
    private EditText et_address;
    OnNextPreviousClick onNextPreviousClick;
    private LinearLayout ll_next;
    private LinearLayout ll_previous;
    int type;


    public CompanyDetailFragment() {
        // Required empty public constructor
    }

    public CompanyDetailFragment(int type, OnNextPreviousClick onNextPreviousClick) {
        this.type = type;
        this.onNextPreviousClick = onNextPreviousClick;
    }

    public static CompanyDetailFragment newInstance(String param1, String param2) {
        CompanyDetailFragment fragment = new CompanyDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_detail, container, false);
        initView(view);
        setData();
        onClick();
        return view;
    }

    private void setData() {
        if (type == 0) {
            et_company_name.setText(Application.getRecruiter().getName());
            et_person_name.setText(Application.getRecruiter().getRecruitername());
            et_email.setText(Application.getRecruiter().getGmail());
            et_phonenumber.setText(Application.getRecruiter().getMobile());
            et_address.setText(Application.getRecruiter().getAddress());
        } else {
            et_company_name.setText(job.getCompanyname());
            et_person_name.setText(job.getContactperson());
            et_email.setText(job.getEmail());
            et_phonenumber.setText(job.getNumber());
            et_address.setText(job.getJobaddress());
        }
    }

    private void onClick() {
        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company_name = et_company_name.getText().toString().trim();
                String person_name = et_person_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String number = et_phonenumber.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                if (TextUtils.isEmpty(company_name)) {
                    et_company_name.setError("Enter company name...!");
                    return;
                }
                if (TextUtils.isEmpty(person_name)) {
                    et_company_name.setError("Enter contact person name...!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    et_company_name.setError("Enter email...!");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    et_company_name.setError("Enter contact number...!");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    et_company_name.setError("Enter interview address...!");
                    return;
                }
                job.setCompanyname(company_name);
                job.setContactperson(person_name);
                job.setEmail(email);
                job.setNumber(number);
                job.setJobaddress(address);
                job.setFind("true");
                job.setLatlong(Application.getRecruiter().getLatlong());
                job.setRecruiterid(Application.getRecruiter().getId());
                job.setProfile(Application.getRecruiter().getProfile());
                job.setJoblocality(job.getJobcity());
                onNextPreviousClick.onNextClick();
            }
        });
        ll_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextPreviousClick.onPreviousClick();
            }
        });
    }

    private void initView(View view) {
        ll_next = (LinearLayout) view.findViewById(R.id.ll_next);
        ll_previous = (LinearLayout) view.findViewById(R.id.ll_previous);
        et_company_name = (EditText) view.findViewById(R.id.et_company_name);
        et_person_name = (EditText) view.findViewById(R.id.et_person_name);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_phonenumber = (EditText) view.findViewById(R.id.et_phonenumber);
        et_address = (EditText) view.findViewById(R.id.et_address);
    }
}