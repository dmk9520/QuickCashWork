package com.example.findmyschool.Activity.Employee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.EmployeeExperienceAdapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Job;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeExperienceActivity extends AppCompatActivity {
    private ImageView iv_back;
    private LinearLayout ll_fresher, ll_experience;
    private RelativeLayout rl_experience;
    public static LinearLayout ll_nodata;
    private LinearLayout ll_experiencedata;
    private RecyclerView rv_experience;
    private TextView tv_add;
    private TextView tv_submit;
    private LinearLayoutManager layoutManager;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int day2 = 0;
    private int month2 = 0;
    private int year2 = 0;

    public static ArrayList<Job> experiences = new ArrayList<>();
    private EmployeeExperienceAdapter adapter;
    private TextView et_date, et_enddate;
    int select = 1;
    private int type;
    private Employees emp;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_experience);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initData();
        initClick();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            emp = Application.getEmployee();
            if (emp.getJob() != null) {
                experiences.addAll(emp.getJob());
            }
            if (experiences.size() == 0) {
                ll_nodata.setVisibility(View.VISIBLE);
            } else {
                ll_nodata.setVisibility(View.GONE);
            }
            if (emp.getExperince().equals("Fresher")) {
                selectedView(ll_fresher, ll_experience);
                rl_experience.setVisibility(View.GONE);
                select = 0;
            } else {
                selectedView(ll_experience, ll_fresher);
                rl_experience.setVisibility(View.VISIBLE);
                select = 1;
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initClick() {
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddExperienceDialog();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll_fresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_fresher, ll_experience);
                rl_experience.setVisibility(View.GONE);
                select = 0;
            }
        });
        ll_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_experience, ll_fresher);
                rl_experience.setVisibility(View.VISIBLE);
                select = 1;
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {
                    if (experiences.size() == 0) {
                        Toast.makeText(EmployeeExperienceActivity.this, "Add previous job details...!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (type == 1) {
                        emp.setJob(experiences);
                        emp.setExperince("Experience");
                    } else {
                        employees.setJob(experiences);
                        employees.setExperince("Experience");
                    }
                } else {

                    if (type == 1) {
                        emp.setJob(experiences);
                        emp.setExperince("Fresher");
                    } else {
                        employees.setJob(experiences);
                        employees.setExperince("Fresher");
                    }
                }
                if (type == 1) {
                    databaseReference.getRoot().child("Employees").child(emp.getId()).setValue(emp);
                    Application.setEmployee(emp);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    startActivity(new Intent(EmployeeExperienceActivity.this, EmployeeEducationMainActivity.class));
                }
            }
        });
    }

    private void showAddExperienceDialog() {
        final Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_add_company);
        Button dialog_submit = (Button) dialog.findViewById(R.id.dialog_submit);
        EditText et_company_name = (EditText) dialog.findViewById(R.id.et_company_name);
        EditText et_designation = (EditText) dialog.findViewById(R.id.et_designation);
        et_date = (TextView) dialog.findViewById(R.id.et_date);
        et_enddate = (TextView) dialog.findViewById(R.id.et_enddate);
        CardView cv_start_date = (CardView) dialog.findViewById(R.id.cv_start_date);
        CardView cv_end_date = (CardView) dialog.findViewById(R.id.cv_end_date);
        CheckBox cb_working = (CheckBox) dialog.findViewById(R.id.cb_working);

        et_enddate.setText(getcurrentDateAndTime());

        cb_working.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cv_end_date.setVisibility(View.GONE);
                } else {
                    cv_end_date.setVisibility(View.VISIBLE);
                }
            }
        });

        cv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog("start");
            }
        });

        cv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog("end");
            }
        });

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_company_name.getText().toString())) {
                    Toast.makeText(EmployeeExperienceActivity.this, "Enter company name..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_designation.getText().toString())) {

                    Toast.makeText(EmployeeExperienceActivity.this, "Enter designation name..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_date.getText().toString())) {
                    Toast.makeText(EmployeeExperienceActivity.this, "Select start date..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cb_working.isChecked()) {
                    if (TextUtils.isEmpty(et_enddate.getText().toString())) {
                        Toast.makeText(EmployeeExperienceActivity.this, "Select end date..!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Job experience = new Job();
                experience.setId(String.valueOf(experiences.size()));
                experience.setCname(et_company_name.getText().toString());
                experience.setDesignation(et_designation.getText().toString());
                experience.setSdate(et_date.getText().toString());
                if (cb_working.isChecked()) {
                    experience.setEdate("Present");
                } else {
                    experience.setEdate(et_enddate.getText().toString());
                }
                experiences.add(experience);
                adapter.setExperiences(experiences);
                if (experiences.size() == 0) {
                    ll_nodata.setVisibility(View.VISIBLE);
                } else {
                    ll_nodata.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static String getcurrentDateAndTime() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }


    private void showDatePickerDialog(String call) {
        if (call.equalsIgnoreCase("start")) {
            if (day == 0) {
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
            }
            new DatePickerDialog(this, datePickerListener, year, month, day).show();
        } else {
            if (day == 2) {
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
            }
            new DatePickerDialog(this, datePickerListener2, year2, month2, day2).show();
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            if (et_date != null) {
                et_date.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
            }
        }
    };


    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day2 = selectedDay;
            month2 = selectedMonth;
            year2 = selectedYear;
            if (et_enddate != null) {
                et_enddate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
            }
        }
    };


    private void initData() {
        layoutManager = new LinearLayoutManager(EmployeeExperienceActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_experience.setLayoutManager(layoutManager);
        adapter = new EmployeeExperienceAdapter(this, experiences);
        rv_experience.setAdapter(adapter);
        if (experiences.size() == 0) {
            ll_nodata.setVisibility(View.VISIBLE);
        } else {
            ll_nodata.setVisibility(View.GONE);
        }
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_fresher = (LinearLayout) findViewById(R.id.ll_fresher);
        ll_experience = (LinearLayout) findViewById(R.id.ll_experience);
        ll_nodata = (LinearLayout) findViewById(R.id.ll_nodata);
        ll_experiencedata = (LinearLayout) findViewById(R.id.ll_experiencedata);
        rl_experience = (RelativeLayout) findViewById(R.id.rl_experience);
        rv_experience = (RecyclerView) findViewById(R.id.rv_experience);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }

    void selectedView(LinearLayout view1, LinearLayout view2) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
    }
}