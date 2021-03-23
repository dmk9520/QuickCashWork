package com.example.findmyschool.Activity.Recruiter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Location_Get_Activity;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import static com.example.findmyschool.Common.Constant.recruiter;

public class Edit_Recruiter_Activity extends AppCompatActivity {

    private EditText et_company_name;
    private EditText et_person_name;
    private EditText et_email;
    private EditText et_address;
    private LinearLayout cv_location;
    private TextView tv_location;
    private EditText et_industry_type;
    private EditText et_no_of_employees;
    private EditText et_year;
    private EditText et_description;
    private TextView tv_submit;
    static final Integer LOCATION_REQUEST = 44;
    private EditText tv_city;
    private EditText tv_state;
    private EditText tv_country;
    private EditText et_benefits;
    private DatabaseReference databaseReference;
    private Recruiter recruiter;
    private ImageView iv_back;
    private EditText et_vision;
    private EditText et_mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recruiter);
        recruiter = Application.getRecruiter();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initClick();
        initData();
    }

    private void initData() {
        et_company_name.setText(recruiter.getName());
        et_person_name.setText(recruiter.getRecruitername());
        et_email.setText(recruiter.getGmail());
        et_address.setText(recruiter.getAddress());
        tv_city.setText(recruiter.getCity());
        tv_state.setText(recruiter.getState());
        tv_country.setText(recruiter.getCountry());
        et_industry_type.setText(recruiter.getIndsturytype());
        et_year.setText(recruiter.getEstablished());
        tv_location.setText(recruiter.getLatlong());
        et_description.setText(recruiter.getDescription());
        et_no_of_employees.setText(recruiter.getNoemployees());
        et_benefits.setText(recruiter.getBenefits());
        et_vision.setText(recruiter.getVision());
        et_mission.setText(recruiter.getMission());
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        cv_location = (LinearLayout) findViewById(R.id.cv_location);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_person_name = (EditText) findViewById(R.id.et_person_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_address = (EditText) findViewById(R.id.et_Address);
        tv_city = (EditText) findViewById(R.id.tv_city);
        tv_state = (EditText) findViewById(R.id.tv_state);
        tv_country = (EditText) findViewById(R.id.tv_country);
        et_industry_type = (EditText) findViewById(R.id.et_industry_type);
        et_year = (EditText) findViewById(R.id.et_year);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        et_description = (EditText) findViewById(R.id.et_description);
        et_no_of_employees = (EditText) findViewById(R.id.et_no_of_employees);
        et_benefits = (EditText) findViewById(R.id.et_benefits);
        et_vision = (EditText) findViewById(R.id.et_vision);
        et_mission = (EditText) findViewById(R.id.et_mission);
    }


    private void initClick() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Edit_Recruiter_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Edit_Recruiter_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Edit_Recruiter_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1111);
                } else {
                    startActivityForResult(new Intent(Edit_Recruiter_Activity.this, Location_Get_Activity.class), LOCATION_REQUEST);
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_company_name.getText().toString().trim();
                String recruiter_name = et_person_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String latlong = tv_location.getText().toString().trim();
                String city = tv_city.getText().toString().trim();
                String state = tv_state.getText().toString().trim();
                String country = tv_country.getText().toString().trim();
                String industry_type = et_industry_type.getText().toString().trim();
                String no_employee = et_no_of_employees.getText().toString().trim();
                String established = et_year.getText().toString().trim();
                String des = et_description.getText().toString().trim();
                String benefit = et_benefits.getText().toString().trim();
                String vision = et_vision.getText().toString().trim();
                String mission = et_mission.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    et_company_name.setError("Enter Company Name...!");
                    return;
                }
                if (TextUtils.isEmpty(recruiter_name)) {
                    et_company_name.setError("Enter Recruiter Name...!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Enter Email...!");
                    return;
                }
                if (latlong.equals("Location")) {
                    Toast.makeText(Edit_Recruiter_Activity.this, "Select Location...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    et_address.setError("Enter Address...!");
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    tv_city.setError("Enter city...!");
                    return;
                }
                if (TextUtils.isEmpty(state)) {
                    tv_state.setError("Enter state...!");
                    return;
                }
                if (TextUtils.isEmpty(country)) {
                    tv_country.setError("Enter country...!");
                    return;
                }

                if (TextUtils.isEmpty(industry_type)) {
                    et_industry_type.setError("Enter industry type...!");
                    return;
                }
                if (TextUtils.isEmpty(no_employee)) {
                    et_no_of_employees.setError("Enter no employee...!");
                    return;
                }
                if (TextUtils.isEmpty(established)) {
                    et_year.setError("Enter established year...!");
                    return;
                }
                if (TextUtils.isEmpty(des)) {
                    et_description.setError("Enter description...!");
                    return;
                }
                if (TextUtils.isEmpty(benefit)) {
                    et_benefits.setError("Enter benefits...!");
                    return;
                }

                recruiter.setName(name);
                recruiter.setRecruitername(recruiter_name);
                recruiter.setGmail(email);
                recruiter.setLatlong(latlong);
                recruiter.setAddress(address);
                recruiter.setCity(city);
                recruiter.setState(state);
                recruiter.setCountry(country);
                recruiter.setIndsturytype(industry_type);
                recruiter.setEstablished(established);
                recruiter.setDescription(des);
                recruiter.setNoemployees(no_employee);
                recruiter.setBenefits(benefit);
                recruiter.setVision(vision);
                recruiter.setMission(mission);
                databaseReference.getRoot().child("Recruiter").child(recruiter.getId()).setValue(recruiter);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Recruiter recruiter1 = snapshot.child("Recruiter").child(recruiter.getId()).getValue(Recruiter.class);
                        Application.setRecruiter(recruiter1);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION_REQUEST) {
                tv_location.setText(data.getStringExtra("lat_long"));
                tv_city.setText(data.getStringExtra("city"));
                tv_state.setText(data.getStringExtra("state"));
                tv_country.setText(data.getStringExtra("country"));
                et_address.setText(data.getStringExtra("address"));
            }
        }
    }
}