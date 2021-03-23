package com.example.findmyschool.Fragment.Job;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Postjob_Fragment extends Fragment implements View.OnClickListener {

    PostJobs post_job;
    private View view;
    private TextView job_title;
    private TextView job_salary;
    private TextView job_location;
    private TextView job_experince;
    private TextView job_qualification;
    private TextView job_language;
    private TextView job_info;
    private TextView job_requirement;
    private TextView job_timing;
    private TextView job_address;
    private TextView interview_time;
    private TextView job_opening;
    private TextView contact_person;
    private Button call_hr;
    private ImageView faviourte;
    private Employees employee;
    private DatabaseReference databaseReference;
    private Button email_send;

    public Postjob_Fragment() {
        // Required empty public constructor
    }

    public Postjob_Fragment(PostJobs post_job) {
        this.post_job = post_job;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_postjob, container, false);
        employee = Application.getEmployee();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        setData();
        initListener();
        return view;
    }

    private void initListener() {
        call_hr.setOnClickListener(this);
        email_send.setOnClickListener(this);
        faviourte.setOnClickListener(this);
    }

    private void setData() {
        job_title.setText(post_job.getJobtitle());
        job_salary.setText(post_job.getSalary());
        job_location.setText(post_job.getJoblocality());
        job_experince.setText(post_job.getWorkexperience());
        job_qualification.setText(post_job.getQualification());
        job_language.setText(post_job.getLanguage());
        String arr[] = post_job.getJobrole().split("\\.");
        StringBuilder desc = new StringBuilder();
        for (String s : arr) {
            if (desc.length() == 0) {
                desc.append("•  " + s);
            } else {
                desc.append("\n• " + s);
            }
        }
        job_info.setText(desc.toString());

        job_requirement.setText(post_job.getSkills());
        if (post_job.getJobtime() != null) {
            if (!post_job.getJobtime().isEmpty()) {
                job_timing.setText(post_job.getJobtime());
            } else {
                job_timing.setText("--");
            }
        } else {
            job_timing.setText("--");
        }
        job_address.setText(post_job.getJobaddress());
        interview_time.setText(post_job.getInterviewtime());
        job_opening.setText(post_job.getStaff());
        contact_person.setText(post_job.getContactperson());
        if (employee.getFavjob() != null) {
            String[] temp = employee.getFavjob().split(",");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].equals(post_job.getId())) {
                    faviourte.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.iv_fill_heart));
                    fav = true;
                    break;
                } else {
                    fav = false;
                    faviourte.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.iv_heart));
                }
            }
        } else {
            fav = false;
            faviourte.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.iv_heart));
        }

    }

    private void initView() {
        job_title = (TextView) view.findViewById(R.id.job_title);
        job_salary = (TextView) view.findViewById(R.id.job_salary);
        job_location = (TextView) view.findViewById(R.id.job_location);
        job_experince = (TextView) view.findViewById(R.id.job_experince);
        job_qualification = (TextView) view.findViewById(R.id.job_qualification);
        job_language = (TextView) view.findViewById(R.id.job_language);
        job_info = (TextView) view.findViewById(R.id.job_info);
        job_requirement = (TextView) view.findViewById(R.id.job_requirement);
        job_timing = (TextView) view.findViewById(R.id.job_timing);
        job_address = (TextView) view.findViewById(R.id.job_address);
        interview_time = (TextView) view.findViewById(R.id.interview_time);
        job_opening = (TextView) view.findViewById(R.id.job_opening);
        contact_person = (TextView) view.findViewById(R.id.contact_person);
        call_hr = (Button) view.findViewById(R.id.call_hr);
        email_send = (Button) view.findViewById(R.id.email_send);
        faviourte = (ImageView) view.findViewById(R.id.faviourte);
    }

    boolean fav = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_hr:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + post_job.getNumber()));
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 100);
                }
                break;
            case R.id.email_send:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{post_job.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Apply Job");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.faviourte:
                ArrayList<String> temp = new ArrayList<>();
                if (employee.getFavjob() != null) {
                    for (int i = 0; i < employee.getFavjob().split(",").length; i++) {
                        temp.add(employee.getFavjob().split(",")[i]);
                    }
                }
                String fav_id = "";
                if (fav) {
                    fav = false;
                    faviourte.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.iv_heart));
                    temp.remove(post_job.getId());
                    for (int i = 0; i < temp.size(); i++) {
                        if (TextUtils.isEmpty(fav_id)) {
                            fav_id = temp.get(i);
                        } else {
                            fav_id = fav_id + "," + temp.get(i);
                        }
                    }
                    employee.setFavjob(fav_id);
                    Application.setEmployee(employee);
                    databaseReference.getRoot().child("Employees").child(employee.getId()).child("favjob").setValue(fav_id);
                } else {
                    fav = true;
                    faviourte.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.iv_fill_heart));
                    temp.add(post_job.getId());
                    for (int i = 0; i < temp.size(); i++) {
                        if (TextUtils.isEmpty(fav_id)) {
                            fav_id = temp.get(i);
                        } else {
                            fav_id = fav_id + "," + temp.get(i);
                        }
                    }
                    employee.setFavjob(fav_id);
                    Application.setEmployee(employee);
                    databaseReference.getRoot().child("Employees").child(employee.getId()).child("favjob").setValue(fav_id);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + post_job.getNumber()));
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Allow this permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
