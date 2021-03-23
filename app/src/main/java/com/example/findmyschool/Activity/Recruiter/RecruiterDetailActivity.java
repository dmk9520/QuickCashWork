package com.example.findmyschool.Activity.Recruiter;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.EmployeeInterestActivity;
import com.example.findmyschool.Activity.Employee.EmployeeMainActivity;
import com.example.findmyschool.Activity.Location_Get_Activity;

import static com.example.findmyschool.Common.Constant.recruiter;

import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class RecruiterDetailActivity extends AppCompatActivity {

    private CardView cv_profile;
    private ImageView iv_profileimage;
    private EditText et_company_name;
    private EditText et_person_name;
    private EditText et_email;
    private TextView et_phonenumber;
    private EditText et_address;
    private LinearLayout cv_location;
    private TextView tv_location;
    private EditText et_industry_type;
    private EditText et_no_of_employees;
    private EditText et_year;
    private EditText et_description;
    private TextView tv_submit;
    static final Integer CAMERA_RESULT = 11;
    static final Integer CAMERA_REQUEST = 22;
    static final Integer LOCATION_REQUEST = 44;
    static final Integer GALLERY = 33;
    private Uri mUri;
    private String mCurrentPhotoPath;
    private Uri imageUri;
    private String number;
    private String code;
    private EditText tv_city;
    private EditText tv_state;
    private EditText tv_country;
    private EditText et_benefits;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ImageView iv_back;
    private EditText et_vision;
    private EditText et_mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_detail);
        number = getIntent().getStringExtra("number");
        code = getIntent().getStringExtra("code");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        initView();
        initClick();
    }

    private void initClick() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        cv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(RecruiterDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RecruiterDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RecruiterDetailActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1111);
                } else {
                    startActivityForResult(new Intent(RecruiterDetailActivity.this, Location_Get_Activity.class), LOCATION_REQUEST);
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = code + number;
                String name = et_company_name.getText().toString().trim();
                String recruiter_name = et_person_name.getText().toString().trim();
                String number = et_phonenumber.getText().toString().trim();
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
                if (imageUri == null) {
                    Toast.makeText(RecruiterDetailActivity.this, "Select Profile", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    et_company_name.setError("Enter Company Name...!");
                    return;
                }
                if (TextUtils.isEmpty(recruiter_name)) {
                    et_company_name.setError("Enter Recruiter Name...!");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    et_phonenumber.setError("Enter Number...!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Enter Email...!");
                    return;
                }
                if (latlong.equals("Location") || TextUtils.isEmpty(latlong)) {
                    Toast.makeText(RecruiterDetailActivity.this, "Select Location...!", Toast.LENGTH_SHORT).show();
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

                recruiter = new Recruiter();
                recruiter.setId(id);
                recruiter.setName(name);
                recruiter.setRecruitername(recruiter_name);
                recruiter.setMobile(number);
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
                recruiter.setProfile(String.valueOf(imageUri));

                final StorageReference ref = storageReference.child("Recruiter").child(recruiter.getId()).child("profile");
                UploadTask uploadTask = ref.putFile(Uri.parse(recruiter.getProfile()));
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String stringUri;
                            stringUri = downloadUri.toString();
                            recruiter.setProfile(stringUri);
                            databaseReference.getRoot().child("Recruiter").child(recruiter.getId()).setValue(recruiter);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Recruiter recruiter1 = snapshot.child("Recruiter").child(recruiter.getId()).getValue(Recruiter.class);
                                    Application.setRecruiter(recruiter1);
                                    Application.putLogin(true);
                                    Application.putLoginType(0);
                                    Intent intent = new Intent(RecruiterDetailActivity.this, RecruiterMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }

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
//        boolean permission = true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                showPermissionDialog();
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

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
                et_address.setText(data.getStringExtra("address"));
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
            if (ActivityCompat.checkSelfPermission(RecruiterDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RecruiterDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(RecruiterDetailActivity.this, Location_Get_Activity.class), LOCATION_REQUEST);
            }
        }

    }

    private void initView() {
        cv_profile = (CardView) findViewById(R.id.cv_profile);
        cv_location = (LinearLayout) findViewById(R.id.cv_location);
        iv_profileimage = (ImageView) findViewById(R.id.iv_profileimage);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_person_name = (EditText) findViewById(R.id.et_person_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phonenumber = (TextView) findViewById(R.id.et_phonenumber);
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

        et_phonenumber.setText(number);
    }
}