package com.example.findmyschool.Activity.Employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findmyschool.Adapter.Employee_Review_Adapter;
import com.example.findmyschool.Model.Employees;
import com.example.findmyschool.Model.Recruiter;
import com.example.findmyschool.Model.Resume;
import com.example.findmyschool.Model.Review;
import com.example.findmyschool.R;
import com.example.findmyschool.Utils.Application;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Recruiter_Show_Activity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private Employees employees;
    private DatabaseReference databaseReference;
    private View view;
    private GoogleMap map;
    private TextView company_info;
    private TextView company_name;
    private TextView company_established;
    private TextView company_employee;
    private TextView company_type;
    private TextView interview_address;
    private TextView preks;
    private ShapeableImageView company_profile;
    private ImageView review_star;
    private Employee_Review_Adapter employee_review_adapter;
    private RecyclerView review_recycler;
    private TextView review_title;
    Review reviews = null;
    Resume resume = null;
    ArrayList<Review> reviews_list = new ArrayList<>();
    private TextView company_mission;
    private TextView company_vision;
    private Recruiter recruiter;
    private ImageView post_job_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_show);
        recruiter = (Recruiter) getIntent().getSerializableExtra("data");
        employees = Application.getEmployee();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        initListener();
        setData();
    }

    private void initListener() {
        review_star.setOnClickListener(this);
        post_job_back.setOnClickListener(this);
    }

    private void setData() {
        company_name.setText(recruiter.getName());
        company_info.setText(recruiter.getDescription());
        company_established.setText(recruiter.getEstablished());
        company_employee.setText(recruiter.getNoemployees());
        company_type.setText(recruiter.getIndsturytype());
        preks.setText(recruiter.getBenefits());
        interview_address.setText(recruiter.getAddress());
        if (recruiter.getVision() != null) {
            if (!TextUtils.isEmpty(recruiter.getVision())) {
                String arr[] = recruiter.getVision().split("\\.");
                StringBuilder desc = new StringBuilder();
                for (String s : arr) {
                    if (desc.length() == 0) {
                        desc.append("•  " + s);
                    } else {
                        desc.append("\n• " + s);
                    }
                }
                company_vision.setText(desc.toString());
            } else {
                company_vision.setText("--");
            }
        } else {
            company_vision.setText("--");
        }

        if (recruiter.getMission() != null) {
            if (!TextUtils.isEmpty(recruiter.getMission())) {
                String arr[] = recruiter.getMission().split("\\.");
                StringBuilder desc = new StringBuilder();
                for (String s : arr) {
                    if (desc.length() == 0) {
                        desc.append("•  " + s);
                    } else {
                        desc.append("\n• " + s);
                    }
                }
                company_mission.setText(desc.toString());
            } else {
                company_mission.setText("--");
            }
        } else {
            company_mission.setText("--");
        }

        Glide.with(Recruiter_Show_Activity.this).load(recruiter.getProfile()).into(company_profile);
    }

    private void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        company_name = (TextView) findViewById(R.id.company_name);
        review_title = (TextView) findViewById(R.id.review_title);
        review_recycler = (RecyclerView) findViewById(R.id.review_recycler);
        review_star = (ImageView) findViewById(R.id.review);
        post_job_back = (ImageView) findViewById(R.id.post_job_back);
        company_info = (TextView) findViewById(R.id.company_info);
        company_established = (TextView) findViewById(R.id.company_established);
        company_employee = (TextView) findViewById(R.id.company_employee);
        company_type = (TextView) findViewById(R.id.company_type);
        interview_address = (TextView) findViewById(R.id.interview_address);
        company_mission = (TextView) findViewById(R.id.company_mission);
        company_vision = (TextView) findViewById(R.id.company_vision);
        preks = (TextView) findViewById(R.id.preks);
        company_profile = (ShapeableImageView) findViewById(R.id.company_profile);

        float radius = getResources().getDimension(R.dimen._30sdp);
        company_profile.setShapeAppearanceModel(company_profile.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());
        initReviewAdapter();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Review")) {
                    reviews_list.clear();
                    if (recruiter.getId() != null) {
                        if (recruiter.getReview() != null) {
                            if (!TextUtils.isEmpty(recruiter.getReview())) {
                                ArrayList<String> check_reviews = new ArrayList<String>(Arrays.asList(recruiter.getReview().split(",")));
                                for (DataSnapshot postSnapshot : snapshot.child("Review").getChildren()) {
                                    Review review = postSnapshot.getValue(Review.class);
                                    if (check_reviews.contains(review.getId())) {
                                        if (review.getToid().equals(recruiter.getId())) {
                                            reviews_list.add(review);
                                            if (review.getFromid().equals(employees.getId())) {
                                                reviews = review;
                                            }
                                        }
                                    }
                                }
                                if (reviews_list.size() > 0) {
                                    review_title.setVisibility(View.VISIBLE);
                                } else {
                                    review_title.setVisibility(View.GONE);
                                }
                                employee_review_adapter.setData(reviews_list);
                                if (reviews != null) {
                                    review_star.setImageDrawable(ContextCompat.getDrawable(Recruiter_Show_Activity.this, R.drawable.iv_fill_star));
                                } else {
                                    review_star.setImageDrawable(ContextCompat.getDrawable(Recruiter_Show_Activity.this, R.drawable.iv_unfil_black_star));
                                }
                            } else {
                                review_title.setVisibility(View.GONE);
                            }
                        } else {
                            review_title.setVisibility(View.GONE);
                        }
                    } else {
                        review_title.setVisibility(View.GONE);
                    }
                }
                if (snapshot.hasChild("Resume")) {
                    for (DataSnapshot postSnapshot : snapshot.child("Resume").getChildren()) {
                        Resume review = postSnapshot.getValue(Resume.class);
                        if (review.getToid().equals(recruiter.getId())) {
                            if (review.getFromid().equals(employees.getId())) {
                                resume = review;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initReviewAdapter() {
        employee_review_adapter = new Employee_Review_Adapter(Recruiter_Show_Activity.this, reviews_list);
        review_recycler.setLayoutManager(new LinearLayoutManager(Recruiter_Show_Activity.this, LinearLayoutManager.VERTICAL, false));
        review_recycler.setAdapter(employee_review_adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        String[] temp = recruiter.getLatlong().split(",");
        LatLng pacific = new LatLng(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
        map.addMarker(new MarkerOptions().position(pacific).title(recruiter.getName()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(pacific, 16));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.review:
                final Dialog reviewDialog = new Dialog(Recruiter_Show_Activity.this, R.style.WideDialog);
                reviewDialog.setContentView(R.layout.dialog_review);
                reviewDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                EditText review = (EditText) reviewDialog.findViewById(R.id.review);
                RatingBar rate = (RatingBar) reviewDialog.findViewById(R.id.rate);
                Button cancel = (Button) reviewDialog.findViewById(R.id.cancel);
                Button ok = (Button) reviewDialog.findViewById(R.id.ok);
                if (reviews != null) {
                    review.setText(reviews.getReview());
                    rate.setRating(Float.parseFloat(reviews.getStar()));
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reviewDialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp_r = review.getText().toString().trim();
                        Float temp_s = rate.getRating();
                        if (TextUtils.isEmpty(temp_r)) {
                            Toast.makeText(Recruiter_Show_Activity.this, "Enter review...!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (temp_s == 0) {
                            Toast.makeText(Recruiter_Show_Activity.this, "Give rate...!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Review review1 = new Review();
                        String id;
                        if (reviews != null) {
                            id = reviews.getId();
                        } else {
                            id = String.valueOf(System.currentTimeMillis());
                        }
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
                        review1.setId(id);
                        review1.setDate(dateString);
                        review1.setFromid(employees.getId());
                        review1.setFromname(employees.getName());
                        review1.setFromprofile(employees.getProfile());
                        review1.setToid(recruiter.getId());
                        review1.setToname(recruiter.getName());
                        review1.setToprofile(recruiter.getProfile());
                        review1.setReview(temp_r);
                        review1.setStar(String.valueOf(temp_s));
                        databaseReference.getRoot().child("Review").child(id).setValue(review1);
                        String e_review = employees.getReviewpost();
                        String r_review = recruiter.getReview();
                        if (TextUtils.isEmpty(e_review)) {
                            e_review = id;
                        } else {
                            e_review = e_review + "," + id;
                        }
                        if (TextUtils.isEmpty(r_review)) {
                            r_review = id;
                        } else {
                            r_review = r_review + "," + id;
                        }
                        databaseReference.getRoot().child("Employees").child(employees.getId()).child("reviewpost").setValue(e_review);
                        databaseReference.getRoot().child("Recruiter").child(recruiter.getId()).child("review").setValue(r_review);
                        reviewDialog.dismiss();
                    }
                });
                reviewDialog.show();
                break;
            case R.id.post_job_back:
                onBackPressed();
                break;
        }
    }
}