package com.example.findmyschool.Activity.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findmyschool.R;

import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeEducationActivity extends AppCompatActivity {

    private LinearLayout ll_below_10, ll_10_pass, ll_12_pass_above, ll_graducate_above;
    private LinearLayout ll_english, ll_hindi, ll_gujarati, ll_other;
    private LinearLayout ll_no_english, ll_little_english, ll_good_english;
    private TextView tv_submit;
    private ImageView iv_back;
    String education = "Below 10th";
    String medium = "English";
    String english = "No English";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_education);
        initView();
        initClick();
    }

    private void initClick() {
        ll_below_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_below_10, ll_10_pass, ll_12_pass_above, ll_graducate_above);
                education = "Below 10th";
            }
        });
        ll_10_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_10_pass, ll_below_10, ll_12_pass_above, ll_graducate_above);
                education = "10th Pass & Above";
            }
        });
        ll_12_pass_above.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_12_pass_above, ll_10_pass, ll_below_10, ll_graducate_above);
                education = "12th Pass & Above";
            }
        });
        ll_graducate_above.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_graducate_above, ll_12_pass_above, ll_10_pass, ll_below_10);
                education = "Graduate & Above";
            }
        });
        ll_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_english, ll_hindi, ll_gujarati, ll_other);
                medium = "English";
            }
        });
        ll_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_hindi, ll_english, ll_gujarati, ll_other);
                medium = "Hindi";
            }
        });
        ll_gujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_gujarati, ll_english, ll_hindi, ll_other);
                medium = "Gujarati";
            }
        });
        ll_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_other, ll_english, ll_hindi, ll_gujarati);
                medium = "Other";
            }
        });
        ll_good_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_good_english, ll_little_english, ll_no_english);
                english = "Good English";
            }
        });
        ll_little_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_little_english, ll_good_english, ll_no_english);
                english = "Little English";
            }
        });
        ll_no_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedView(ll_no_english, ll_good_english, ll_little_english);
                english = "No English";
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employees.setQualification(education);
                employees.setMedium(medium);
                employees.setSpeak(english);
                startActivity(new Intent(EmployeeEducationActivity.this, EmployeeCertificateActivity.class));
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
        ll_below_10 = (LinearLayout) findViewById(R.id.ll_below_10);
        ll_10_pass = (LinearLayout) findViewById(R.id.ll_10_pass);
        ll_12_pass_above = (LinearLayout) findViewById(R.id.ll_12_pass_above);
        ll_graducate_above = (LinearLayout) findViewById(R.id.ll_graducate_above);
        ll_english = (LinearLayout) findViewById(R.id.ll_english);
        ll_hindi = (LinearLayout) findViewById(R.id.ll_hindi);
        ll_gujarati = (LinearLayout) findViewById(R.id.ll_gujarati);
        ll_other = (LinearLayout) findViewById(R.id.ll_other);
        ll_no_english = (LinearLayout) findViewById(R.id.ll_no_english);
        ll_little_english = (LinearLayout) findViewById(R.id.ll_little_english);
        ll_good_english = (LinearLayout) findViewById(R.id.ll_good_english);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    void selectedView(LinearLayout view1, LinearLayout view2, LinearLayout view3, LinearLayout view4) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
        view3.setBackground(getResources().getDrawable(R.drawable.unselected));
        view4.setBackground(getResources().getDrawable(R.drawable.unselected));
    }

    void selectedView(LinearLayout view1, LinearLayout view2, LinearLayout view3) {
        view1.setBackground(getResources().getDrawable(R.drawable.selected));
        view2.setBackground(getResources().getDrawable(R.drawable.unselected));
        view3.setBackground(getResources().getDrawable(R.drawable.unselected));
    }
}