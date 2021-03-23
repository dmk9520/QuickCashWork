package com.example.findmyschool.Activity.Employee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Adapter.EmployeeCertificateAdapter;
import com.example.findmyschool.Model.Certificate;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeCertificateActivity extends AppCompatActivity {

    private RecyclerView rv_certificate;
    private TextView tv_add;
    private TextView tv_submit;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private TextView et_date;
    public static ArrayList<Certificate> certificates = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private EmployeeCertificateAdapter adapter;
    public static ImageView iv_nodata;
    private ImageView iv_back;
    private int type;
    private Employees emp;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_certificate);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initData();
        initClick();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            emp = Application.getEmployee();
            if (emp.getCertificate() != null) {
                certificates.addAll(emp.getCertificate());
            }
            if (certificates.size() == 0) {
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
                showAddCertificateDialog();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    employees.setCertificate(certificates);
                    startActivity(new Intent(EmployeeCertificateActivity.this, EmployeeExperienceActivity.class).putExtra("type", 0));
                } else {
                    emp.setCertificate(certificates);
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

    //permission dialog
    public void showAddCertificateDialog() {
        final Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_add_certificate);
        Button dialog_submit = (Button) dialog.findViewById(R.id.dialog_submit);
        EditText et_certificate_name = (EditText) dialog.findViewById(R.id.et_certificate_name);
        EditText et_institute_name = (EditText) dialog.findViewById(R.id.et_institute_name);
        et_date = (TextView) dialog.findViewById(R.id.et_date);
        CardView cv_issue_date = (CardView) dialog.findViewById(R.id.cv_issue_date);

        cv_issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_certificate_name.getText().toString())) {
                    Toast.makeText(EmployeeCertificateActivity.this, "Enter certificate title..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_institute_name.getText().toString())) {

                    Toast.makeText(EmployeeCertificateActivity.this, "Enter institute name..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_date.getText().toString())) {
                    Toast.makeText(EmployeeCertificateActivity.this, "Select date..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Certificate certificate = new Certificate();
                certificate.setCertiname(et_certificate_name.getText().toString());
                certificate.setCertiinsti(et_institute_name.getText().toString());
                certificate.setDate(et_date.getText().toString());
                certificate.setId(String.valueOf(certificates.size()));
                certificates.add(certificate);
                adapter.setCertificates(certificates);
                if (certificates.size() == 0) {
                    iv_nodata.setVisibility(View.VISIBLE);
                } else {
                    iv_nodata.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDatePickerDialog() {
        if (day == 0) {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        new DatePickerDialog(this, datePickerListener, year, month, day).show();
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


    private void initData() {
        layoutManager = new LinearLayoutManager(EmployeeCertificateActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_certificate.setLayoutManager(layoutManager);
        adapter = new EmployeeCertificateAdapter(this, certificates);
        rv_certificate.setAdapter(adapter);
        if (certificates.size() == 0) {
            iv_nodata.setVisibility(View.VISIBLE);
        } else {
            iv_nodata.setVisibility(View.GONE);
        }
    }

    private void initView() {
        rv_certificate = (RecyclerView) findViewById(R.id.rv_certificate);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        iv_nodata = (ImageView) findViewById(R.id.iv_nodata);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }
}