package com.example.findmyschool.Activity.Employee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmyschool.Adapter.EmployeeEducationAdapter;
import com.example.findmyschool.Model.Education;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeEducationMainActivity extends AppCompatActivity {

    private TextView tv_add;
    private TextView tv_submit;
    public static ImageView iv_nodata;
    private ImageView iv_back;
    private RecyclerView rv_education;
    public static ArrayList<Education> educations = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private EmployeeEducationAdapter adapter;
    private int type;
    private Employees emp;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_education_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initData();
        initClick();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            emp = Application.getEmployee();
            if (emp.getEducation() != null) {
                educations.addAll(emp.getEducation());
            }
            if (educations.size() == 0) {
                iv_nodata.setVisibility(View.VISIBLE);
            } else {
                iv_nodata.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initClick() {
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEducationDialog();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    employees.setEducation(educations);
                    startActivity(new Intent(EmployeeEducationMainActivity.this, EmployeeInterestActivity.class).putExtra("type", 0));
                } else {
                    emp.setEducation(educations);
                    databaseReference.getRoot().child("Employees").child(emp.getId()).setValue(emp);
                    Application.setEmployee(emp);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        rv_education = (RecyclerView) findViewById(R.id.rv_education);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        iv_nodata = (ImageView) findViewById(R.id.iv_nodata);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }


    public void showAddEducationDialog() {
        final Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_add_education);
        Button dialog_submit = (Button) dialog.findViewById(R.id.dialog_submit);
        EditText et_schoolorclg_name = (EditText) dialog.findViewById(R.id.et_schoolorclg_name);
        EditText et_stdordegree = (EditText) dialog.findViewById(R.id.et_stdordegree);
        EditText et_boardoruniversity = (EditText) dialog.findViewById(R.id.et_boardoruniversity);
        EditText et_start_year = (EditText) dialog.findViewById(R.id.et_start_year);
        EditText et_end_year = (EditText) dialog.findViewById(R.id.et_end_year);
        EditText et_perorcgpa = (EditText) dialog.findViewById(R.id.et_perorcgpa);

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_schoolorclg_name.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Enter school name..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_stdordegree.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Enter degree name..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_boardoruniversity.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Select board date..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_start_year.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Select start date..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_end_year.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Select end date..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_perorcgpa.getText().toString())) {
                    Toast.makeText(EmployeeEducationMainActivity.this, "Enter marks..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Education education = new Education();
                education.setId(String.valueOf(educations.size()));
                education.setSchoolname(et_schoolorclg_name.getText().toString());
                education.setLevel(et_stdordegree.getText().toString());
                education.setBoard(et_boardoruniversity.getText().toString());
                education.setSdate(et_start_year.getText().toString());
                education.setEdate(et_end_year.getText().toString());
                education.setMark(et_perorcgpa.getText().toString());
                educations.add(education);
                adapter.setEducations(educations);
                if (educations.size() == 0) {
                    iv_nodata.setVisibility(View.VISIBLE);
                } else {
                    iv_nodata.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initData() {
        layoutManager = new LinearLayoutManager(EmployeeEducationMainActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_education.setLayoutManager(layoutManager);
        adapter = new EmployeeEducationAdapter(this, educations);
        rv_education.setAdapter(adapter);
        if (educations.size() == 0) {
            iv_nodata.setVisibility(View.VISIBLE);
        } else {
            iv_nodata.setVisibility(View.GONE);
        }
    }


}