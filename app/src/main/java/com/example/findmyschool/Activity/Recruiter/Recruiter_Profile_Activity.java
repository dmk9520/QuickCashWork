package com.example.findmyschool.Activity.Recruiter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

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

import com.bumptech.glide.Glide;
import com.example.findmyschool.Activity.Employee.Edit_Profile_Activity;
import com.example.findmyschool.Activity.Employee.Employee_profile_Activity;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class Recruiter_Profile_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private ShapeableImageView recruiter_profile;
    private TextView company_name;
    private TextView company_dis;
    private TextView company_location;
    private TextView recruiter_name;
    private TextView recruiter_number;
    private TextView recruiter_email;
    private TextView company_est;
    private TextView company_no_employee;
    private TextView company_type;
    private GoogleMap map;
    private Recruiter recruiter;
    private ImageView back;
    private ImageView edit_profile;
    private ImageView edit_image;
    static final Integer CAMERA_RESULT = 11;
    static final Integer CAMERA_REQUEST = 22;
    static final Integer GALLERY = 33;
    private String mCurrentPhotoPath;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_profile);
        recruiter = Application.getRecruiter();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initListener();
        initData();
    }

    private void initData() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Recruiter_Profile_Activity.this);

        company_name.setText(recruiter.getName());
        company_dis.setText(recruiter.getDescription());
        company_location.setText(recruiter.getCity() + "," + recruiter.getState());
        recruiter_name.setText(recruiter.getName());
        recruiter_number.setText(recruiter.getMobile());
        recruiter_email.setText(recruiter.getGmail());
        company_est.setText(recruiter.getEstablished());
        company_no_employee.setText(recruiter.getNoemployees());
        company_type.setText(recruiter.getIndsturytype());
        Glide.with(Recruiter_Profile_Activity.this).load(recruiter.getProfile()).into(recruiter_profile);
    }

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

    private void GetStorage_Permission() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                101);

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

    private void initListener() {
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    DialogSelectImage();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Recruiter_Profile_Activity.this, Edit_Recruiter_Activity.class), 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                recruiter = Application.getRecruiter();
                initData();
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
        final StorageReference ref = storageReference.child("Recruiter").child(recruiter.getId()).child("profile");
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
                    recruiter.setProfile(stringUri);
                    Application.setRecruiter(recruiter);
                    Glide.with(Recruiter_Profile_Activity.this).load(stringUri).into(recruiter_profile);
                    databaseReference.getRoot().child("Recruiter").child(recruiter.getId()).child("profile").setValue(stringUri);
                    if (recruiter.getReviewpost() != null) {
                        if (!TextUtils.isEmpty(recruiter.getReviewpost())) {
                            String[] temp = recruiter.getReviewpost().split(",");
                            for (int i = 0; i < temp.length; i++) {
                                databaseReference.getRoot().child("Review").child(temp[i]).child("fromprofile").setValue(stringUri);
                            }
                        }
                    }
                    if (recruiter.getJobpost() != null) {
                        if (!TextUtils.isEmpty(recruiter.getJobpost())) {
                            String[] temp = recruiter.getJobpost().split(",");
                            for (int i = 0; i < temp.length; i++) {
                                databaseReference.getRoot().child("Jobs").child(temp[i]).child("profile").setValue(stringUri);
                            }
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        recruiter_profile = (ShapeableImageView) findViewById(R.id.recruiter_profile);
        company_name = (TextView) findViewById(R.id.company_name);
        edit_profile = (ImageView) findViewById(R.id.edit_profile);
        company_dis = (TextView) findViewById(R.id.company_dis);
        company_location = (TextView) findViewById(R.id.company_location);
        recruiter_name = (TextView) findViewById(R.id.recruiter_name);
        recruiter_number = (TextView) findViewById(R.id.recruiter_number);
        recruiter_email = (TextView) findViewById(R.id.recruiter_email);
        company_est = (TextView) findViewById(R.id.company_est);
        company_no_employee = (TextView) findViewById(R.id.company_no_employee);
        company_type = (TextView) findViewById(R.id.company_type);
        back = (ImageView) findViewById(R.id.back);
        edit_image = (ImageView) findViewById(R.id.edit_image);
        float radius = getResources().getDimension(R.dimen._30sdp);
        recruiter_profile.setShapeAppearanceModel(recruiter_profile.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        String[] temp = recruiter.getLatlong().split(",");
        LatLng pacific = new LatLng(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
        map.addMarker(new MarkerOptions().position(pacific).title(recruiter.getName()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(pacific, 16));
    }
}