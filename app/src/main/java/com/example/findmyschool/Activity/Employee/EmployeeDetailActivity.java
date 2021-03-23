package com.example.findmyschool.Activity.Employee;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Location_Get_Activity;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.findmyschool.Common.Constant.employees;

public class EmployeeDetailActivity extends AppCompatActivity {

    private CardView cv_profile;
    private ImageView iv_profileimage;
    private ImageView iv_back;
    private EditText et_name;
    private TextView et_mobilenumber;
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
    private String number;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        number = getIntent().getStringExtra("number");
        code = getIntent().getStringExtra("code");
        initView();
        initClick();
    }


    private void initClick() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = code + number;
                String name = et_name.getText().toString().trim();
                String number = et_mobilenumber.getText().toString().trim();
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
                if (imageUri == null) {
                    Toast.makeText(EmployeeDetailActivity.this, "Select Profile", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("Enter Name...!");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    et_mobilenumber.setError("Enter Number...!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Enter Email...!");
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(EmployeeDetailActivity.this, "Select Birthdate...!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (language.equals("Language Known")) {
                    Toast.makeText(EmployeeDetailActivity.this, "Select Language...!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (location.equals("Location")) {
                    Toast.makeText(EmployeeDetailActivity.this, "Select Location...!", Toast.LENGTH_SHORT).show();
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

                employees = new Employees();
                employees.setId(id);
                employees.setProfile(String.valueOf(imageUri));
                employees.setName(name);
                employees.setNumber(number);
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
                startActivity(new Intent(EmployeeDetailActivity.this, EmployeeEducationActivity.class));
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStoragePermissionGranted()) {
                    DialogSelectImage();
                }
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
                if (ActivityCompat.checkSelfPermission(EmployeeDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployeeDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmployeeDetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1111);
                } else {
                    startActivityForResult(new Intent(EmployeeDetailActivity.this, Location_Get_Activity.class), LOCATION_REQUEST);
                }
            }
        });
        cv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguagePickerDialog();
            }
        });
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

    private void showDatePickerDialog() {
        if (day == 0) {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        new DatePickerDialog(this, datePickerListener, year, month, day).show();
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

    private void DialogSelectImage() {
        final Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_select_image);
        dialog.show();

        LinearLayout ll_dialog_camera = (LinearLayout) dialog.findViewById(R.id.ll_dialog_camera);
        LinearLayout ll_dialog_gallery = (LinearLayout) dialog.findViewById(R.id.ll_dialog_gallery);

        ll_dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    File file;
                    try {
                        file = createImageFile();
                        mUri = FileProvider.getUriForFile(getApplication().getApplicationContext(), getPackageName() + ".provider", file);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                        startActivityForResult(cameraIntent, CAMERA_RESULT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ll_dialog_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY);
            }
        });

    }

    //creat image file in gallery
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, /* prefix */ ".jpg", /* suffix */ storageDir /* directory */);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("TAG", "file is crete");
        Log.e("TAG", "mCurrentPhotoPath " + mCurrentPhotoPath);
        return image;
    }

    //storage read write permission call
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                showPermissionDialog();
                return false;
            }
        } else {
            return true;
        }
    }

    //permission dialog
    public void showPermissionDialog() {
        final Dialog permissionDialog = new Dialog(this, R.style.WideDialog);
        permissionDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        permissionDialog.setContentView(R.layout.dialog_permission);
        permissionDialog.setCancelable(false);
        TextView actionNotNow = (TextView) permissionDialog.findViewById(R.id.dialaog_notnow);
        TextView actionAllow = (TextView) permissionDialog.findViewById(R.id.dialog_Allow);
        actionNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionDialog.dismiss();
            }
        });
        actionAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionDialog.dismiss();
                GetStorage_Permission();
            }
        });
        permissionDialog.show();
    }

    //storage permission
    private void GetStorage_Permission() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                101);

    }

    private void initView() {
        cv_profile = (CardView) findViewById(R.id.cv_profile);
        cv_birthdate = (CardView) findViewById(R.id.cv_birthdate);
        cv_language = (CardView) findViewById(R.id.cv_language);
        cv_location = (CardView) findViewById(R.id.cv_location);
        cv_city = (CardView) findViewById(R.id.cv_city);
        cv_state = (CardView) findViewById(R.id.cv_state);
        cv_country = (CardView) findViewById(R.id.cv_country);
        iv_profileimage = (ImageView) findViewById(R.id.iv_profileimage);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_mobilenumber = (TextView) findViewById(R.id.et_mobilenumber);
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

        et_mobilenumber.setText(number);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                imageUri = data.getData();
                Glide.with(this).load(imageUri).into(iv_profileimage);
            } else if (requestCode == CAMERA_RESULT) {
                File file = new File(mCurrentPhotoPath);
                Log.e("TAG", "file path" + file.getAbsolutePath());
                imageUri = Uri.fromFile(file);
                Glide.with(this).load(imageUri).into(iv_profileimage);
            } else if (requestCode == GALLERY) {
                imageUri = data.getData();
                Glide.with(this).load(imageUri).into(iv_profileimage);
            } else if (requestCode == LOCATION_REQUEST) {
                tv_location.setText(data.getStringExtra("lat_long"));
                tv_city.setText(data.getStringExtra("city"));
                tv_state.setText(data.getStringExtra("state"));
                tv_country.setText(data.getStringExtra("country"));
//                if (TextUtils.isEmpty(et_Address.getText().toString().trim())){
                et_Address.setText(data.getStringExtra("address"));
//                }
            }
        }
    }

    //permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {//gallery image
            if (permissions.length >= 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    DialogSelectImage();
                }
            }
        } else if (requestCode == 1111) {
            if (ActivityCompat.checkSelfPermission(EmployeeDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployeeDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(EmployeeDetailActivity.this, Location_Get_Activity.class), LOCATION_REQUEST);
            }
        }

    }

}