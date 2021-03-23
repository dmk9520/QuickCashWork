package com.example.findmyschool.Activity.Employee;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Adapter.Show_Certificate_Adapter;
import com.example.findmyschool.Adapter.Show_Education_Adapter;
import com.example.findmyschool.Adapter.Show_Exp_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class Employee_profile_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private ShapeableImageView employee_profile;
    private TextView employee_name;
    private TextView employee_number;
    private TextView employee_location;
    private TextView employee_about;
    private TextView employee_experience;
    private TextView employee_look;
    private TextView employee_skill;
    private TextView employee_certi;
    private TextView employee_education;
    private TextView employee_hobbies;
    private TextView employee_p_number;
    private TextView employee_p_email;
    private TextView employee_p_gender;
    private TextView employee_p_birth;
    private TextView employee_p_location;
    private TextView employee_p_language;
    private Employees employee;
    private RecyclerView employee_certi_recycler;
    private ImageView edit_profile;
    private RecyclerView employee_exp_recycler;
    private RecyclerView employee_education_recycler;
    private ImageView edit_work;
    private ImageView edit_look;
    private ImageView edit_certi;
    private ImageView edit_education;
    private ImageView edit_image;
    static final Integer CAMERA_RESULT = 11;
    static final Integer CAMERA_REQUEST = 22;
    static final Integer GALLERY = 33;
    private String mCurrentPhotoPath;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        employee = Application.getEmployee();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        initView();
        initListener();
        initSetData();
    }

    private void initSetData() {
        Glide.with(Employee_profile_Activity.this).load(employee.getProfile()).into(employee_profile);
        employee_name.setText(employee.getName());
        employee_number.setText(employee.getNumber());
        employee_location.setText(employee.getCity() + " | " + employee.getState());
        employee_about.setText(employee.getAbout());
        employee_hobbies.setText(employee.getHobbie());
        employee_p_number.setText(employee.getNumber());
        employee_p_email.setText(employee.getEmail());
        employee_p_gender.setText(employee.getGender());
        employee_look.setText(employee.getInterested());


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(employee.getBirth()));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        employee_p_birth.setText(day + " / " + (month + 1) + " / " + year);

        employee_p_location.setText(employee.getCity() + " | " + employee.getState());
        employee_p_language.setText(employee.getLanguage());
        employee_skill.setText(employee.getSkill());
        if (employee.getCertificate() != null) {
            if (employee.getCertificate().size() > 0) {
                employee_certi_recycler.setVisibility(View.VISIBLE);
                employee_certi.setVisibility(View.GONE);
            } else {
                employee_certi_recycler.setVisibility(View.GONE);
                employee_certi.setVisibility(View.VISIBLE);
                employee_certi.setText("--");
            }
        } else {
            employee_certi_recycler.setVisibility(View.GONE);
            employee_certi.setVisibility(View.VISIBLE);
            employee_certi.setText("--");
        }

        if (employee.getEducation() != null) {
            if (employee.getEducation().size() > 0) {
                employee_education_recycler.setVisibility(View.VISIBLE);
            } else {
                employee_education_recycler.setVisibility(View.GONE);
            }
        } else {
            employee_education_recycler.setVisibility(View.GONE);
        }

        if (employee.getJob() != null) {
            if (employee.getExperince().equals("Experience")) {
                if (employee.getJob().size() > 0) {
                    employee_exp_recycler.setVisibility(View.VISIBLE);
                    employee_experience.setVisibility(View.GONE);
                } else {
                    employee_exp_recycler.setVisibility(View.GONE);
                    employee_experience.setVisibility(View.VISIBLE);
                    employee_experience.setText("Fresher");
                }
            } else {
                employee_exp_recycler.setVisibility(View.GONE);
                employee_experience.setVisibility(View.VISIBLE);
                employee_experience.setText("Fresher");
            }
        } else {
            employee_exp_recycler.setVisibility(View.GONE);
            employee_experience.setVisibility(View.VISIBLE);
            employee_experience.setText("Fresher");
        }

        if (employee.getCertificate() != null) {
            Show_Certificate_Adapter show_certificate_adapter = new Show_Certificate_Adapter(Employee_profile_Activity.this, employee.getCertificate());
            employee_certi_recycler.setLayoutManager(new LinearLayoutManager(Employee_profile_Activity.this, LinearLayoutManager.VERTICAL, false));
            employee_certi_recycler.setAdapter(show_certificate_adapter);
        }
        employee_experience.setText(employee.getExperince());
        employee_education.setText(employee.getQualification());

        if (employee.getEducation() != null) {
            Show_Education_Adapter show_education_adapter = new Show_Education_Adapter(Employee_profile_Activity.this, employee.getEducation());
            employee_education_recycler.setLayoutManager(new LinearLayoutManager(Employee_profile_Activity.this, LinearLayoutManager.VERTICAL, false));
            employee_education_recycler.setAdapter(show_education_adapter);
        }
        if (employee.getJob() != null) {
            Show_Exp_Adapter show_exp_adapter = new Show_Exp_Adapter(Employee_profile_Activity.this, employee.getJob());
            employee_exp_recycler.setLayoutManager(new LinearLayoutManager(Employee_profile_Activity.this, LinearLayoutManager.VERTICAL, false));
            employee_exp_recycler.setAdapter(show_exp_adapter);
        }
    }

    private void initListener() {
        back.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        edit_work.setOnClickListener(this);
        edit_education.setOnClickListener(this);
        edit_look.setOnClickListener(this);
        edit_certi.setOnClickListener(this);
        edit_image.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        edit_profile = (ImageView) findViewById(R.id.edit_profile);
        edit_work = (ImageView) findViewById(R.id.edit_work);
        edit_look = (ImageView) findViewById(R.id.edit_look);
        edit_certi = (ImageView) findViewById(R.id.edit_certi);
        edit_education = (ImageView) findViewById(R.id.edit_education);
        edit_image = (ImageView) findViewById(R.id.edit_image);
        employee_profile = (ShapeableImageView) findViewById(R.id.employee_profile);
        employee_name = (TextView) findViewById(R.id.employee_name);
        employee_number = (TextView) findViewById(R.id.employee_number);
        employee_location = (TextView) findViewById(R.id.employee_location);
        employee_about = (TextView) findViewById(R.id.employee_about);
        employee_experience = (TextView) findViewById(R.id.employee_experience);
        employee_look = (TextView) findViewById(R.id.employee_look);
        employee_skill = (TextView) findViewById(R.id.employee_skill);
        employee_certi = (TextView) findViewById(R.id.employee_certi);
        employee_education = (TextView) findViewById(R.id.employee_education);
        employee_hobbies = (TextView) findViewById(R.id.employee_hobbies);
        employee_p_number = (TextView) findViewById(R.id.employee_p_number);
        employee_p_email = (TextView) findViewById(R.id.employee_p_email);
        employee_p_gender = (TextView) findViewById(R.id.employee_p_gender);
        employee_p_birth = (TextView) findViewById(R.id.employee_p_birth);
        employee_p_location = (TextView) findViewById(R.id.employee_p_location);
        employee_p_language = (TextView) findViewById(R.id.employee_p_language);
        employee_certi_recycler = (RecyclerView) findViewById(R.id.employee_certi_recycler);
        employee_education_recycler = (RecyclerView) findViewById(R.id.employee_education_recycler);
        employee_exp_recycler = (RecyclerView) findViewById(R.id.employee_exp_recycler);

        float radius = getResources().getDimension(R.dimen._35sdp);
        employee_profile.setShapeAppearanceModel(employee_profile.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.edit_profile:
                startActivityForResult(new Intent(Employee_profile_Activity.this, Edit_Profile_Activity.class), 100);
                break;
            case R.id.edit_work:
                startActivityForResult(new Intent(Employee_profile_Activity.this, EmployeeExperienceActivity.class).putExtra("type", 1), 100);
                break;
            case R.id.edit_look:
                startActivityForResult(new Intent(Employee_profile_Activity.this, EmployeeInterestActivity.class).putExtra("type", 1), 100);
                break;
            case R.id.edit_certi:
                startActivityForResult(new Intent(Employee_profile_Activity.this, EmployeeCertificateActivity.class).putExtra("type", 1), 100);
                break;
            case R.id.edit_education:
                startActivityForResult(new Intent(Employee_profile_Activity.this, EmployeeEducationMainActivity.class).putExtra("type", 1), 100);
                break;
            case R.id.edit_image:
                if (isStoragePermissionGranted()) {
                    DialogSelectImage();
                }
                break;
        }
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
                        Uri mUri = FileProvider.getUriForFile(getApplication().getApplicationContext(), getPackageName() + ".provider", file);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 100) {
                employee = Application.getEmployee();
                initSetData();
            } else if (requestCode == CAMERA_REQUEST) {
                Uri imageUri = data.getData();
                updateProfile(imageUri);
            } else if (requestCode == CAMERA_RESULT) {
                File file = new File(mCurrentPhotoPath);
                Log.e("TAG", "file path" + file.getAbsolutePath());
                Uri imageUri = Uri.fromFile(file);
                updateProfile(imageUri);
            } else if (requestCode == GALLERY) {
                Uri imageUri = data.getData();
                updateProfile(imageUri);
            }
        }
    }

    private void updateProfile(Uri imageUri) {
        final StorageReference ref = storageReference.child("Employees").child(employee.getId()).child("profile");
        UploadTask uploadTask = ref.putFile(imageUri);
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
                    String stringUri = downloadUri.toString();
                    employee.setProfile(stringUri);
                    Application.setEmployee(employee);
                    Glide.with(Employee_profile_Activity.this).load(stringUri).into(employee_profile);
                    databaseReference.getRoot().child("Employees").child(employee.getId()).child("profile").setValue(stringUri);
                    if (employee.getReview() != null) {
                        if (!TextUtils.isEmpty(employee.getReview())) {
                            String[] temp = employee.getReview().split(",");
                            for (int i = 0; i < temp.length; i++) {
                                databaseReference.getRoot().child("Review").child(temp[i]).child("toprofile").setValue(stringUri);
                            }
                        }
                    }
                }
            }
        });
    }
}