package com.example.findmyschool.Activity.Employee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyschool.Activity.Location_Get_Activity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.example.findmyschool.Common.Constant.employees;


public class Edit_Profile_Activity extends AppCompatActivity {

    private ImageView iv_back;
    private EditText et_name;
    private EditText et_email;
    private CardView cv_birthdate;
    private TextView tv_birthdate;
    private CardView cv_language;
    private TextView tv_language;
    private RadioGroup rg_gender;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private EditText et_Address;
    private EditText et_skill;
    private CardView cv_location;
    private TextView tv_location;
    private EditText et_aboutme;
    private TextView tv_submit;
    static final Integer CAMERA_RESULT = 11;
    static final Integer CAMERA_REQUEST = 22;
    static final Integer GALLERY = 33;
    static final Integer LOCATION_REQUEST = 44;
    private Uri mUri;
    private String mCurrentPhotoPath;
    private Uri imageUri = null;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private EditText et_hobbies;
    private CardView cv_city;
    private CardView cv_state;
    private CardView cv_country;
    private EditText tv_city;
    private EditText tv_state;
    private EditText tv_country;
    private String code;
    private Employees employees;

    private LinearLayout ll_below_10, ll_10_pass, ll_12_pass_above, ll_graducate_above;
    private LinearLayout ll_english, ll_hindi, ll_gujarati, ll_other;
    private LinearLayout ll_no_english, ll_little_english, ll_good_english;
    String education = "Below 10th";
    String medium = "English";
    String english = "No English";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        employees = Application.getEmployee();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initClick();
        setData();
    }

    private void setData() {
        et_name.setText(employees.getName());
        et_email.setText(employees.getEmail());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(employees.getBirth()));
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        tv_birthdate.setText(day + " / " + (month + 1) + " / " + year);
        tv_language.setText(employees.getLanguage());
        if (employees.getGender().equals("Male")) {
            rb_male.setChecked(true);
            rb_female.setChecked(false);
        } else {
            rb_male.setChecked(false);
            rb_female.setChecked(true);
        }
        et_Address.setText(employees.getLocation());
        et_hobbies.setText(employees.getHobbie());
        et_aboutme.setText(employees.getAbout());
        tv_location.setText(employees.getLatlong());
        tv_city.setText(employees.getCity());
        tv_state.setText(employees.getState());
        tv_country.setText(employees.getCountry());
        et_skill.setText(employees.getSkill());
        education = employees.getQualification();
        date = employees.getBirth();
        medium = employees.getMedium();
        english = employees.getSpeak();
        if (education.equals("Below 10th")) {
            selectedView(ll_below_10, ll_10_pass, ll_12_pass_above, ll_graducate_above);
        } else if (education.equals("10th Pass & Above")) {
            selectedView(ll_10_pass, ll_below_10, ll_12_pass_above, ll_graducate_above);
        } else if (education.equals("12th Pass & Above")) {
            selectedView(ll_12_pass_above, ll_10_pass, ll_below_10, ll_graducate_above);
        } else if (education.equals("Graduate & Above")) {
            selectedView(ll_graducate_above, ll_12_pass_above, ll_10_pass, ll_below_10);
        }


        if (medium.equals("English")) {
            selectedView(ll_english, ll_hindi, ll_gujarati, ll_other);
        } else if (medium.equals("Hindi")) {
            selectedView(ll_hindi, ll_english, ll_gujarati, ll_other);
        } else if (medium.equals("Gujarati")) {
            selectedView(ll_gujarati, ll_english, ll_hindi, ll_other);
        } else if (medium.equals("Other")) {
            selectedView(ll_other, ll_english, ll_hindi, ll_gujarati);
        }

        if (english.equals("Good English")) {
            selectedView(ll_good_english, ll_little_english, ll_no_english);
        } else if (english.equals("Little English")) {
            selectedView(ll_little_english, ll_good_english, ll_no_english);
        } else if (english.equals("No English")) {
            selectedView(ll_no_english, ll_good_english, ll_little_english);
        }
    }

    private void initClick() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String birth = tv_birthdate.getText().toString().trim();
                String language = tv_language.getText().toString().trim();
                String gender;
                if (rb_male.isChecked()) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
                String address = et_Address.getText().toString().trim();
                String hobbie = et_hobbies.getText().toString().trim();
                String about = et_aboutme.getText().toString().trim();
                String location = tv_location.getText().toString().trim();
                String city = tv_city.getText().toString().trim();
                String state = tv_state.getText().toString().trim();
                String country = tv_country.getText().toString().trim();
                String skill = et_skill.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("Enter Name...!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Enter Email...!");
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(Edit_Profile_Activity.this, "Select Birthdate...!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (language.equals("Language Known")) {
                    Toast.makeText(Edit_Profile_Activity.this, "Select Language...!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (location.equals("Location")) {
                    Toast.makeText(Edit_Profile_Activity.this, "Select Location...!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    et_Address.setError("Enter Address...!");
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
                if (TextUtils.isEmpty(hobbie)) {
                    et_hobbies.setError("Enter hobbie...!");
                    return;
                }
                if (TextUtils.isEmpty(skill)) {
                    et_skill.setError("Enter skills...!");
                    return;
                }
                if (TextUtils.isEmpty(about)) {
                    et_aboutme.setError("Enter about...!");
                    return;
                }

                employees.setName(name);
                employees.setEmail(email);
                employees.setBirth(date);
                employees.setLanguage(language);
                employees.setGender(gender);
                employees.setLatlong(location);
                employees.setLocation(address);
                employees.setCity(city);
                employees.setState(state);
                employees.setCountry(country);
                employees.setHobbie(hobbie);
                employees.setSkill(skill);
                employees.setAbout(about);
                employees.setQualification(education);
                employees.setMedium(medium);
                employees.setSpeak(english);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                databaseReference.getRoot().child("Employees").child(employees.getId()).setValue(employees);
                Application.setEmployee(employees);
                finish();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cv_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        cv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Edit_Profile_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Edit_Profile_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Edit_Profile_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1111);
                } else {
                    startActivityForResult(new Intent(Edit_Profile_Activity.this, Location_Get_Activity.class), LOCATION_REQUEST);
                }
            }
        });
        cv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguagePickerDialog();
            }
        });

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
                education = "10th Pass";
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

    private void showDatePickerDialog() {
        if (day == 0) {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        new DatePickerDialog(this, datePickerListener, year, month, day).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION_REQUEST) {
                tv_location.setText(data.getStringExtra("lat_long"));
                tv_city.setText(data.getStringExtra("city"));
                tv_state.setText(data.getStringExtra("state"));
                tv_country.setText(data.getStringExtra("country"));
                et_Address.setText(data.getStringExtra("address"));
            }
        }
    }

    String date;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.YEAR, selectedYear);
            date = String.valueOf(calendar.getTimeInMillis());
            tv_birthdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
        }
    };

    private void initView() {
        cv_birthdate = (CardView) findViewById(R.id.cv_birthdate);
        cv_language = (CardView) findViewById(R.id.cv_language);
        cv_location = (CardView) findViewById(R.id.cv_location);
        cv_city = (CardView) findViewById(R.id.cv_city);
        cv_state = (CardView) findViewById(R.id.cv_state);
        cv_country = (CardView) findViewById(R.id.cv_country);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_aboutme = (EditText) findViewById(R.id.et_aboutme);
        et_email = (EditText) findViewById(R.id.et_email);
        et_hobbies = (EditText) findViewById(R.id.et_hobbies);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_city = (EditText) findViewById(R.id.tv_city);
        tv_state = (EditText) findViewById(R.id.tv_state);
        tv_country = (EditText) findViewById(R.id.tv_country);
        et_Address = (EditText) findViewById(R.id.et_Address);
        et_skill = (EditText) findViewById(R.id.et_skill);
        tv_birthdate = (TextView) findViewById(R.id.tv_birthdate);
        tv_language = (TextView) findViewById(R.id.tv_language);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
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
    }

    private void showLanguagePickerDialog() {
        final Dialog languageDialog = new Dialog(this, R.style.WideDialog);
        languageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        languageDialog.setCancelable(true);
        languageDialog.setContentView(R.layout.dialog_language_options);
        languageDialog.setCanceledOnTouchOutside(true);
        languageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tv_ok = languageDialog.findViewById(R.id.tv_ok);
        TextView tv_cancel = languageDialog.findViewById(R.id.tv_cancel);

        CheckBox check_english = languageDialog.findViewById(R.id.check_english);
        CheckBox check_hindi = languageDialog.findViewById(R.id.check_hindi);
        CheckBox check_marathi = languageDialog.findViewById(R.id.check_marathi);
        CheckBox check_gujarati = languageDialog.findViewById(R.id.check_gujarati);
        CheckBox check_panjabi = languageDialog.findViewById(R.id.check_panjabi);
        CheckBox check_bengali = languageDialog.findViewById(R.id.check_bengali);
        CheckBox check_oriya = languageDialog.findViewById(R.id.check_oriya);
        CheckBox check_tamil = languageDialog.findViewById(R.id.check_tamil);
        CheckBox check_telugu = languageDialog.findViewById(R.id.check_telugu);
        CheckBox check_kannada = languageDialog.findViewById(R.id.check_kannada);
        CheckBox check_malayalam = languageDialog.findViewById(R.id.check_malayalam);

        String language = tv_language.getText().toString().trim();
        if (language.contains("English")) {
            check_english.setChecked(true);
        }
        if (language.contains("Hindi")) {
            check_hindi.setChecked(true);
        }
        if (language.contains("Marathi")) {
            check_marathi.setChecked(true);
        }
        if (language.contains("Gujarati")) {
            check_gujarati.setChecked(true);
        }
        if (language.contains("Panjabi")) {
            check_panjabi.setChecked(true);
        }
        if (language.contains("Bengali")) {
            check_bengali.setChecked(true);
        }
        if (language.contains("Oriya")) {
            check_oriya.setChecked(true);
        }
        if (language.contains("Tamil")) {
            check_tamil.setChecked(true);
        }
        if (language.contains("Telugu")) {
            check_telugu.setChecked(true);
        }
        if (language.contains("Kannada")) {
            check_kannada.setChecked(true);
        }
        if (language.contains("Malyalam")) {
            check_malayalam.setChecked(true);
        }


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languageDialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_language.setText("");
                String language;
                StringBuilder stringBuilder = new StringBuilder();
                if (check_english.isChecked()) {
                    stringBuilder.append("English,");
                }
                if (check_hindi.isChecked()) {
                    stringBuilder.append("Hindi,");
                }
                if (check_marathi.isChecked()) {
                    stringBuilder.append("Marathi,");
                }
                if (check_gujarati.isChecked()) {
                    stringBuilder.append("Gujarati,");
                }
                if (check_panjabi.isChecked()) {
                    stringBuilder.append("Panjabi,");
                }
                if (check_bengali.isChecked()) {
                    stringBuilder.append("Bengali,");
                }
                if (check_oriya.isChecked()) {
                    stringBuilder.append("Oriya,");
                }
                if (check_tamil.isChecked()) {
                    stringBuilder.append("Tamil,");
                }
                if (check_telugu.isChecked()) {
                    stringBuilder.append("Telugu,");
                }
                if (check_kannada.isChecked()) {
                    stringBuilder.append("Kannada,");
                }
                if (check_malayalam.isChecked()) {
                    stringBuilder.append("Malyalam,");
                }
                if (stringBuilder.toString().length() > 0) {
                    language = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                } else {
                    language = "Language Known";
                }

                tv_language.setText(language);
                languageDialog.dismiss();
            }
        });
        languageDialog.show();

    }
}