package com.example.findmyschool.Fragment.Employee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.Employee_Job_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.PostJobs;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Employee_Job_Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<PostJobs> jobs = new ArrayList<>();
    ArrayList<PostJobs> all_jobs = new ArrayList<>();
    private RecyclerView job_recycler;
    private ProgressBar progress;
    private Employees employee;
    private Employee_Job_Adapter employee_job_adapter;
    private ImageView filter;
    private TextView job_dates;
    private CardView search_lay;
    private EditText search_text;
    double upto_salary = 10000;

    public Employee_Job_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employee_job, container, false);
        employee = Application.getEmployee();
        initView();
        initListener();
        getJobsData();
        initAdapter();
        return view;
    }

    private void initListener() {
        filter.setOnClickListener(this);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    jobs.clear();
                    for (int i = 0; i < all_jobs.size(); i++) {
                        if (Boolean.parseBoolean(all_jobs.get(i).getFind()) && employee.getInterested().toLowerCase().contains(all_jobs.get(i).getCompanytype().toLowerCase())) {
                            if (checkJobType(all_jobs.get(i))) {
                                if (checkJobDistance(all_jobs.get(i))) {
                                    if (checkJobSalary(all_jobs.get(i))) {
                                        jobs.add(all_jobs.get(i));
                                    }
                                }
                            }
                        }
                    }
                    employee_job_adapter.setData(jobs);
                } else {
                    jobs.clear();
                    for (int i = 0; i < all_jobs.size(); i++) {
                        if (Boolean.parseBoolean(all_jobs.get(i).getFind()) && employee.getInterested().toLowerCase().contains(all_jobs.get(i).getCompanytype().toLowerCase()) && all_jobs.get(i).getJobtitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            if (checkJobType(all_jobs.get(i))) {
                                if (checkJobDistance(all_jobs.get(i))) {
                                    if (checkJobSalary(all_jobs.get(i))) {
                                        jobs.add(all_jobs.get(i));
                                    }
                                }
                            }
                        }
                    }
                    employee_job_adapter.setData(jobs);
                }
            }
        });
    }

    private void getJobsData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobs.clear();
                all_jobs.clear();
                job_recycler.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                if (dataSnapshot.hasChild("Jobs")) {
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Jobs").getChildren()) {
                        PostJobs postJobs = postSnapshot.getValue(PostJobs.class);
                        all_jobs.add(postJobs);
                        String search = search_text.getText().toString().toLowerCase();
                        if (TextUtils.isEmpty(search)) {
                            if (Boolean.parseBoolean(postJobs.getFind()) && employee.getInterested().toLowerCase().contains(postJobs.getCompanytype().toLowerCase())) {
                                if (checkJobType(postJobs)) {
                                    if (checkJobDistance(postJobs)) {
                                        if (checkJobSalary(postJobs)) {
                                            jobs.add(postJobs);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (Boolean.parseBoolean(postJobs.getFind()) && employee.getInterested().toLowerCase().contains(postJobs.getCompanytype().toLowerCase()) && postJobs.getJobtitle().toLowerCase().contains(search)) {
                                if (checkJobType(postJobs)) {
                                    if (checkJobDistance(postJobs)) {
                                        if (checkJobSalary(postJobs)) {
                                            jobs.add(postJobs);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    progress.setVisibility(View.GONE);
                    search_lay.setVisibility(View.VISIBLE);
                    job_recycler.setVisibility(View.VISIBLE);
                    if (jobs.size() == 0) {
                        Toast.makeText(getActivity(), "Jobs not founds...!", Toast.LENGTH_SHORT).show();
                    }
                    employee_job_adapter.setData(jobs);
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Jobs not founds...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean checkJobType(PostJobs postJobs) {
        if (job_type_list[job_type].toLowerCase().equals(postJobs.getJobtype().toLowerCase())) {
            if (job_type != 2) {
                return true;
            } else {
                if (TextUtils.isEmpty(job_date)) {
                    return true;
                } else {
                    if (job_date.equals(postJobs.getJobdate())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkJobDistance(PostJobs postJobs) {
        String[] temp = employee.getLatlong().split(",");
        String[] temp1 = postJobs.getLatlong().split(",");
        double lat1 = Double.parseDouble(temp[0]);
        double lon1 = Double.parseDouble(temp[1]);
        double lat2 = Double.parseDouble(temp1[0]);
        double lon2 = Double.parseDouble(temp1[1]);
        if (job_distance == 3) {
            if (employee.getCity().toLowerCase().equals(postJobs.getJobcity().toLowerCase())) {
                return true;
            }
        } else if (job_distance == 2) {
            if (distance(lat1, lon1, lat2, lon2) <= 20) {
                return true;
            }
        } else if (job_distance == 1) {
            if (distance(lat1, lon1, lat2, lon2) <= 10) {
                return true;
            }
        } else if (job_distance == 0) {
            if (distance(lat1, lon1, lat2, lon2) <= 5) {
                return true;
            }
        }
        return false;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return milesTokm(dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private double milesTokm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }

    String[] job_type_list = new String[]{"All Day", "Part time", "Mandatory"};

    private void initAdapter() {
        employee_job_adapter = new Employee_Job_Adapter(getActivity(), jobs);
        job_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        job_recycler.setAdapter(employee_job_adapter);
    }

    private void initView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        job_recycler = (RecyclerView) view.findViewById(R.id.job_recycler);
        search_lay = (CardView) view.findViewById(R.id.search_lay);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        filter = (ImageView) view.findViewById(R.id.filter);
        search_text = (EditText) view.findViewById(R.id.search_text);
    }


    int job_type = 0;
    int job_distance = 3;
    String job_date;
    int[] job_type_id = new int[]{R.id.job_all, R.id.job_part, R.id.job_men};
    int[] job_distance_id = new int[]{R.id.km_5, R.id.km_10, R.id.km_20, R.id.full_city};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter:
                final Dialog filterDialog = new Dialog(getActivity(), R.style.WideDialog);
                filterDialog.setContentView(R.layout.dialog_employee_filter);
                filterDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                RadioGroup job_types = (RadioGroup) filterDialog.findViewById(R.id.job_type);
                RadioGroup job_distances = (RadioGroup) filterDialog.findViewById(R.id.job_distance);
                Button apply = (Button) filterDialog.findViewById(R.id.apply);
                LinearLayout date_lay = (LinearLayout) filterDialog.findViewById(R.id.date_lay);
                EditText job_salary = (EditText) filterDialog.findViewById(R.id.job_salary);
                job_dates = (TextView) filterDialog.findViewById(R.id.job_date);
                job_salary.setText(String.valueOf(upto_salary));
                ImageView calender = (ImageView) filterDialog.findViewById(R.id.calender);
                job_types.check(job_type_id[job_type]);
                job_distances.check(job_distance_id[job_distance]);
                if (job_type == 2) {
                    date_lay.setVisibility(View.VISIBLE);
                    job_dates.setText(job_date);
                } else {
                    date_lay.setVisibility(View.GONE);
                }
                job_types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.job_men) {
                            date_lay.setVisibility(View.VISIBLE);
                        } else {
                            date_lay.setVisibility(View.GONE);
                        }
                    }
                });
                calender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp_salary = job_salary.getText().toString().trim();
                        if (TextUtils.isEmpty(temp_salary)) {
                            Toast.makeText(getActivity(), "Enter expected salary...!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        upto_salary = Double.parseDouble(temp_salary);
                        if (job_types.getCheckedRadioButtonId() == R.id.job_all) {
                            job_type = 0;
                        } else if (job_types.getCheckedRadioButtonId() == R.id.job_part) {
                            job_type = 1;
                        } else if (job_types.getCheckedRadioButtonId() == R.id.job_men) {
                            job_type = 2;
                            /*if (TextUtils.isEmpty(job_dates.getText().toString().trim())) {
                                Toast.makeText(getActivity(), "Select Job Date...!", Toast.LENGTH_SHORT).show();
                                return;
                            }*/
                            job_date = job_dates.getText().toString().trim();
                        }
                        if (job_distances.getCheckedRadioButtonId() == R.id.km_5) {
                            job_distance = 0;
                        } else if (job_distances.getCheckedRadioButtonId() == R.id.km_10) {
                            job_distance = 1;
                        } else if (job_distances.getCheckedRadioButtonId() == R.id.km_20) {
                            job_distance = 2;
                        } else if (job_distances.getCheckedRadioButtonId() == R.id.full_city) {
                            job_distance = 3;
                        }
                        jobs.clear();
                        for (int i = 0; i < all_jobs.size(); i++) {
                            String search = search_text.getText().toString().toLowerCase();
                            if (TextUtils.isEmpty(search)) {
                                if (Boolean.parseBoolean(all_jobs.get(i).getFind()) && employee.getInterested().toLowerCase().contains(all_jobs.get(i).getCompanytype().toLowerCase())) {
                                    if (checkJobType(all_jobs.get(i))) {
                                        if (checkJobDistance(all_jobs.get(i))) {
                                            if (checkJobSalary(all_jobs.get(i))) {
                                                jobs.add(all_jobs.get(i));
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (Boolean.parseBoolean(all_jobs.get(i).getFind()) && employee.getInterested().toLowerCase().contains(all_jobs.get(i).getCompanytype().toLowerCase()) && all_jobs.get(i).getJobtitle().toLowerCase().contains(search)) {
                                    if (checkJobType(all_jobs.get(i))) {
                                        if (checkJobDistance(all_jobs.get(i))) {
                                            if (checkJobSalary(all_jobs.get(i))) {
                                                jobs.add(all_jobs.get(i));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        employee_job_adapter.setData(jobs);
                        filterDialog.dismiss();
                    }
                });
                filterDialog.show();
                break;
        }
    }

    private boolean checkJobSalary(PostJobs postJobs) {
        String[] temp = postJobs.getSalary().split("to");
        if (Double.parseDouble(temp[0].trim()) >= upto_salary || Double.parseDouble(temp[1].trim()) >= upto_salary) {
            return true;
        } else {
            return false;
        }
    }

    int day;
    int month;
    int year;

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        new DatePickerDialog(getActivity(), datePickerListener, year, month, day).show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            if (job_dates != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                calendar.set(Calendar.MONTH, selectedMonth);
                calendar.set(Calendar.YEAR, selectedYear);
                String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(calendar.getTimeInMillis()));
                job_dates.setText(dateString);
            }
        }
    };
}